/*
This should be run by some command; it may be like a test framework, or just a series of requests and feel free to ask us on what we had in mind with this if it doesn't make sense! Feel free to implement this script in any language.
(We usually use Mocha on Node.js in case you want a hint, Ruby has a couple of options, including RSpec)

- feed the `follows.json` through your `/follow` endpoint, in sequence (or parallel)
- the same for `/listen`
- make a call to user "a" on `/recommendations` and display the results
*/

var assert = require("assert")
var http = require('http');
var querystring = require('querystring');
var fs = require('fs');
var mongo = require('mongodb').MongoClient;

/*
Make a post request.
endPointPath - the api endpoint including the beginning /
postData - data to post
done - function to call when post succeeds
*/
function makePost(endpointPath, postData, done) {
    var options = {
      hostname: 'localhost',
      port: 3000,
      path: endpointPath,
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': postData.length
      }
    };

    var req = http.request(options, function(res) {
      //console.log('STATUS: ' + res.statusCode);
      //console.log('HEADERS: ' + JSON.stringify(res.headers));
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
        //console.log('BODY: ' + chunk);
        assert.equal('"success"', chunk);
        done();
      });
    });

    req.on('error', function(e) {
      console.log('problem with request: ' + e.message);
      throw e;
    });

    // write data to request body
    console.log(postData);
    req.write(postData);
    req.end();
}

/*
Make a get request.
endPointPath - the api endpoint including the beginning /
queryString - query string including '?'
done - function to call when post succeeds - accepts param with json response data
*/
function makeGet(endpointPath, queryString, done) {
    var options = {
      hostname: 'localhost',
      port: 3000,
      path: endpointPath + queryString,
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };

    console.log("request options: ", options);

    var req = http.request(options, function(res) {
      //console.log('STATUS: ' + res.statusCode);
      //console.log('HEADERS: ' + JSON.stringify(res.headers));
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
        //console.log('BODY: ' + chunk);
        done(JSON.parse(chunk));
      });
    });

    req.on('error', function(e) {
      console.log('problem with request: ' + e.message);
      throw e;
    });

    console.log(queryString);
    req.end();
}

describe('music api test suite', function() {

    // Clear the users collection in the db to start the test suite
    before(function(done) {
        mongo.connect("mongodb://localhost:27017/music-db", function(err, database) {
            if(err) {
                console.error("Connection to music-db failed: ", err);
                return;
            }

            db = database;
            console.log("We are connected to music-db");
            db.collection('users').remove({}, function (err, numRemoved) {
                if(err) {
                    console.error("Error emptying users collection: ", err);
                    return;
                }

                console.log("Cleared users database so we can accurately test insertion via api endpoints. Num records removed: ", numRemoved);

                db.collection('users').count({}, function (err, cnt) {
                    if(err) {
                        console.error("Error emptying users collection on count: ", err);
                        return;
                    } else if (cnt > 0) {
                        console.error("Count was > 0 after emptying users collection: ", cnt);
                        return;
                    }
                    console.log("Success emptying users collection");
                    done();
                });
            });
        });
    });

    // Passes in follows.json via API endpoint and passes when no error responses come back
    describe('Load in data via follow endpoint', function() {
        it('should return "success" for each call to /follow', function(done){

            var buffer = fs.readFileSync('follows.json');
            var followsString = buffer.toString();
            var followsJson = JSON.parse(followsString);
            var numPostsTotal = followsJson.operations.length;

            for (var i = 0; i<numPostsTotal; i++) {
                var postData = querystring.stringify({
                  from : followsJson.operations[i][0],
                  to: followsJson.operations[i][1]
                });

                makePost('/follow', postData, function finished() {
                    numPostsTotal--;
                    if (numPostsTotal < 1) {
                        done();
                    }
                });
            }
        });
    });

    // Passes in listen.json via API endpoint and passes when no error responses come back
    describe('Load in data via listen endpoint', function() {
        it('should return "success" for each call to /listen', function(done){

            var buffer = fs.readFileSync('listen.json');
            var listensString = buffer.toString();
            var listensJson = JSON.parse(listensString);
            var numPostsTotal = 0;

            for(var userId in listensJson.userIds) {
                if (listensJson.userIds.hasOwnProperty(userId)) {
                  numPostsTotal += listensJson.userIds[userId].length;
                }
            }

            for(var userId in listensJson.userIds) {
                if (listensJson.userIds.hasOwnProperty(userId)) {
                    for(var i = 0; i<listensJson.userIds[userId].length; i++) {
                        var postData = querystring.stringify({
                            user: userId,
                            music: listensJson.userIds[userId][i]
                        });

                        makePost('/listen', postData, function finished() {
                            numPostsTotal--;
                            if (numPostsTotal < 1) {
                                done();
                            }
                        });
                    }
                 }
            }
        });
    });

    after(function() {
        console.log("POST tests complete");

        describe('Recommendations on a user that we have no data on', function() {
            it('get recommendations for a non-existent user and get exactly 5 recs', function(done) {
                makeGet('/recommendations', '?user=zzzz', function(jsonResp) {
                    assert.equal(5, jsonResp.list.length);
                    console.log("Response to /recommendations?user=zzz: ", jsonResp);
                    done();
                });
            });
        });

        describe('Recommendations', function() {
            it('get recommendations for user a, expect to get back exactly 5 recs', function(done) {
                makeGet('/recommendations', '?user=a', function(jsonResp) {
                    assert.equal(5, jsonResp.list.length);
                    console.log("Response to /recommendations?user=a: ", jsonResp);
                    done();
                });
            });
        });
    });

});
