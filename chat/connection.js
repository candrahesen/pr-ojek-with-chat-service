var mongoose = require('mongoose');
var config = require('./config')

mongoose.Promise = Promise;

mongoose.connect(config.mongoUrl, {
    useMongoClient: true,
    promiseLibrary: global.Promise
});

var db = mongoose.connection;

module.exports = db;