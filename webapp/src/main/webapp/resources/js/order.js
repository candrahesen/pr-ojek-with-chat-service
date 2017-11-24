// var rating = 0;
// var destination = null;;
// var pickingpoint = null;
// var idDriver = 0;
// var comment = null;
// var driver
//
// window.onload = function(){
//     document.getElementById('make-order').style.display = 'block';
//     document.getElementById('find-order').style.display = 'block';
//     // document.getElementById('finding-order').style.display = 'none';
//     document.getElementById('choose-driver').style.display = 'none';
//     document.getElementById('complete-order').style.display = 'none';
//     // document.getElementById('chat-room').style.display = 'none';
// }
//
// function nextToSelectDriver() {
//     var prev = 'select-destination';
//     var next = 'select-driver';
//     document.getElementById(prev).style.display = 'none';
//     document.getElementById(next).style.display = 'block';
//     document.getElementById("tab-"+prev).classList.remove("button-progress-now");
//     document.getElementById("tab-"+next).classList.add("button-progress-now");
//
//     document.getElementById('find-order').style.display = 'none';
// }
// function nextToCompleteOrder(){
//     var prev = 'select-driver'
//     var next = 'complete-order'
//     document.getElementById(prev).style.display = 'none';
//     document.getElementById(next).style.display = 'block';
//     document.getElementById("tab-"+prev).classList.remove("button-progress-now");
//     document.getElementById("tab-"+next).classList.add("button-progress-now");
// }
//
// function nextToFindOrder(){
//     document.getElementById('make-order').style.display = 'none';
//     document.getElementById('find-order').style.display = 'none';
//     document.getElementById('finding-order').style.display = 'block';
//     document.getElementById('get-notif').style.display = 'none';
//
//     setTimeout(function(){gotOrder();}, 2000);
// }
//
// function gotOrder(){
//     document.getElementById('get-notif').style.display = 'block';
//     document.getElementById('finding').style.display = 'none';
//     setTimeout(function(){nextToChat();}, 1000);
// }
//
// function nextToChat(){
//     document.getElementById('finding-order').style.display = 'none';
//     document.getElementById('chat-room').style.display = 'block';
// }
//
// function changeTo(element){
//     var id = Number(element.id.charAt(7));
//     for(var i = 1; i <= 5; i++){
//         if(i<=id){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function changeBack(){
//     for(var i=1; i <= 5; i++){
//         if(i<=rating){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function rateThis(element){
//     var id = Number(element.id.charAt(7));
//     rating = id;
//     for(var i = 1; i <= 5; i++){
//         if(i<=rating){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function openOrder(step) {
//     var i, taborder, tablinks,active,tabchosen;
//     tablinks = document.getElementsByClassName("form-order-default");
//     for (i = 0; i < tablinks.length; i++) {
//         tablinks[i].className = tablinks[i].className.replace("form-order-default", "form-order");
//     }
//     active=document.getElementById(step);
// 	active.className=document.getElementById(step).className.replace("form-order","form-order-default");
// 	taborder = document.getElementsByClassName("taborder");
// 	for (i = 0; i < taborder.length; i++) {
// 		taborder[i].className=taborder[i].className.replace("button-progress-now","button-plain");
// 	}
//     tabchosen = document.getElementById("tab-"+step);
// 	tabchosen.className=tabchosen.className.replace("button-plain","button-progress-now");
// }
//
// function grabDriver(){
//     pickingpoint = document.getElementById('pickingpoint').value;
//     destination = document.getElementById('destination').value;
//     var preferreddriver = document.getElementById('preferreddriver').value;
// 	var url = "/soapservlet";
// 	// if (preferreddriver != "") {
//     //     postAjax(url, {name: "get-driver", dest: destination, loc: pickingpoint, prefDriver: preferreddriver}, function (data1) {
//     //         console.log(data1);
//     //         var createElement = document.getElementById('prefer-driver');
//     //         var prefdetail = JSON.parse(data1);
//     //         var addRow = "";
//     //         if (prefdetail.hasOwnProperty('answer')) {
//     //             addRow += "<div id='no-pref'>Nothing to display :(</div>";
//     //         } else {
//     //             if (prefdetail.length > 0) {
//     //                 for (var i = 0; i < prefdetail.length; i++) {
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<div class='col-4'>";
//     //                     addRow += "<div class='picture driver-picture'>";
//     //                     addRow += "<img src='" + prefdetail[i]['profile_pic_url'] + "'>";
//     //                     addRovar rating = 0;
// var destination = null;;
// var pickingpoint = null;
// var idDriver = 0;
// var comment = null;
// var driver
//
// window.onload = function(){
//     document.getElementById('make-order').style.display = 'block';
//     document.getElementById('find-order').style.display = 'block';
//     // document.getElementById('finding-order').style.display = 'none';
//     document.getElementById('choose-driver').style.display = 'none';
//     document.getElementById('complete-order').style.display = 'none';
//     // document.getElementById('chat-room').style.display = 'none';
// }
//
// function nextToSelectDriver() {
//     var prev = 'select-destination';
//     var next = 'select-driver';
//     document.getElementById(prev).style.display = 'none';
//     document.getElementById(next).style.display = 'block';
//     document.getElementById("tab-"+prev).classList.remove("button-progress-now");
//     document.getElementById("tab-"+next).classList.add("button-progress-now");
//
//     document.getElementById('find-order').style.display = 'none';
// }
// function nextToCompleteOrder(){
//     var prev = 'select-driver'
//     var next = 'complete-order'
//     document.getElementById(prev).style.display = 'none';
//     document.getElementById(next).style.display = 'block';
//     document.getElementById("tab-"+prev).classList.remove("button-progress-now");
//     document.getElementById("tab-"+next).classList.add("button-progress-now");
// }
//
// function nextToFindOrder(){
//     document.getElementById('make-order').style.display = 'none';
//     document.getElementById('find-order').style.display = 'none';
//     document.getElementById('finding-order').style.display = 'block';
//     document.getElementById('get-notif').style.display = 'none';
//
//     setTimeout(function(){gotOrder();}, 2000);
// }
//
// function gotOrder(){
//     document.getElementById('get-notif').style.display = 'block';
//     document.getElementById('finding').style.display = 'none';
//     setTimeout(function(){nextToChat();}, 1000);
// }
//
// function nextToChat(){
//     document.getElementById('finding-order').style.display = 'none';
//     document.getElementById('chat-room').style.display = 'block';
// }
//
// function changeTo(element){
//     var id = Number(element.id.charAt(7));
//     for(var i = 1; i <= 5; i++){
//         if(i<=id){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function changeBack(){
//     for(var i=1; i <= 5; i++){
//         if(i<=rating){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function rateThis(element){
//     var id = Number(element.id.charAt(7));
//     rating = id;
//     for(var i = 1; i <= 5; i++){
//         if(i<=rating){
//             document.getElementById("rating-"+i).style.color = "orange";
//         } else {
//             document.getElementById("rating-"+i).style.color = "gray";
//         }
//     }
// }
//
// function openOrder(step) {
//     var i, taborder, tablinks,active,tabchosen;
//     tablinks = document.getElementsByClassName("form-order-default");
//     for (i = 0; i < tablinks.length; i++) {
//         tablinks[i].className = tablinks[i].className.replace("form-order-default", "form-order");
//     }
//     active=document.getElementById(step);
// 	active.className=document.getElementById(step).className.replace("form-order","form-order-default");
// 	taborder = document.getElementsByClassName("taborder");
// 	for (i = 0; i < taborder.length; i++) {
// 		taborder[i].className=taborder[i].className.replace("button-progress-now","button-plain");
// 	}
//     tabchosen = document.getElementById("tab-"+step);
// 	tabchosen.className=tabchosen.className.replace("button-plain","button-progress-now");
// }
//
// function grabDriver(){
//     pickingpoint = document.getElementById('pickingpoint').value;
//     destination = document.getElementById('destination').value;
//     var preferreddriver = document.getElementById('preferreddriver').value;
// 	var url = "/soapservlet";
// 	// if (preferreddriver != "") {
//     //     postAjax(url, {name: "get-driver", dest: destination, loc: pickingpoint, prefDriver: preferreddriver}, function (data1) {
//     //         console.log(data1);
//     //         var createElement = document.getElementById('prefer-driver');
//     //         var prefdetail = JSON.parse(data1);
//     //         var addRow = "";
//     //         if (prefdetail.hasOwnProperty('answer')) {
//     //             addRow += "<div id='no-pref'>Nothing to display :(</div>";
//     //         } else {
//     //             if (prefdetail.length > 0) {
//     //                 for (var i = 0; i < prefdetail.length; i++) {
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<div class='col-4'>";
//     //                     addRow += "<div class='picture driver-picture'>";
//     //                     addRow += "<img src='" + prefdetail[i]['profile_pic_url'] + "'>";
//     //                     addRow += "</div>";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='col-8 driver-detail'>";
//     //                     addRow += "<div class='driver-name'>";
//     //                     addRow += prefdetail[i]['name'];
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='driver-rating'>";
//     //                     addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>" + prefdetail[i]['rating'] + "</span>(" + prefdetail[i]['votes'] + " votes)";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<a href='#' class='button button-success right' id='" + prefdetail[i]['ID'] + "' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
//     //                     addRow += "</div></div></div>";
//     //                 }
//     //             } else {
//     //                 addRow += "<div id='no-pref'>Nothing to display :(</div>";
//     //             }
//     //         }
//     //         createElement.innerHTML = addRow;
//     //         nextToSelectDriver()
//     //     });
//     // } else {
//     //     postAjax(url, {name: "get-driver", dest: destination, loc: pickingpoint}, function (data2) {
//     //         var driversdetail = JSON.parse(data2);
//     //         var createElement = document.getElementById('other-driver');
//     //         var addRow = "";
//     //         if (driversdetail.hasOwnProperty('answer')) {
//     //             addRow += "<div id='no-other-driver'>Nothing to display :(</div>";
//     //         } else {
//     //             if (driversdetail.length > 0) {
//     //                 for (var i = 0; i < driversdetail.length; i++) {
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<div class='col-4'>";
//     //                     addRow += "<div class='picture driver-picture'>";
//     //                     addRow += "<img src='" + driversdetail[i]['profile_pic_url'] + "'>";
//     //                     addRow += "</div>";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='col-8 driver-detail'>";
//     //                     addRow += "<div class='driver-name'>";
//     //                     addRow += driversdetail[i]['name'];
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='driver-rating'>";
//     //                     addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>" + driversdetail[i]['rating'] + "</span>(" + driversdetail[i]['votes'] + " votes)";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<a href='#' class='button button-success right' id='" + driversdetail[i]['id'] + "' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
//     //                     addRow += "</div></div></div>";
//     //                 }
//     //             } else {
//     //                 addRow += "<div id='no-other-driver'>Nothing to display :(</div>";
//     //             }
//     //         }
//     //         createElement.innerHTML = addRow;
//     //         nextToSelectDriver()
//     //     });
//     // }
//     nextToSelectDriver();
// }
//
// document.getElementById('pickingpoint').onkeyup = function(event){
//     var character = event.which;
//     if (character>40 || character <37){
//         var locNow = document.getElementById('pickingpoint').value;
//         var suggestTag = document.getElementById('suggest-pickingpoint');
//         if(locNow.length > 0){
//             var url = "helpers/ajax/location_searcher.php?location=" + locNow;
//             getAjax(url, function(data){
//                 suggestTag.innerHTML = '';
//                 var suggestion = JSON.parse(data);
//                 for(var i = 0; i < suggestion.length; i++){
//                     var option = document.createElement("option");
//                     option.setAttribute("value", suggestion[i]);
//                     suggestTag.appendChild(option);
//                 }
//             });
//         } else {
//             suggestTag.innerHTML = '';
//         }
//     }
// }
// document.getElementById('destination').onkeyup = function(event){
//     var character = event.which;
//     if (character>40 || character <37){
//         var locNow = document.getElementById('destination').value;
//         var suggestTag = document.getElementById('suggest-destination');
//         if(locNow.length > 0){
//             var url = "helpers/ajax/location_searcher.php?location=" + locNow;
//             getAjax(url, function(data){
//                 suggestTag.innerHTML = '';
//                 var suggestion = JSON.parse(data);
//                 for(var i = 0; i < suggestion.length; i++){
//                     var option = document.createElement("option");
//                     option.setAttribute("value", suggestion[i]);
//                     suggestTag.appendChild(option);
//                 }
//             });
//         } else {
//             suggestTag.innerHTML = '';
//         }
//     }
// }
//
// function selectDriver(element){
//     idDriver = element.id
//     console.log(idDriver)
// 	document.getElementById('modalverifyorder').style.display = "block";
// }
// // When the user clicks on <span> (x), close the modal
// document.getElementById("no-order").onclick = function() {
//     document.getElementById('modalverifyorder').style.display = "none";
// }
// document.getElementById("yes-order").onclick = function() {
//     document.getElementById('modalverifyorder').style.display = "none";
// 	var url= "/soapservlet";
//     console.log(idDriver)
// 	postAjax(url, {name: "get-driver-by-id", iddriver:idDriver}, function(data){
// 		console.log(data);
// 		var elementdriverpict = document.getElementById('driver-pict').getElementsByTagName('img')[0];
// 		var elementdriveruname = document.getElementById('driver-username');
// 		var elementdrivername = document.getElementById('driver-fullname');
// 		var driverdetail = JSON.parse(data);
//         console.log(driverdetail)
// 		elementdriverpict.src = driverdetail['profpicUrl'];
// 		elementdriveruname.innerHTML = '@'+driverdetail['username'];
// 		elementdrivername.innerHTML = driverdetail['name'];
//         nextToCompleteOrder();
// 	});
//     openOrder("complete-order");
// }
// // When the user clicks anywhere outside of the modal, close it
// window.onclick = function(event) {
//     if (event.target == document.getElementById('modalverifyorder')) {
//         document.getElementById('modalverifyorder').style.display = "none";
//     }
// }
// document.getElementById('submit-order').onclick = function(){
// 	var url = "/soapservlet";
// 	if(rating> 0){
// 		comment = document.getElementById('comment-area').value;
// 		postAjax(url, {name: "submit-order", iddriver:idDriver, idcust:idCustomer, dest:destination, loc:pickingpoint, rate:rating, comm:comment}, function(data){
// 			console.log(data)
// 			if(data == "ok"){
// 				document.getElementById('modalsubmit').style.display = "block";
// 			} else {
// 				document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Sorry, an error occured!';
// 				document.getElementById('warning-msg-submit').style.display = 'block';
// 			}
// 		});
// 	} else {
// 		document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Please enter rating for our driver';
// 		document.getElementById('warning-msg-submit').style.display = 'block';
// 	}
// }
//
// // When the user clicks on OK, close the modal
// document.getElementById("verifysubmit").onclick = function() {
//     document.getElementById('modalsubmit').style.display = "none";
// 	window.location = "order";
// }w += "</div>";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='col-8 driver-detail'>";
//     //                     addRow += "<div class='driver-name'>";
//     //                     addRow += prefdetail[i]['name'];
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='driver-rating'>";
//     //                     addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>" + prefdetail[i]['rating'] + "</span>(" + prefdetail[i]['votes'] + " votes)";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<a href='#' class='button button-success right' id='" + prefdetail[i]['ID'] + "' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
//     //                     addRow += "</div></div></div>";
//     //                 }
//     //             } else {
//     //                 addRow += "<div id='no-pref'>Nothing to display :(</div>";
//     //             }
//     //         }
//     //         createElement.innerHTML = addRow;
//     //         nextToSelectDriver()
//     //     });
//     // } else {
//     //     postAjax(url, {name: "get-driver", dest: destination, loc: pickingpoint}, function (data2) {
//     //         var driversdetail = JSON.parse(data2);
//     //         var createElement = document.getElementById('other-driver');
//     //         var addRow = "";
//     //         if (driversdetail.hasOwnProperty('answer')) {
//     //             addRow += "<div id='no-other-driver'>Nothing to display :(</div>";
//     //         } else {
//     //             if (driversdetail.length > 0) {
//     //                 for (var i = 0; i < driversdetail.length; i++) {
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<div class='col-4'>";
//     //                     addRow += "<div class='picture driver-picture'>";
//     //                     addRow += "<img src='" + driversdetail[i]['profile_pic_url'] + "'>";
//     //                     addRow += "</div>";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='col-8 driver-detail'>";
//     //                     addRow += "<div class='driver-name'>";
//     //                     addRow += driversdetail[i]['name'];
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='driver-rating'>";
//     //                     addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>" + driversdetail[i]['rating'] + "</span>(" + driversdetail[i]['votes'] + " votes)";
//     //                     addRow += "</div>";
//     //                     addRow += "<div class='row'>";
//     //                     addRow += "<a href='#' class='button button-success right' id='" + driversdetail[i]['id'] + "' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
//     //                     addRow += "</div></div></div>";
//     //                 }
//     //             } else {
//     //                 addRow += "<div id='no-other-driver'>Nothing to display :(</div>";
//     //             }
//     //         }
//     //         createElement.innerHTML = addRow;
//     //         nextToSelectDriver()
//     //     });
//     // }
//     nextToSelectDriver();
// }
//
// document.getElementById('pickingpoint').onkeyup = function(event){
//     var character = event.which;
//     if (character>40 || character <37){
//         var locNow = document.getElementById('pickingpoint').value;
//         var suggestTag = document.getElementById('suggest-pickingpoint');
//         if(locNow.length > 0){
//             var url = "helpers/ajax/location_searcher.php?location=" + locNow;
//             getAjax(url, function(data){
//                 suggestTag.innerHTML = '';
//                 var suggestion = JSON.parse(data);
//                 for(var i = 0; i < suggestion.length; i++){
//                     var option = document.createElement("option");
//                     option.setAttribute("value", suggestion[i]);
//                     suggestTag.appendChild(option);
//                 }
//             });
//         } else {
//             suggestTag.innerHTML = '';
//         }
//     }
// }
// document.getElementById('destination').onkeyup = function(event){
//     var character = event.which;
//     if (character>40 || character <37){
//         var locNow = document.getElementById('destination').value;
//         var suggestTag = document.getElementById('suggest-destination');
//         if(locNow.length > 0){
//             var url = "helpers/ajax/location_searcher.php?location=" + locNow;
//             getAjax(url, function(data){
//                 suggestTag.innerHTML = '';
//                 var suggestion = JSON.parse(data);
//                 for(var i = 0; i < suggestion.length; i++){
//                     var option = document.createElement("option");
//                     option.setAttribute("value", suggestion[i]);
//                     suggestTag.appendChild(option);
//                 }
//             });
//         } else {
//             suggestTag.innerHTML = '';
//         }
//     }
// }
//
// function selectDriver(element){
//     idDriver = element.id
//     console.log(idDriver)
// 	document.getElementById('modalverifyorder').style.display = "block";
// }
// // When the user clicks on <span> (x), close the modal
// document.getElementById("no-order").onclick = function() {
//     document.getElementById('modalverifyorder').style.display = "none";
// }
// document.getElementById("yes-order").onclick = function() {
//     document.getElementById('modalverifyorder').style.display = "none";
// 	var url= "/soapservlet";
//     console.log(idDriver)
// 	postAjax(url, {name: "get-driver-by-id", iddriver:idDriver}, function(data){
// 		console.log(data);
// 		var elementdriverpict = document.getElementById('driver-pict').getElementsByTagName('img')[0];
// 		var elementdriveruname = document.getElementById('driver-username');
// 		var elementdrivername = document.getElementById('driver-fullname');
// 		var driverdetail = JSON.parse(data);
//         console.log(driverdetail)
// 		elementdriverpict.src = driverdetail['profpicUrl'];
// 		elementdriveruname.innerHTML = '@'+driverdetail['username'];
// 		elementdrivername.innerHTML = driverdetail['name'];
//         nextToCompleteOrder();
// 	});
//     openOrder("complete-order");
// }
// // When the user clicks anywhere outside of the modal, close it
// window.onclick = function(event) {
//     if (event.target == document.getElementById('modalverifyorder')) {
//         document.getElementById('modalverifyorder').style.display = "none";
//     }
// }
// document.getElementById('submit-order').onclick = function(){
// 	var url = "/soapservlet";
// 	if(rating> 0){
// 		comment = document.getElementById('comment-area').value;
// 		postAjax(url, {name: "submit-order", iddriver:idDriver, idcust:idCustomer, dest:destination, loc:pickingpoint, rate:rating, comm:comment}, function(data){
// 			console.log(data)
// 			if(data == "ok"){
// 				document.getElementById('modalsubmit').style.display = "block";
// 			} else {
// 				document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Sorry, an error occured!';
// 				document.getElementById('warning-msg-submit').style.display = 'block';
// 			}
// 		});
// 	} else {
// 		document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Please enter rating for our driver';
// 		document.getElementById('warning-msg-submit').style.display = 'block';
// 	}
// }
//
// // When the user clicks on OK, close the modal
// document.getElementById("verifysubmit").onclick = function() {
//     document.getElementById('modalsubmit').style.display = "none";
// 	window.location = "order";
// }

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

    $scope.showPrefDriver = [];
    $scope.showOtherDriver = [];

    $scope.chosenDriver = null;
    $scope.role = null;

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
                url : "/webapp/soapservlet",
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
                url : "/webapp/soapservlet",
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
});