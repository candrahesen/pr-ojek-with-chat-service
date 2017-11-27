<%@ page import="com.ojek.common.User" %>
<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%@ page import="com.ojek.common.Location" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.jauharteam.ojek.ojek.LocationService" %>
<%
    String accessToken = (String) request.getAttribute("accessToken");
    UserService userService = (UserService) request.getAttribute("userService");
    LocationService locationService = (LocationService) request.getAttribute("locationService");
    Config conf = (Config) request.getAttribute("config");

    User user = userService.getUser(accessToken);
    if (user == null)
        user = new User();
%>
<div class="row profile">
    <h3>My Profile</h3>
    <div class="row">
        <div class="col-4">
        </div>
        <div class="col-4">
            <div class="row">
                <div class="picture center profile-picture">
                    <img src="<%= user.getProfpicUrl() %>" alt="<%= user.getName() %>"/>
                </div>
            </div>
        </div>
        <div class="col-4">
            <div class="right">
                <a href="<%= conf.getBaseUrl() %>editprofile" class="edit-icon"><i
                        class="material-icons">&#xE254;</i></a>
            </div>
        </div>
    </div>
    <div class="row profile-info">
        <div class="row">
            <span class="username">
                <%= user.getUsername() %>
            </span>
        </div>
        <div class="row">
            <span class="full-name">
                <%= user.getName() %>
            </span>
        </div>
        <div class="row">
            <% if (user.getDriver() != null) {%>
            <span class="status">Driver</span> | <span style="color:orange">&#9734;</span><span
                class="rating"><% out.print(String.format("%.2f", (double) user.getRating()/ (double) user.getVotes())); %></span>
            (<span class="votes"><%= user.getVotes() %></span>)
            <% } %>
        </div>
        <div class="row">
            <span class="material-icons" style="font-size: 10pt">&#xe0e1;</span>
            <span> </span><span class="email"><%= user.getEmail() %></span>
        </div>
        <div class="row">
            <span>&#9742;</span><span> </span><span class="phone"><%= user.getPhone() %></span>
        </div>
    </div>
    <% if (user.getDriver() != null) { %>
    <div class="row">
        <div class="right">
            <a href="<%= conf.getBaseUrl() %>editlocation"
               class="edit-icon"><i class="material-icons">&#xE254;</i></a>
        </div>
        <h4>Preferred Locations :</h4>
        <ul class="scrollable">
            <%
                int margin_left = 0;
                Location[] locations = locationService.getAllLocation(accessToken);
                if (locations.length > 0) {
                    for (Location location : locations) {
            %>
            <li style="margin-left:<%= margin_left+=10 %>px">
                <i class="material-icons" style="font-size:16px">navigate_next</i>
                <%= location.getLocation() %>
            </li>
            <%
                    }
                } else {
            %>
            <li>You have no preferred location yet :(</li>
            <%
                }
            %>
        </ul>
    </div>
    <% } %>
</div>

