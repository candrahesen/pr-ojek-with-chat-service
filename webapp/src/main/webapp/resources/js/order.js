var firebaseConfig = {
    apiKey: "AIzaSyBxzVgPhOMrFtz8t-b5PE8ZZEWQvyWDgEY", 
    authDomain: "pr-ojek-e79f4.firebaseapp.com", 
    databaseURL: "https://pr-ojek-e79f4.firebaseio.com", 
    projectId: "pr-ojek-e79f4", 
    storageBucket: "pr-ojek-e79f4.appspot.com", 
    messagingSenderId: "1077844006265" 
}; 
firebase.initializeApp(firebaseConfig);
messaging = firebase.messaging();


var rating = 0;

function changeTo(element){
    var id = Number(element.id.charAt(7));
    for(var i = 1; i <= 5; i++){
        if(i<=id){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

function changeBack(){
    for(var i=1; i <= 5; i++){
        if(i<=rating){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

function rateThis(element){
    var id = Number(element.id.charAt(7));
    rating = id;
    for(var i = 1; i <= 5; i++){
        if(i<=rating){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

var app = angular.module('orderApp', []);

app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
});

app.controller('appController', function($scope, $timeout, $http, $window){
    $scope.isDriver = $window.isDriver;
    $scope.state = 'main';
    $scope.messages = [];
    $scope.input_msg = "";
    $scope.picking = "";
    $scope.destination = "";
    $scope.preferredDriver = "";
    $scope.comment = "";
    $scope.usernameChatRec = "";

    $scope.showPrefDriver = [];
    $scope.showOtherDriver = [];

    $scope.chosenDriver = null;
    $scope.role = null;
    $scope.rating = 0;

    $scope.selectDestClass = 'button-progress-now';
    $scope.selectDriverClass = 'button-plain';
    $scope.chatDriverClass = 'button-plain';
    $scope.completeOrderClass = 'button-plain';


    var sender = {
        id : $window.idCustomer,
        username : $window.customerUsername
    };
    var receiver;

    function appendMessage(username, msg, pos){
        var time = new Date();
        var string_time = time.getHours() + ':' + time.getMinutes();
        $scope.messages.push({
            username : username,
            message : msg,
            pos : pos,
            time : string_time
        });
    };

    function getTopics(username1, username2){
        var topic = "chat-";
        if(username1.localeCompare(username2) < 0){
            topic = topic + username1 + "-" + username2;
        } else {
            topic = topic + username2 + "-" + username1; 
        }
        return topic;
    }

    function autoScroll(){
        $timeout(function(){
            angular.element("#chat-container")[0].scrollTop = angular.element("#chat-container")[0].scrollHeight;
        }, 20);
    }

    $scope.sendMessage = function(){
        var topic = getTopics(receiver.username, sender.username);

        $http({
            url : globalConfig.chatRestPath + "send",
            method : "POST",
            headers: {'Content-Type': 'application/json'},
            params : {
                "topics": topic, 
                "receiver": receiver.username, 
                "messages": $scope.input_msg, 
                "sender": sender.username,
                "mode" : "usual"
            }
        }).then(function(response){
            var status = JSON.parse(response.data).status;
            if(status == "success"){
                appendMessage(sender.username, $scope.input_msg, 'right');
                autoScroll();
            } else {
                console.log(response.data);
            }
            $scope.input_msg = "";
        }, function(error){
            console.log(error);
        })        
    };
    $scope.nextToFindOrder = function(){
        // $timeout(function(){
        //     $scope.state = 'finding.got';
        //     $timeout(function(){
        //         $scope.state = 'chatting';
        //     }, 1000);
        // }, 2000);
        $http({url : globalConfig.baseUrl + "soapservlet",
            method : "POST",
            headers: {'Content-Type': 'application/json'},
            params : {
                "name": "set-finding", 
                "finding" : 1
            }
        }).then(function success(response){
            console.log(response.data);
            $scope.state = 'finding';
            $scope.role = 'driver';
        }, function error(response){
            console.log("We encounter an error");
        });
    };

    $scope.cancelFinding = function(){
        $http({url : globalConfig.baseUrl + "soapservlet",
            method : "POST",
            headers: {'Content-Type': 'application/json'},
            params : {
                "name": "set-finding", 
                "finding" : 0
            }
        }).then(function success(response){
            console.log(response.data);
            $scope.state = 'main';
            $scope.role = null;
        }, function error(response){
            console.log("We encounter an error");
        });
    };

    $scope.grabDriver = function(){
        $scope.role = 'customer';
        $scope.selectDestClass = 'button-plain';
        $scope.selectDriverClass = 'button-progress-now';
        if($scope.preferredDriver != ""){
            $http({
                url : globalConfig.baseUrl + "soapservlet",
                method : "POST",
                headers: {'Content-Type': 'application/json'},
                params : {
                    "name": "get-driver", 
                    "dest": $scope.destination, 
                    "loc": $scope.picking, 
                    "prefDriver": $scope.preferredDriver
                }
            }).then(function success(response){
                console.log(response.data);  
                $scope.showPrefDriver = response.data;
                $scope.state = 'choosing';
            }, function error(response){
                console.log(response.statusText);
            });
        } else {
            $http({
                url : globalConfig.baseUrl + "soapservlet",
                method : "POST",
                headers: {'Content-Type': 'application/json'},
                params : {
                    "name": "get-driver", 
                    "dest": $scope.destination, 
                    "loc": $scope.picking, 
                }
            }).then(function success(response){
                console.log(response.data);  
                $scope.showOtherDriver = response.data;
                $scope.state = 'choosing';
            }, function error(response){
                console.log(response.statusText);
            });
        }
    };

    $scope.selectDriver = function(event){
        var id = event.target.id;
        if ($scope.preferredDriver != ""){
            var i = 0;
            for (x in $scope.showPrefDriver){
                if ($scope.showPrefDriver[i].id == id){
                    $scope.chosenDriver = $scope.showPrefDriver[i];
                    break;
                }
                i++;
            }
        } else {
            var i = 0;
            for (x in $scope.showOtherDriver){
                if ($scope.showOtherDriver[i].id == id){
                    $scope.chosenDriver = $scope.showOtherDriver[i];
                    break;
                }
                i++;
            }
        }

        receiver = {
            id : $scope.chosenDriver.id,
            username : $scope.chosenDriver.username
        };

        $http({
            url : globalConfig.chatRestPath + "send",
            method : "POST",
            headers: {'Content-Type': 'application/json'},
            params : {
                "topics": "opening", 
                "receiver": receiver.username, 
                "messages": sender.username, 
                "sender": sender.username,
                "mode" : "open"
            }
        }).then(function(response){
            var status = JSON.parse(response.data).status;
            if(status == "success"){
                var topic = getTopics(sender.username, receiver.username);
                $http({
                    url : globalConfig.chatRestPath + "history",
                    method : "GET",
                    headers: {'Content-Type': 'application/json'},
                    params : {
                        "topics": topic, 
                    }
                }).then(function(response){
                    var chats = JSON.parse(response.data).result;
                    var i = 0;
                    for (x in chats){
                        if (chats[i].sender == sender.username) {
                            appendMessage(chats[i].sender, chats[i].message, 'right');
                        } else {
                            appendMessage(chats[i].sender, chats[i].message, 'left');
                        }
                        i++;
                    }
                    autoScroll();
                }, function (error){
                    console.log(error);
                });
                $scope.selectDriverClass = 'button-plain';
                $scope.chatDriverClass = 'button-progress-now';
                $scope.state = 'chatting';
            } else {
                console.log(response.data);
            }
            $scope.input_msg = "";
        }, function(error){
            console.log(error);
        })
    }

    $scope.closeChat = function(){
        $scope.state = 'completing';
        $scope.chatDriverClass = 'button-plain';
        $scope.completeOrderClass = 'button-progress-now';
    }

    $scope.rate = function(rating){
        $scope.rating = rating;
    }

    $scope.completeOrder = function(){
        if($scope.rating > 0){
            $http({
                url : globalConfig.baseUrl + "soapservlet",
                method : "POST",
                headers: {'Content-Type': 'application/json'},
                params : {
                    "name": "submit-order", 
                    "dest": $scope.destination, 
                    "loc": $scope.picking, 
                    "iddriver": $scope.chosenDriver.id,
                    "idcust": $window.idCustomer,
                    "rate": $scope.rating,
                    "comm": $scope.comment
                }
            }).then(function success(response){
                console.log(response.data);  
                if(response.data == "ok"){
                    $http({
                        url : globalConfig.chatRestPath + "send",
                        method : "POST",
                        headers: {'Content-Type': 'application/json'},
                        params : {
                            "topics": "closing", 
                            "receiver": receiver.username, 
                            "messages": "", 
                            "sender": sender.username,
                            "mode" : "close"
                        }
                    }).then(function(response){
                        var status = JSON.parse(response.data).status;
                        if(status == "success"){
                            $window.location.reload();
                        } else {
                            console.log(response.data);
                        }
                        $scope.input_msg = "";
                    }, function(error){
                        console.log(error);
                    });
                } else {
                    alert("Error occured");
                }
            }, function error(response){
                console.log(response.statusText);
            });
        } else {
            alert("Please give rating");
        }
    };

    $window.onbeforeunload = function(){
        if ($scope.state == 'finding'){
            $http({url : globalConfig.baseUrl + "soapservlet",
                method : "POST",
                headers: {'Content-Type': 'application/json'},
                params : {
                    "name": "set-finding", 
                    "finding" : 0
                }
            }).then(function success(response){
                console.log(response.data);
            }, function error(response){
                console.log("We encounter an error");
            });
        }
    }

    navigator.serviceWorker.register(globalConfig.baseUrl + "resources/js/firebase-messaging-sw.js")
    .then(function (registration) {
        messaging.useServiceWorker(registration);
        messaging.requestPermission().then(function() {
            console.log("Permission granted");
            return messaging.getToken();
        }).then(function(token) {
            console.log(token);
            // $scope.browserToken = token;
            $http({
                url : globalConfig.chatRestPath + "register",
                method : "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params : {
                    "username": customerUsername,
                    "token": token,
                }
            }).then(function success(response){
                console.log("Success registering token to chat service", response.data);
            }, function error(response){
                console.log(response.statusText);
            });
        }.bind($scope)).catch(function(err) {
            console.log("Error occured", err);
        });
    });
    
    messaging.onMessage(function(payload) {
        var title = payload.notification.title;
        var body = payload.notification.body;
        console.log("Get message with title: " + title + " and body: " + body);
    
        var scope = angular.element($("#extern-container")).scope();
        if (title == 'open'){
            if(scope.state == 'finding') {
                receiver = {
                    username : body
                };
                $http({url : globalConfig.baseUrl + "soapservlet",
                    method : "POST",
                    headers: {'Content-Type': 'application/json'},
                    params : {
                        "name": "set-finding", 
                        "finding" : 0
                    }
                }).then(function success(response){
                    console.log(response.data);
                }, function error(response){
                    console.log("We encounter an error");
                });
                var topic = getTopics(sender.username, receiver.username);
                $http({
                    url : globalConfig.chatRestPath + "history",
                    method : "GET",
                    headers: {'Content-Type': 'application/json'},
                    params : {
                        "topics": topic, 
                    }
                }).then(function(response){
                    var chats = JSON.parse(response.data).result;
                    var i = 0;
                    var pos;
                    console.log(JSON.parse(response.data).result);
                    $timeout(function(){
                        for (x in chats){
                            if (chats[i].sender == sender.username) {
                                pos = 'right';
                            } else {
                                pos = 'left';
                            }
                            scope.$apply(function(){
                                scope.messages.push({
                                    username : chats[i].sender,
                                    message : chats[i].message,
                                    pos : pos,
                                    time : chats[i].time
                                });
                                scope.usernameChatRec = body;
                                scope.state = 'chatting';
                            });
                            i++;
                        }
                        autoScroll();
                    }, 0);
                }, function (error){
                    console.log(error);
                });
            }
        } else if (title == 'close'){
            if(scope.state == 'chatting') {
                window.location.reload();
            }
        } else if (title == 'usual'){
            if(scope.state == 'chatting'){
                scope.$apply(function(){
                    appendMessage(receiver.username, body, 'left');
                    autoScroll();
                });
            }
        }
        // if (sender == $scope.chosenDriver.username && receiver == $window.customerUsername) {
        //     console.log("Append message with content: ", message);
        // }
    });
});