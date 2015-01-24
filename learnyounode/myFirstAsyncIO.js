var fs = require('fs');
fs.readFile(process.argv[2], 'utf8', function doneReading(err, data) {
    if (err) {
        console.error(err);
        return;
    }
    console.log(data.split('\n').length - 1);
});
