importScripts('https://www.gstatic.com/firebasejs/4.6.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.6.2/firebase-messaging.js');
var config = {
	apiKey: "AIzaSyBxzVgPhOMrFtz8t-b5PE8ZZEWQvyWDgEY",
	authDomain: "pr-ojek-e79f4.firebaseapp.com",
	databaseURL: "https://pr-ojek-e79f4.firebaseio.com",
	projectId: "pr-ojek-e79f4",
	storageBucket: "pr-ojek-e79f4.appspot.com",
	messagingSenderId: "1077844006265"
};
firebase.initializeApp(config);

const messaging = firebase.messaging();
