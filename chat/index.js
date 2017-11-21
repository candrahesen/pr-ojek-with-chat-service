const express = require('express')
const app = express()

var firebase = require("firebase")

 // Initialize Firebase
var config = {
	apiKey: "AIzaSyBxzVgPhOMrFtz8t-b5PE8ZZEWQvyWDgEY",
	authDomain: "pr-ojek-e79f4.firebaseapp.com",
	databaseURL: "https://pr-ojek-e79f4.firebaseio.com",
	projectId: "pr-ojek-e79f4",
	storageBucket: "pr-ojek-e79f4.appspot.com",
	messagingSenderId: "1077844006265"
};
firebase.initializeApp(config);


app.get('/', (req, res) => res.send('Hello world!'))

app.get('/login', function(req, res) {
	console.log(req.query)
})

app.listen(3000, () => console.log('Example app listening on port 3000!'))