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

  payloadbody = querystring.stringify(payloadbody);

  console.log('topics: ' + topics + ' message: ' + req.query.messages);
  request({
    headers: {
      'Content-Length': payloadbody.length,
      'Content-Type': 'application/json',
      'Authorization': 'key=AAAA-vSCVXk:APA91bEd9voLgauOsD8F-ND2mClzYBlMznKIJmwQWJEW4sPz_u4lXN6Qy9UDjur5LjZltBXgCxLKpLRgnw5jCg4HuFcSAdYINMUqm4RNF8FQKOV_Q0qXeFOWqTjDZ91ZkwtQKdjib3Gr' 
    },
    uri: 'https://fcm.googleapis.com/fcm/send',
    body: {
      to : topics,
      priority : "high",
      notification : {
        body : messages,
        title : "FCM Message"
      }
    },
    method: "POST",
    json: true
  }, function(error, response, body) {
    console.log('error:', error); // Print the error if one occurred
    console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
    console.log('body:', body); // Print the HTML for the Google homepage.
    res.write("Bocans");
    res.end();
  });
});

/*
request('http://localhost:3000/send', function (error, response, body) {
  console.log('error:', error); // Print the error if one occurred
  console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
  console.log('body:', body); // Print the HTML for the Google homepage.
});
	*/
//start our web server
server.listen(3000, function(){
  console.log('listening on *:3000');
});