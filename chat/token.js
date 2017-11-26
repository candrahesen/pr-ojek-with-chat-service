var mongoose = require('mongoose');

var tokenSchema = mongoose.Schema({
    token : String,
    username : String
});

var tokenModel = mongoose.model('Token', tokenSchema);

module.exports = tokenModel;