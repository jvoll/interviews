var fs = require('fs');

var buffer = fs.readFileSync(process.argv[2]);
var s = buffer.toString();
var nl = s.split('\n').length - 1;
console.log(nl);

// note you can avoid the .toString() by passing 'utf8' as the
// second argument to readFileSync, then you'll get a String!
//
// fs.readFileSync(process.argv[2], 'utf8').split('\n').length - 1
