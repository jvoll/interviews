// bl https://www.npmjs.com/package/bl
var http = require('http');
var bl = require('bl');

http.get(process.argv[2], function callback(response) {
    response.pipe(bl(function (err, data) {
        if (err) {
            return console.error(err);
        }
        data = data.toString();
        console.log(data.length);
        console.log(data);
    }));
});
