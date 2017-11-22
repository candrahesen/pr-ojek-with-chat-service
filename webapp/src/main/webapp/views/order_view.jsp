<%@ page import="java.util.Date" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.ojek.common.User" %>
<%
	String accessToken = (String) request.getAttribute("accessToken");
	UserService userService = (UserService) request.getAttribute("userService");
	User user = userService.getUser(accessToken);
%>
            <section id="make-order" ng-show="state == 'main'">
                <div>
                    <h3>MAKE AN ORDER</h3>
                    <ul class="row">
                        <li class="col-4">
                            <button id="tab-select-destination" class="taborder button button-progress-now row button-disable" disabled>
                                <div class="circle-numbering col-3">1</div>
                                Select Destination
                            </button>
                        </li>
                        <li class="col-4">
                            <button id="tab-select-driver" class="taborder button button-plain row button-disable" disabled>
                                <div class="circle-numbering col-3">2</div>
                                Select a Driver
                            </button>
                        </li>
                        <li class="col-4">
                            <button id="tab-complete-order" class="taborder button button-plain row button-disable" disabled>
                                <div class="circle-numbering col-3">3</div>
                                Complete your order
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="container" id="select-destination" style="display: block">
                    <div class="form-order-default">
                        <div class="row">
                            <div class="col-5">
                                Picking Point
                            </div>
                            <label>
                                <input class="col-7 input-standard" autocorrect="off" autocomplete="off" name="pickingpoint" placeholder="Fill A Place"
                                            id="pickingpoint" type="text" size="30" list="suggest-pickingpoint">
                                <datalist id="suggest-pickingpoint"></datalist>
                            </label>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                    Destination
                            </div>
                            <label>
                                <input class="col-7 input-standard" autocorrect="off" autocomplete="off" name="destination" placeholder="Fill A Place"
                                            id="destination"  type="text" size="30" list="suggest-destination">
                                <datalist id="suggest-destination"></datalist>
                            </label>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                Preferred Driver
                            </div>
                            <input class="col-7 input-standard" name="preferreddriver" placeholder="(optional)"
                                        id="preferreddriver"  type="text" size="30">
                        </div>
                        <div style="text-align: center; margin: 15px 0px;">
                            <div class="button button-success" onclick="grabDriver()">NEXT!</div>
                        </div>
                        <div class="row warning-box" id="warning-msg-loc" style='display: none;'>
                        </div>
                    </div>
                </div>
            </section>
            <section id="find-order" ng-show="state == 'main'">
                <div>
                    <h3>LOOKING FOR AN ORDER</h3>
                    <div style="text-align: center">
                        <button class="button button-success" ng-click="nextToFindOrder()">FIND ORDER</button>    
                    </div>
                </div>
            </section>
            <section id="finding-order" ng-show="state == 'finding'">
                <div>
                    <h3>LOOKING FOR AN ORDER</h3>
                    <div style="text-align: center" id="finding">
                        <p style="font-weight: bold">Finding Order...</p>
                        <div class="loader"></div>
                        <button class="button button-fail" ng-click="cancelFinding()">CANCEL</button>    
                    </div>
                    <div class="alert-box success" id="get-notif">
                        <span>Okay! </span>We got you an order.
                    </div>
                </div>
            </section>
            <section id="choose-driver" ng-show="state == 'choosing'">
                <div class="form-order" id="select-driver" style="display: none">
                    <div class="container rounded-border" id="thereprefdriver">
                        <h2>PREFERRED DRIVERS:</h2>
                        <div id="prefer-driver">
                        <!-- display preferred driver after button next clicked--> 
                        </div>
                    </div>
                    <div class="container rounded-border">
                        <h2>OTHER DRIVERS:</h2>
                        <div id="other-driver">
                        <!-- display drivers w/ our picking point as preferred loc after button next clicked-->
                        </div>
                    </div>
                    <div id="modalverifyorder" class="modalview">
                              <!-- Modal content -->
                        <div class="modal-content">
                            <div class="modal-text">Are you sure?</div>
                            <div class="modal-options">
                                <a class="button button-fail" id="no-order">NO</a>
                                <a class="button button-success" id="yes-order">YES</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
            <section id="complete-order" ng-show="state == 'completing'">
                <div class="form-order" id="complete-order" style="display: none">
                    <h3>HOW WAS IT?</h3>
                    <div class="row">
                        <div class="col-4">
                        </div>
                        <div class="col-4">
                            <div class="row">
                                <div class="picture center profile-picture" id="driver-pict">
                                    <img src="assets/images/pikachu.png" alt="Pikachuu">
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                        </div>
                    </div>
                    <div class="row profile-info">
                        <div class="row">
                            <span class="username" id="driver-username">
                                boom
                            </span>
                        </div>
                        <div class="row">
                            <span class="full-name" id="driver-fullname">
                                boom
                            </span>
                        </div>
                    </div>
                    <div class="container">
                        <div class="container">
                            <ul class="rating-list row">
                                <li class="rating-element" id="rating-1" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-2" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-3" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-4" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)"><i class="material-icons">star_rate</i></li>
                                <li class="rating-element" id="rating-5" onmouseover="changeTo(this)" onmouseout="changeBack()" onclick="rateThis(this)"><i class="material-icons">star_rate</i></li>
                             </ul>
                        </div>
                        <div class="container">
                            <textarea rows="4" cols="50" id="comment-area" placeholder="Your comment..."></textarea>
                        </div>
                        <div class="container row">
                            <a class="button button-success" id="submit-order" type="submit" name="submit" style="float: right;">COMPLETE ORDER</a>
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
				var idCustomer = <%= user.getId() %>
			</script>
            <% long ts = (new Date()).getTime(); //Used to prevent JS/CSS caching %>
			<script type="text/javascript" src="./resources/js/order.js?<%=ts%>"></script>
			<script type="text/javascript" src="./resources/js/ajax.js?<%= ts %>"></script>

            <%--<script type="text/javascript" src="assets/js/ajax.js"></script>--%>
            <%--<script type="text/javascript" src="assets/js/order.js"></script>--%>