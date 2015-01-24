var fs = require('fs');
var path = require('path');

module.exports = function(dir, extFilter, callback) {
    fs.readdir(dir, function(err, list) {
        if (err) {
            return callback(err);
        }

        var fileExtension = '.' + extFilter;
        var filteredFiles = [];

        for (var i = 0; i < list.length; i++) {
            if (path.extname(list[i]) === fileExtension) {
                filteredFiles.push(list[i]);
            }
        }
        callback(null, filteredFiles);
    });
};
