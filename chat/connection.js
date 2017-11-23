var mongoose = require('mongoose');

mongoose.Promise = Promise;

mongoose.connect('mongodb://localhost/chat_projek', {
    useMongoClient: true,
    promiseLibrary: global.Promise
});

var db = mongoose.connection;

module.exports = db;