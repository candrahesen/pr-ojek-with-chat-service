var config = { 
    apiKey: "AIzaSyBxzVgPhOMrFtz8t-b5PE8ZZEWQvyWDgEY", 
    authDomain: "pr-ojek-e79f4.firebaseapp.com", 
    databaseURL: "https://pr-ojek-e79f4.firebaseio.com", 
    projectId: "pr-ojek-e79f4", 
    storageBucket: "pr-ojek-e79f4.appspot.com", 
    messagingSenderId: "1077844006265" 
}; 
firebase.initializeApp(config);
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
    $scope.messages = [
        {
            username : 'agung',
            message : 'Hello',
            pos : 'right',
            time : '11:50'
        },
        {
            username : 'bocan',
            message : 'Hello juga',
            pos : 'left',
            time : '11:51'
        },
        {
            username : 'agung',
            message : 'Bye',
            pos : 'right',
            time : '11:51'
        },
    ];
    $scope.input_msg = "";
    $scope.picking = "";
    $scope.destination = "";
    $scope.preferredDriver = "";
    $scope.comment = "";

    $scope.showPrefDriver = [];
    $scope.showOtherDriver = [];

    $scope.chosenDriver = null;
    $scope.role = null;
    $scope.rating = 0;

    $scope.selectDestClass = 'button-progress-now';
    $scope.selectDriverClass = 'button-plain';
    $scope.chatDriverClass = 'button-plain';
    $scope.completeOrderClass = 'button-plain';

    function appendMessage(username, msg){
        var time = new Date();
        var string_time = time.getHours() + ':' + time.getMinutes();
        $scope.messages.push({
            username : username,
            message : msg,
            pos : 'right',
            time : string_time
        });
    };

    $scope.sendMessage = function(){
        appendMessage('agung', $scope.input_msg);
        $scope.input_msg = "";
        // alert(angular.element("#chat-container")[0]);
        $timeout(function(){
            angular.element("#chat-container")[0].scrollTop = angular.element("#chat-container")[0].scrollHeight;
        }, 50);
        
    };
    $scope.nextToFindOrder = function(){
        $scope.state = 'finding';
        $scope.role = 'driver';
        $timeout(function(){
            $scope.state = 'finding.got';
            $timeout(function(){
                $scope.state = 'chatting';
            }, 1000);
        }, 2000);
    };

    $scope.cancelFinding = function(){
        $scope.state = 'main';
    };

    $scope.grabDriver = function(){
        $scope.role = 'customer';
        $scope.selectDestClass = 'button-plain';
        $scope.selectDriverClass = 'button-progress-now';
        if($scope.preferredDriver != ""){
            $http({
                url : "../soapservlet",
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
                url : "../soapservlet",
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

        $scope.selectDriverClass = 'button-plain';
        $scope.chatDriverClass = 'button-progress-now';
        $scope.state = 'chatting';
        console.log($scope.chosenDriver + ' ' + $scope.state);
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
                url : "../soapservlet",
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
                    $window.location.reload();
                } else {
                    alert("Error occured");
                }
            }, function error(response){
                console.log(response.statusText);
            });
        } else {
            alert("Please give rating");
        }
    }

    messaging.requestPermission().then(function() {
        console.log("Permission granted");
        return messaging.getToken();
    }).then(function(token) {
        console.log(token);
        $scope.browserToken = token;
    }.bind($scope)).catch(function(err) {
        console.log("Error occured");
    });
    
    messaging.onMessage(function(payload) {
        console.log('onMessage: ', payload);
    });
});