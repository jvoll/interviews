var mymodule = require('./filteredLSModule.js');

mymodule(process.argv[2], process.argv[3], function(err, data) {
    if (err) {
        console.log(err);
        return;
    }

    data.forEach(function(file) {
        console.log(file);
    });
});
