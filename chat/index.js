var express = require('express');  
var app = express();  
var server = require('http').createServer(app); 
var request = require('request'); 
var querystring = require('querystring');

app.post('/send', function(req, res) {
  var topics = "/topics/" +  req.query.topics;
  var messages = req.query.messages;

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

//start our web server
server.listen(3000, function(){
  console.log('listening on *:3000');
});