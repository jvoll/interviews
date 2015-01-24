var fs = require('fs');
var path = require('path');

fs.readdir(process.argv[2], function(err, list) {
    if (err) {
        console.error(err);
        return;
    }

    var fileExtension = '.' + process.argv[3];

    for (var i = 0; i < list.length; i++) {
        if (path.extname(list[i]) === fileExtension) {
            console.log(list[i]);
        }
    }
});
