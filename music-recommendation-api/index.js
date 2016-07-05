var express = require('express');
var bodyParser = require("body-parser");
var mongo = require('mongodb').MongoClient;
var fs = require('fs');
var url = require('url');


// initialize app
console.log("initializing app");
var db;
var app = express();
app.use(bodyParser.urlencoded({ extended: false }));

var server = app.listen(3000, function () {
  setup();
  var host = server.address().address
  var port = server.address().port
  console.log('Music recommendation app listening at http://%s:%s', host, port)
});

// Setup and initialize the app
function setup() {
  // Connect to the db and load in initial music
  mongo.connect("mongodb://localhost:27017/music-db", function(err, database) {
      if(err) {
          console.error("Connection to music-db failed: ", err);
          return;
      }

      db = database;
      console.log("We are connected to music-db");
      buildMusicCollection();
  });
};

// Helper function calculating the number of properties on an object
function objectSize(obj) {
    var n = 0;
    for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
            n++;
        }
    }
    return n;
}

// Helper to build the music collection in the db
// Removes old music collection before adding (as a cleanup).
// The inserts from file music.json.
function buildMusicCollection() {
    db.collection('music-types').remove({}, function (err, numRemoved) {
        if (err) {
            console.error("error removing music collection on init: ", err);
            return;
        }

        console.log("loading music.json to db");
        var buffer = fs.readFileSync('music.json');
        var musicJson = JSON.parse(buffer.toString());
        var musicCollection = db.collection('music-types');
        var numInserted = 0;
        var numMusics = objectSize(musicJson);
        for (var key in musicJson) {
            if (musicJson.hasOwnProperty(key)) {
                musicCollection.insert({ typeid: key,  genres: musicJson[key] }, { w:1}, function(err, result) {
                    if (err) {
                        console.error("Error inserting music types: ", err);
                    }
                    numInserted++;
                    if (numInserted == numMusics) {
                        musicInsertComplete(musicCollection);
                    }
                });
            }
        }
    });
};

function musicInsertComplete(coll) {
    // output all music --debug
    //coll.find().each(function(err,doc) {
    //    console.log("all: ", doc);
    //});
    console.log("api up and ready");
};

// Not part of api spec -- but why not? :)
app.get('/', function (req, res) {
    var msg = {
        msg: "Hello World! This isn't a real path, but thought I should say hello."
    };
    res.send(JSON.stringify(msg));
});

//  Return 5 music recommendations to this user, they should be sorted by relevance
//  Query string has:
//  user: \<user ID\>
//  response looks like:
//  ```json
//  {
//    "list": ["<music ID>", "<music ID>", "<music ID>", "<music ID>", "<music ID>"]
//  }
//  ```
app.get('/recommendations', function (req, res) {
    var parsedUrl = url.parse(req.url, true);
    // check request for required query param
    if (!parsedUrl.query.user) {
        return sendBadRequest(req, res, '/recommendations');
    }

    var userId = parsedUrl.query.user;

    // start by getting recommendations based on user's past listening
    getRecommendationsBasedOnPast(userId, [], function(recs) {
        // if we have enough recommendations, return them
        if (recs && recs.length > 4) {
            return writeRecommendations(recs, res);
        }

        // then get recommendations based on people the user follows
        getRecommendationsBasedOnFollowed(userId, recs, function(newRecs) {
            recs = recs.concat(newRecs)
            if (recs && recs.length> 4) {
                return writeRecommendations(recs, res);
            }

            // then get recommendations based on what other users who have listened to your listens listened to
            getRecomendationsBasedOnOthersHistory(recs, function(moreRecs) {
                recs = recs.concat(moreRecs);
                if (recs && recs.length> 4) {
                    return writeRecommendations(recs, res);
                }

                // finally, fill in with random recommendations
                getSomeMusicFallback(recs, function(moreRecs) {
                  recs = recs.concat(moreRecs);
                  return writeRecommendations(recs, res);
                });
            });
        });
    });
});

// Helper function to write out a max of 5 recommendations to the response
function writeRecommendations(recs, res) {
    var recsObj = {
        list: recs.slice(0,5)
    };
    res.writeHead(200, { 'content-type' : 'application/json' });
    res.write(JSON.stringify(recsObj));
    res.end();
}

// Get recommendations based on past listening
// userId: user id to lookup past listens for
// exclusionList: list of musics to exclude (already have these as recommendations)
// callback: function to call with recommendations
function getRecommendationsBasedOnPast(userId, exclusionList, callback) {
    db.collection('users').find({ "userId" : userId }).toArray(function(err, docs) {
        if (docs && docs.length > 0 && docs[0].listens && docs[0].listens.length > 0) {
            console.log("userId: ", userId, " exclusionList: ", exclusionList, " docs.listens ", docs[0].listens);
            if (exclusionList && exclusionList.length > 0) {
                return callback(docs[0].listens);
            } else {
                // return filtered version of array that excludes recommendations that are arleady in the list
                return callback(docs[0].listens.filter(function(el) {
                    return exclusionList.indexOf(el) < 0;
                }));
            }
        }
        return callback([]);
    });
};

