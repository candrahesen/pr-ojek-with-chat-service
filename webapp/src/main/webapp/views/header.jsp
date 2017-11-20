<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%@ page import="com.ojek.common.User" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="java.util.Date" %>
<%
    String accessToken = (String) request.getAttribute("accessToken");
    Config conf = (Config) request.getAttribute("config");
    UserService userService = (UserService) request.getAttribute("userService");
    User user = userService.getUser(accessToken);
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
    </head>
    <body>
        <div class="default-extern-container">
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