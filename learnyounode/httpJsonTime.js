var http = require('http');
var url = require('url');

var server = http.createServer(function (req, res) {

    var parsedUrl = url.parse(req.url, true);
    var date;
    var retObj;
    if (parsedUrl.query.iso) {
        date = new Date(parsedUrl.query.iso);
    }

    if (parsedUrl.pathname === '/api/parsetime' && date) {
        retObj = {
            hour: date.getHours(),
            minute: date.getMinutes(),
            second: date.getSeconds()
        };
    } else if (parsedUrl.pathname === '/api/unixtime' && date) {
        retObj = {
            unixtime: date.getTime()
        };
    } else {
        res.writeHead(404);
        res.end();
        return;
    }

    res.writeHead(200, { 'content-type' : 'application/json' });
    res.write(JSON.stringify(retObj));
    res.end();
});

server.listen(process.argv[2]);
