var mongoose = require('mongoose');

var chatSchema = mongoose.Schema({
    topic : String,
    sender : String,
    message : String,
    time : Date
});

chatSchema.pre('save', function(next){
    var curr_time = new Date();
    this.time = curr_time;

    next();
});

var chatModel = mongoose.model('Chat', chatSchema);

module.exports = chatModel;