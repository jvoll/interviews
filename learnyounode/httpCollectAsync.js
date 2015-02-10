var http = require('http');
var bl = require('bl');

var responseData = [];
var responses = 0;

function printIt() {
    for (var j = 0; j < responseData.length; j++) {
        console.log(responseData[j]);
    }
}

function getIt(index) {
    http.get(process.argv[2 + index], function callback(response) {
        response.pipe(bl(function (err, data) {
            if (err) {
                return console.error(err);
            }

            responseData[index] = data.toString();
            responses++;

            if (responses == 3) {
                printIt();
            }
        }));
    });
}

for (var i = 0; i < 3; i++) {
    getIt(i);
}
