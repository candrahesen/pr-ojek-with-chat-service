var express = require('express');  
var app = express();  
var server = require('http').createServer(app); 
var request = require('request'); 
var chat = require('./chat');
var db = require('./connection');
var mongoose = require('mongoose');

db.once('open', function(){
  console.log("Connected to mongoDB");
});

var saveChat = function(sender, message, topic){
  var chatEntity = chat({
    topic : topic,
    sender : sender,
    message : message
  })
  
  chatEntity.save(function(err){
    if(err) throw err;

    console.log("Chat saved");
  });
}


app.post('/send', function(req, res) {
  var topics = "/topics/" +  req.query.topics;
  var messages = req.query.messages;
  var sender = req.query.sender;

  var payloadbody = {
    to : topics,
    priority : "high",
    notification : {
      body : messages,
      title : "FCM Message"
    }
  }

  console.log("Sending: " + payloadbody);
  
  request.post('https://fcm.googleapis.com/fcm/send',
   {headers : {
      'Content-Type': 'application/json',
      'Authorization': 'key=AAAA-vSCVXk:APA91bEd9voLgauOsD8F-ND2mClzYBlMznKIJmwQWJEW4sPz_u4lXN6Qy9UDjur5LjZltBXgCxLKpLRgnw5jCg4HuFcSAdYINMUqm4RNF8FQKOV_Q0qXeFOWqTjDZ91ZkwtQKdjib3Gr' 
    },
    json: payloadbody}
  ).on('response', function(response){
    console.log("Status code: " + response.statusCode);
    response.on('data', function(data){
      console.log("Get data: " + data.toString());
      if(JSON.parse(data).hasOwnProperty('message_id')){
        res.write(JSON.stringify({status : 'success'}));

        var chats = db.collection('chats');
        chats.find({}, {_id: 0}).sort({$natural: 1}).each(function(err, doc) {
	      console.log(doc);
	    });

    	console.log(saveChat(sender, messages, topics));

      } else {
        res.write(JSON.stringify({status : 'failed'}));
      }
      res.end();
    });
  }).on('error', function(err){
    res.write(JSON.stringify({status: 'failed'}));
    res.close();
  });
});

app.get('/history', function(topics) {

});

//start our web server
server.listen(3000, function(){
  console.log('listening on *:3000');
});