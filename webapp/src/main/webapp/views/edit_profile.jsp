<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%@ page import="com.ojek.common.User" %>
<%
    String accessToken = (String) request.getAttribute("accessToken");
    UserService userService = (UserService) request.getAttribute("userService");
    Config conf = (Config) request.getAttribute("config");

    User user = userService.getUser(accessToken);
    if (user == null)
        user = new User();
%>
            <div class="row profile">
                <h2>Edit Profile Information</h2>
                <form action="" method="POST" accept-charset="utf-8" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-4">
                            <div class="row">
                                <div class="picture edit-picture">
                                    <img src="<%= user.getProfpicUrl() %>" alt="<%= user.getName() %>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-8 margin-top">
                            Update profile picture :
                            <div class="row">
                                <input class="col-8 input-standard upload-bar" id="uploadFile" placeholder="Choose File" disabled="disabled" />
                                <div class="file-upload col-4 button-upload">
                                    <span>Browse..</span>
                                    <input id="uploadBtn" name="uploaded_file" type="file" class="upload" />
                                </div>
                                <span class="warning"><?php echo $status; ?></span>
                            </div>
                        </div>
                    </div>
                    <div class="row margin-top">
                        <div class="row">
                            <div class="col-4">
                                Your Name
                            </div>
                            <div class="col-8">
                                <input class="input-standard input-standard-v2" type="text" name="name" id='name' placeholder="Your name..." value="<%= user.getName() %>">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                Phone
                            </div>
                            <div class="col-8">
                                <input class="input-standard input-standard-v2" type="text" name="phone" id='phone' placeholder="Your phone.." value="<%= user.getPhone() %>">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                Status Driver
                            </div>
                            <div class="col-8">
                                <label class="switch">
                                <input type="checkbox" name="driver" <% if(user.getDriver() != null){ %>checked<% } %> >
                                <span class="slider"></span>
                                </label>
                            </div>
                        </div>
                        <div class="row margin-top">
                            <div class="col-2">
                                <a href="<%= conf.getBaseUrl() %>profile" class="button button-fail">Back</a>
                            </div>
                            <div class="col-8">
                            </div>
                            <div class="col-2">
                                <input class="button button-success" type="submit" name="submit" value="Save">
                            </div>
                        </div>
                        <div class="row warning-box" id="warning-msg" style='display: none;'>
                        </div>
                    </div>
                </form>
            </div>
            <script> 
//                var id = <?php echo $ID; ?>;
            </script>
            <%--<script type='text/javascript' src='../assets/js/ajax.js'></script>--%>
            <%--<script type='text/javascript' src='../assets/js/upload_profile_picture.js'></script>--%>
            <%--<script type="text/javascript" src="http://<?php echo $_SERVER['HTTP_HOST']; ?>/assets/js/edit_profile.js"></script>--%>