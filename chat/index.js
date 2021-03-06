var express = require('express');  
var app = express();  
var server = require('http').createServer(app); 
var request = require('request'); 
var chat = require('./chat');
var db = require('./connection');
var mongoose = require('mongoose');

var tokenMapping = {};

app.use(express.json());
app.use(express.urlencoded({extended: true}));

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

app.use(function(req, res, next) {
  res.setHeader("Access-Control-Allow-Origin", "*");
  next();
});

app.post('/register', function(req, res) {
  // should check token to identity service for security purpose.
  var token = req.query.token;
  var username = req.query.username;
  tokenMapping[username] = token;
  res.json({token:token, username:username});
});

// for debugging purpose only
app.get('/token_map', function(req, res) {
  res.json(tokenMapping);
});

app.post('/send', function(req, res) {
  var topics = "/topics/" +  req.query.topics;
  var receiver = req.query.receiver;
  var messages = req.query.messages;
  var sender = req.query.sender;
  var mode = req.query.mode;

  console.log("Chat sent", {
    topic: topics,
    receiver: receiver,
    message: messages,
    sender: sender
  });

  res.setHeader("Access-Control-Allow-Origin", "*");
  var receiverToken = tokenMapping[receiver];
  if (receiverToken == undefined) {
    console.log("Chat not sent because receiver is not registered");
    res.json({"error":"receiver not registered in the session"});
  }

  var payloadbody = {
    to : receiverToken,
    priority : "high",
    notification : {
      body : messages,
      title : mode
    }
  }

  console.log("Sending payload body to firebase", payloadbody);
  
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
      if(JSON.parse(data).success > 0){
        res.json(JSON.stringify({status : 'success'}));

    	  saveChat(sender, messages, topics);
      } else {
        res.json(JSON.stringify({status : 'failed'}));
      }
      res.end();
    });
  }).on('error', function(err){
    res.json(JSON.stringify({status: 'failed'}));
    res.end();
  });
});

app.get('/history', function(req, res) {
    var paramtopic = req.query.topics;

    console.log(paramtopic);

    var identtopic = chat.find({topic : "/topics/" + paramtopic}).exec(function(err, docs){
    	if(err) {
    		res.json(JSON.stringify({status : 'failed'}));
    		throw err;
    	} else {
    		var response = {
    			status : "success",
    			result : docs
    		}
    		res.json(JSON.stringify(response));
    	}
    	res.end();
    });
});

//start our web server
server.listen(3000, function(){
  console.log('listening on *:3000');
});