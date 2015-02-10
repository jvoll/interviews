var net = require('net');

function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

var server = net.createServer(function callback(socket) {
    var date = new Date();
    var dateStr = date.getFullYear() + "-" + pad(date.getMonth() + 1, 2) + "-" + pad(date.getDate(), 2) +
        " " + date.getHours() + ":" + date.getMinutes();
    socket.write(dateStr);
    socket.end('\n');
});

server.listen(process.argv[2]);
