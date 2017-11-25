<%@ page import="java.util.Date" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.ojek.common.User" %>
<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%
    String accessToken = (String) request.getAttribute("accessToken");
    UserService userService = (UserService) request.getAttribute("userService");
    User user = userService.getUser(accessToken);
%>

<%
    Config conf = (Config) request.getAttribute("config");
    String path = request.getRequestURI();
    path = path.replaceAll("\\/([a-zA-Z0-9]+)\\.jsp", "$1");
%>
<%
    long ts = (new Date()).getTime(); //Used to prevent JS/CSS caching
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order PR-Ojek</title>
    <link rel="stylesheet" href="./resources/css/style.css?<%= ts %>" >
    <link rel="stylesheet" href="./resources/css/element.css?<%= ts %>">
    <link rel="stylesheet" href="./resources/css/order.css?<%= ts %>">
    <script src="https://www.gstatic.com/firebasejs/4.6.2/firebase.js"></script> 
    <script src="https://www.gstatic.com/firebasejs/4.6.2/firebase-messaging.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    <meta charset="utf-8">
</head>
<body>
<div class="default-extern-container" ng-app="orderApp" ng-controller="appController">
    <div class="row">
        <div class="col-6">
            <h1 class="logo"><span style="color: #034E03;">PR</span>-<span style="color:red;">OJEK</span></h1>
            <h2 class="tagline" style="color: #468D03;">wushh... wushh... ngeeeeeenggg...</h2>
        </div>
        <div class="col-6 align-right">
            <p>Hello, <%= user.getUsername() %> !</p>
            <a href="<%= conf.getBaseUrl() %>logout">Logout</a>
        </div>
    </div>
    <ul class="row navbar">
        <li class="col-4 align-center standard-border <% if(path.equals("order")) {%>active<%}%>"><a href="<%= conf.getBaseUrl() %>order">Order</a></li>
        <li class="col-4 align-center standard-border <% if(path.equals("history")) {%>active<%}%>"><a href="<%= conf.getBaseUrl() %>history">History</a></li>
        <li class="col-4 align-center standard-border <% if(path.equals("profile") || path.equals("editprofile") || path.equals("editlocation")) {%>active<%}%>"><a href="<%= conf.getBaseUrl() %>profile">My Profile</a></li>
    </ul>
            <section id="progress-bar" ng-show="state == 'main' || role == 'customer'">
                <div>
                    <h3>MAKE AN ORDER</h3>
                    <ul class="row">
                        <li class="col-3">
                            <button id="tab-select-destination" class="taborder button row button-disable" disabled ng-class='selectDestClass'>
                                <div class="circle-numbering col-3">1</div>
                                Select Dest.
                            </button>
                        </li>
                        <li class="col-3">
                            <button id="tab-select-driver" class="taborder button row button-disable" disabled ng-class='selectDriverClass'>
                                <div class="circle-numbering col-3">2</div>
                                Select a Driver
                            </button>
                        </li>
                        <li class="col-3">
                            <button id="tab-complete-order" class="taborder button row button-disable" disabled ng-class="chatDriverClass">
                                <div class="circle-numbering col-3">3</div>
                                Chat Driver
                            </button>
                        </li>
                        <li class="col-3">
                            <button id="tab-complete-order" class="taborder button row button-disable" disabled ng-class="completeOrderClass">
                                <div class="circle-numbering col-3">4</div>
                                Compl. order
                            </button>
                        </li>
                    </ul>
                </div>
            </section>
            <section id="make-order" ng-show="state == 'main'">
                <div class="container" id="select-destination" style="display: block">
                    <div class="form-order-default">
                        <div class="row">
                            <div class="col-5">
                                Picking Point
                            </div>
                            <label>
                                <input class="col-7 input-standard" autocorrect="off" autocomplete="off" name="pickingpoint" placeholder="Fill A Place"
                                            id="pickingpoint" type="text" size="30" ng-model="picking">
                            </label>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                    Destination
                            </div>
                            <label>
                                <input class="col-7 input-standard" autocorrect="off" autocomplete="off" name="destination" placeholder="Fill A Place"
                                            id="destination"  type="text" size="30" ng-model="destination">
                            </label>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                Preferred Driver
                            </div>
                            <input class="col-7 input-standard" name="preferreddriver" placeholder="(optional)"
                                        id="preferreddriver"  type="text" size="30" ng-model="preferredDriver">
                        </div>
                        <div style="text-align: center; margin: 15px 0px;">
                            <div class="button button-success" ng-click="grabDriver()">NEXT!</div>
                        </div>
                        <div class="row warning-box" id="warning-msg-loc" style='display: none;'>
                        </div>
                    </div>
                </div>
            </section>
            <section id="find-order" ng-show="state == 'main' && isDriver">
                <div>
                    <h3>LOOKING FOR AN ORDER</h3>
                    <div style="text-align: center">
                        <button class="button button-success" ng-click="nextToFindOrder()">FIND ORDER</button>
                    </div>
                </div>
            </section>
            <section id="finding-order">
                <div ng-show="state == 'finding'">
                    <h3>LOOKING FOR AN ORDER</h3>
                    <div style="text-align: center" id="finding">
                        <p style="font-weight: bold">Finding Order...</p>
                        <div class="loader"></div>
                        <button class="button button-fail" ng-click="cancelFinding()">CANCEL</button>
                    </div>
                </div>
                <div class="alert-box success" id="get-notif" ng-show="state == 'finding.got'">
                    <span>Okay! </span>We got you an order.
                </div>
            </section>
            <section id="choose-driver" ng-show="state == 'choosing'">
                <div  id="select-driver">
                    <div class="container rounded-border" id="thereprefdriver">
                        <h2>PREFERRED DRIVERS:</h2>
                        <div id="prefer-driver">
                        <!-- display preferred driver after button next clicked-->
                            <div id='no-pref' ng-show="showPrefDriver.length == 0">
                                Nothing to display :(
                            </div>
                            <div class='row' ng-repeat="driver in showPrefDriver">
                                <div class='col-4'>
                                    <div class='picture driver-picture'>
                                        <img src='{{driver.profile_pic_url}}' alt="PR-Ojek">
                                    </div>
                                </div>
                                <div class='col-8 driver-detail'>
                                    <div class='driver-name'>
                                        {{driver.name}}
                                    </div>
                                    <div class='driver-rating'>
                                            <span style='color:orange'>&#9734;</span>
                                            <span class='rating'> {{driver.rating}}</span>({{driver.votes}} votes)
                                    </div>
                                    <div class='row'>
                                            <a href='#' class='button button-success right' id='{{driver.id}}' data-ng-click='selectDriver($event)'>I CH0OSE YOU!</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="container rounded-border">
                        <h2>OTHER DRIVERS:</h2>
                        <div id="other-driver">
                        <!-- display drivers w/ our picking point as preferred loc after button next clicked-->
                            <div id='no-pref' ng-show="showOtherDriver.length == 0">
                                Nothing to display :(
                            </div>
                            <div class='row' ng-repeat="driver in showOtherDriver">
                                <div class='col-4'>
                                    <div class='picture driver-picture'>
                                        <img src='{{driver.profile_pic_url}}' alt="PR-Ojek">
                                    </div>
                                </div>
                                <div class='col-8 driver-detail'>
                                    <div class='driver-name'>
                                        {{driver.name}}
                                    </div>
                                    <div class='driver-rating'>
                                            <span style='color:orange'>&#9734;</span>
                                            <span class='rating'> {{driver.rating}}</span>({{driver.votes}} votes)
                                    </div>
                                    <div class='row'>
                                            <a href='#' class='button button-success right' id='{{driver.id}}' data-ng-click='selectDriver($event)'>I CH0OSE YOU!</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <div id="modalverifyorder" class="modalview" ng-style="modalVerifiyOrder">
                    <!-- Modal content -->
              <div class="modal-content">
                  <div class="modal-text">Are you sure?</div>
                  <div class="modal-options">
                      <a class="button button-fail" id="no-order">NO</a>
                      <a class="button button-success" id="yes-order">YES</a>
                  </div>
              </div>
          </div>
            <section id="chat-room" ng-show="state == 'chatting'">
                <div ng-show="role == 'driver'">
                    <h3>LOOKING FOR AN ORDER</h3>
                    <div class="centering-parent">
                        <div class="green-banner">
                            Got an Order!
                        </div>
                    </div>
                    <div class="centering-parent">
                        <div style="font-weight: bold; margin: auto;">
                            @username
                        </div>
                    </div>
                </div>
                <div class="panel-body msg_container_base" id="chat-container">
                    <div ng-repeat="msg in messages">
                        <div ng-if="msg.pos == 'left'">
                            <div class="row msg_container base_receive">
                                <div class="col-2 col-2 avatar">
                                    <img src="resources/images/user.png" class="img-responsive ">
                                </div>
                                <div class="col-10 col-10">
                                    <div class="messages msg_receive chat-left">
                                        <p>{{msg.message}}</p>
                                        <time datetime="2009-11-13T20:00">{{msg.username}} - {{msg.time}}</time>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div ng-if="msg.pos == 'right'">
                            <div class="row msg_container base_sent">
                                <div class="col-10 col-10 ">
                                    <div class="messages msg_sent chat-right">
                                        <p>{{msg.message}}</p>
                                        <time datetime="2009-11-13T20:00">{{msg.username}} - {{msg.time}}</time>
                                    </div>
                                </div>
                                <div class="col-2 col-2 avatar">
                                    <img src="resources/images/user.png" class=" img-responsive ">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer" style="margin: 5px 0px;">
                    <div class="input-group row">
                        <input id="btn-input" type="text" class="chat_input col-9" placeholder="Write your message here..." ng-model="input_msg" ng-enter="sendMessage()"/>
                        <div class="col-3" style="padding: 0px">
                            <div id="btn-chat" class="send-button" ng-click="sendMessage()">Send</div>
                        </div>
                    </div>
                </div>
                <div ng-show="role == 'customer'" class="centering-parent">
                    <div id="btn-chat" class="close-button" ng-click="closeChat()">CLOSE</div>
                </div>
            </section>
            <section id="complete-order" ng-show="state == 'completing'">
                <div id="complete-order">
                    <h3>HOW WAS IT?</h3>
                    <div class="row">
                        <div class="col-4">
                        </div>
                        <div class="col-4">
                            <div class="row">
                                <div class="picture center profile-picture" id="driver-pict">
                                    <img src="{{chosenDriver.profile_pic_url}}" alt="{{chosenDriver.username}}">
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                        </div>
                    </div>
                    <div class="row profile-info">
                        <div class="row">
                            <span class="username" id="driver-username">
                                {{chosenDriver.username}}
                            </span>
                        </div>
                        <div class="row">
                            <span class="full-name" id="driver-fullname">
                                {{chosenDriver.name}}
                            </span>
                        </div>
                    </div>
                    <div class="container">
                        <div class="container">
                            <ul class="rating-list row">
                                <li class="rating-element" id="rating-1" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)" ng-click="rate(1)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-2" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)" ng-click="rate(2)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-3" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)" ng-click="rate(3)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-4" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)" ng-click="rate(4)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-5" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)" ng-click="rate(5)"><i class="material-icons">star_rate</i></li>
                             </ul>
                        </div>
                        <div class="container">
                            <textarea rows="4" cols="50" id="comment-area" placeholder="Your comment..." ng-model="comment"></textarea>
                        </div>
                        <div class="container row">
                            <a class="button button-success" id="submit-order" style="float: right;" ng-click="completeOrder()">COMPLETE ORDER</a>
                        </div>
                        <div class="row warning-box" id="warning-msg-submit" style='display: none;'>
                        </div>
                    </div>
                </div>
            </section>
			<div id="modalsubmit" class="modalview">
							  <!-- Modal content -->
				<div class="modal-content">
					<div class="modal-text">ORDER SUCCESS!!!! YEAY!!!!</div>
					<div class="modal-options">
						<a class="button button-success" id="verifysubmit" >OK</a>
					</div>
				</div>
			</div>

			<script>
                var idCustomer = <%= user.getId() %>;
                var isDriver = <%= user.getDriver() %>;
			</script>
			<script type="text/javascript" src="./resources/js/order.js?<%=ts%>"></script>
			<script type="text/javascript" src="./resources/js/ajax.js?<%= ts %>"></script>

            <%--<script type="text/javascript" src="assets/js/ajax.js"></script>--%>
            <%--<script type="text/javascript" src="assets/js/order.js"></script>--%>


        </div>
    </body>
</html>