// Get recommendations based on who a user follows
// userId: user id to lookup
// exclusionList: list of musics to exclude (already have these as recommendations)
// callback: function to call with recommendations
function getRecommendationsBasedOnFollowed(userId, exclusionList, callback) {
    db.collection('users').find({ "userId" : userId }).toArray(function(err, docs) {
        // user docs[0] as we expect userId to be unique
        if (docs && docs.length > 0 && docs[0].follows && docs[0].follows.length > 0) {
            var follows = docs[0].follows;
            var moreFollows = follows.length; // countdown lock mechanism
            var addedRecs = [];
            for (var i = 0; i < follows.length; i++) {
                console.log("making call on follows[i]: ", follows[i]);
                // get the followees listening history
                getRecommendationsBasedOnPast(follows[i], exclusionList, function(moreRecs) {
                    console.log("moreRecs from call to followee ", follows[i], ":", moreRecs);
                    addedRecs = addedRecs.concat(moreRecs);
                    moreFollows--;
                    console.log("addedRecs: ", addedRecs);
                    // wait until we've heard back from all db calls
                    if (moreFollows == 0) {
                        return callback(addedRecs);
                    }
                });
            }

            if (moreFollows == 0) {
                return callback([]);
            }

        } else {
            return callback([]);
        }
    });
};

// based on any listening history, see what others who have listened
// to that music also listened to.
function getRecomendationsBasedOnOthersHistory(curRecs, callback) {
    var addedRecs = [];
    var countdownLock = curRecs.length;
    
    // for music user has listened to in the past (or has been recommended) find similar music based on other users past listenings
    for (var j = 0; j< curRecs.length; j++) {
      // arbitrary limit to 10 since 10 should probably populate our 5 required musics, especially if the user already has a listen or two recorded
      db.collection('users').find({ "listens" : { $elemMatch: { $eq: curRecs[j] }}}).limit(10).toArray(function(err, docs) {
          if (docs && docs.length > 0) {
              for (var k = 0; k < docs.length; k++){
                doc = docs[k];
                if (doc && doc.listens) {
                  var listens = doc.listens;
                  for (var i = 0; i < listens.length; i++) {
                      // if we don't already have this music recommended, add it
                      if (curRecs.indexOf(listens[i]) < 0 && addedRecs.indexOf(listens[i]) < 0) {
                        console.log("adding music based on random other people's listens: ", listens[i]);
                        addedRecs.push(listens[i]);
                      }
                  }
                }
              }
           } 
          countdownLock--;
          if (countdownLock < 1) {
            return callback(addedRecs); 
          }
       });
    }
    if (countdownLock < 1) {
      return callback(addedRecs); 
    }
};


// Get a some random music from the database.
// Just grabs the first 10 musics from db.
function getSomeMusicFallback(curRecs, callback) {
  var addedRecs = [];
  db.collection('music-types').find({}).limit(10).toArray(function (err, docs) {
    if (docs && docs.length > 0) {
      for (var i = 0; i < docs.length; i++) {
        var type = docs[i].typeid;
        if (curRecs.indexOf(type) < 0) {
          addedRecs.push(type);
        }
      }
      console.log("got ", addedRecs.length, " random recs ", addedRecs);
    } 
    return callback(addedRecs);
  });
}

// Add one follow relationship (see `follows.json`)
// Two params in request body
// from: \<user ID\>
// to: \<user ID\>
app.post('/follow', function (req, res) {

    // check request for required body params
    if (!(req.body.from && req.body.to)) {
        return sendBadRequest(req, res, '/follow');
    }

    console.log("writing follow to db with data: ", req.body.from, req.body.to);
    var usersCollection = db.collection('users');
    usersCollection.update(
        { userId : req.body.from },
        {
            $addToSet: { follows: req.body.to }
        },
        {
            upsert: true
        },
        function (err, cnt, stat) {
            console.log("update complete");
            if (err) {
                console.error("Error on upsert for follow: ", err);
                res.writeHead(400);
                res.end();
                return;
            }
            res.writeHead(200, { 'content-type' : 'application/json' });
            res.write(JSON.stringify("success"));
            res.end();
        }
    );
});

// Add one song as the user has just listened ( see `listen.json` )
// Two params in request body:
// user: \<user ID\>
// music: \<music ID\>
app.post('/listen', function (req, res) {

    // check request for required body params
    if (!(req.body.user && req.body.music)) {
        return sendBadRequest(req, res, '/listen');
    }

    console.log("writing listen to db with data: ", req.body.user, req.body.music);
    var usersCollection = db.collection('users');
    usersCollection.update(
        { userId : req.body.user},
        {
            $addToSet: { listens: req.body.music }
        },
        {
            upsert: true
        },
        function (err, cnt, stat) {
            console.log("update complete");
            if (err) {
                console.error("Error on upsert for follow: ", err);
                res.writeHead(400);
                res.end();
                return;
            }
            res.writeHead(200, { 'content-type' : 'application/json' });
            res.write(JSON.stringify("success"));
            res.end();
        }
    );
});

// Helper to log and return a bad request
function sendBadRequest(req, res, endpointName) {
  console.error("bad request to: ", endpointName);
  res.writeHead(400);
  res.end();
};

