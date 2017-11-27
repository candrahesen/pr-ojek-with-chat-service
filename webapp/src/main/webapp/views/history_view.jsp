<%@ page import="java.util.Date" %>
<%@ page import="com.ojek.common.User" %>
<%@ page import="com.ojek.common.util.StringUtil" %>
<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.jauharteam.ojek.ojek.OrderService" %>
<%@ page import="com.jauharteam.ojek.ojek.LocationService" %>
<%@ page import="com.ojek.common.Order" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    String accessToken = (String) request.getAttribute("accessToken");
    OrderService orderService = (OrderService) request.getAttribute("orderService");
    UserService userService = (UserService) request.getAttribute("userService");
    LocationService locationService = (LocationService) request.getAttribute("locationService");
    Config conf = (Config) request.getAttribute("config");

    User user = userService.getUser(accessToken);
    Order[] historyDriver = orderService.getAllOrderDriver(accessToken);
    Order[] historyCustomer= orderService.getAllOrderCustomer(accessToken);

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
%>

            <div class="row">
                <h3>Transaction History</h3>
                <ul class="row navbar">
                    <li class="col-6 align-center standard-border active" id="previous-order-tab">
                        <a onclick="toPreviousOrder()">My Previous Order</a>
                    </li>
                    <li class="col-6 align-center standard-border" id="driver-history-tab">
                        <a onclick="toDriverHistory()">Driver History</a>
                    </li>
                </ul>
            </div>
            <section id="driver-history">

                <% int driverHistory = 0; %>

                <div id="history-exist" style="display: <%= historyDriver.length>0?"block":"none" %>" class="scrollable">

                    <% for (Order order1 : historyDriver ){ %>
                    <% driverHistory++; %>

                    <div id="history-<%= order1.getId()%>" class="row" style="<%= order1.getDriverShow()?"block":"none" %>">
                        <div class='col-3'>
                            <div class='picture driver-picture'>
                                <img src="<%= userService.getUserById(accessToken, order1.getCustomerId()).getProfpicUrl() %>" alt='no file'>
                            </div>
                        </div>

                         <div class='col-8 driver-detail'>
                            <div class='date'>
                                <% 
                                    Date time_driver = new Date(order1.getTime().getTime());
                                    out.print(df.format(time_driver));
                                %>
                            </div>
                            <div class='driver-name'>
                                <%= userService.getUserById(accessToken, order1.getCustomerId()).getName()%>
                            </div>
                            <div class="text">
                                gave <span class="rating"> <%= order1.getRate() %> </span> stars for this order
                            </div>
                            <div class="text">
                                <span class="text"> <%= locationService.getLocationById(accessToken, order1.getLocationId()).getLocation()%> </span>&rarr;<span class="text"> <%= locationService.getLocationById(accessToken, order1.getDestinationId()).getLocation()%> </span>
                            </div>
                            <div class="text">
                                and left comment:
                            </div>
                            <div class="comment">
                                <%= order1.getComment() %>
                            </div>
                        </div>
                        <div class="col-1 ">
                            <button class="button button-fail right" onclick="hideDriverHistory('<%= conf.getBaseUrl() %>', <%= order1.getId() %>)">HIDE!</button>
                        </div>
                    <% } %>

                </div>
                <div id="no-history" class="margin-top" style="display : <%= driverHistory<=0?"block":"none"%>">
                    No transaction yet!
                </div>
            </section>

            <section id="previous-order">

                <%int customerHistory = 0; %>

                <div id="previous-exist" style="display: <%= historyCustomer.length>0?"block":"none"%>" class="scrollable">

                <% for (Order order2 : historyCustomer ){ %>
                <% System.out.println("order2 : " + order2); %>
                <% customerHistory++; %>

                    <div id="previous-<%= order2.getId() %>" class="row" style="<%= order2.getCustomerShow()?"block":"none" %>">
                        <div class='col-3'>
                            <div class='picture driver-picture'>
                                <img src="<%= userService.getUserById(accessToken, order2.getDriverId()).getProfpicUrl() %>" alt='no file'>
                            </div>
                        </div>
                        <div class='col-8 driver-detail'>
                            <div class='date'>
                                <% 
                                    Date time = new Date(order2.getTime().getTime());
                                    out.print(df.format(time));
                                %>
                            </div>
                            <div class='driver-name'>
                                <%= userService.getUserById(accessToken, order2.getDriverId()).getName() %>
                            </div>
                            <div class="text">
                                <span class="text"> <%= locationService.getLocationById(accessToken, order2.getLocationId()).getLocation()%> </span>&rarr;<span class="text"> <%= locationService.getLocationById(accessToken, order2.getDestinationId()).getLocation()%> </span>
                            </div>
                            <div class="row">
                                <div class="col-3" style="padding-left:0px;" >
                                    <div class="text">You rated:</div>
                                </div>
                                <% for (int i = 0; i < order2.getRate(); i++){ %>
                                    <span class="rating" id="driverrating">&#9734;</span>
                                <% } %>
                            </div>
                            <div class="text">
                                You commented:
                            </div>
                            <div class="comment">
                                <%= order2.getComment() %>
                            </div>
                        </div>
                        <div class="col-1 ">
                            <button class="button button-fail right" onclick="hidePreviousHistory('<%= conf.getBaseUrl() %>',<%= order2.getId()%>)">HIDE!</button>
                        </div>
                    </div>
                <% } %>

                </div>
                <div id="no-previous" class="margin-top" style="display: <%= historyCustomer.length<=0?"block":"none"%>">
                    No transaction yet!
                </div>
            </section>

            <% long ts = (new Date()).getTime(); //Used to prevent JS/CSS caching %>
            <script type="text/javascript" src="./resources/js/history.js?<%=ts%>"></script>
            <script type="text/javascript" src="./resources/js/ajax.js?<%=ts%>"></script>

            <%--<script>--%>
                <%--var nbDriver = <?php echo $nbHistory; ?>;--%>
                <%--var nbMyPrev = <?php echo $nbPrev; ?>;--%>
            <%--</script>--%>
            <%--<script type="text/javascript" src="assets/js/ajax.js"></script>--%>
            <%--<script type="text/javascript" src="assets/js/history.js"></script>--%>