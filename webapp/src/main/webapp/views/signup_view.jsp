<!DOCTYPE html>
<html>
    <head>
        <title> Sign Up</title>
        <link rel="stylesheet" href="./resources/css/style.css">
        <link rel="stylesheet" href="./resources/css/element.css">
    </head>
    <body>
        <div class="login-extern-container">
        <div class="row">
            <div class="col-4 align-center">
                <hr class="loginhr">
            </div>
            <div class="col-4 align-center">
                <h3 class="headstyle">Signup</h3>
            </div>
            <div class="col-4 align-center">
                <hr class="loginhr">
            </div>
        </div>
        <div class="signupcontainer">
            <div class="row">
                <form action="" method="POST" accept-charset="utf-8" id="signup-form">
                    <div  class="row standard" >
                        <div class="col-5">
                            <p class="loginform">Your Name</p>
                        </div>
                        <input id="name" class="col-7 input-standard" name="name">
                    </div>
                    <div  class="row standard" >
                        <div class="col-5">
                            <p class="loginform">Username</p>
                        </div>
                        <input id="username" class="col-6 input-standard" name="username">
                        <span id="username-ok"><i class="material-icons">&#xE5CA;</i></span>
                        <span id="username-no"><i class="material-icons">&#xE14C;</i></span>

                    </div>
                    <div  class="row standard" >
                        <div class="col-5">
                            <p class="loginform">Email</p>
                        </div>
                        <input id="email" class="col-6 input-standard" name="email">
                        <span id="email-ok"><i class="material-icons">&#xE5CA;</i></span>
                        <span id="email-no"><i class="material-icons">&#xE14C;</i></span>

                    </div>
                    <div class="row standard" >
                        <div class="col-5">
                            <p class="loginform">Password</p>
                        </div>
                        <input id="password" class="col-7 input-standard" name="password" type="password">
                    </div>
                    <div class="row standard">
                        <div class="col-5">
                            <p class="loginform">Confirm Password</p>
                        </div>
                        <input id="confirm-password" class="col-7 input-standard" name="confirm_password" type="password">
                    </div>
                    <div class="row standard">
                        <div class="col-5">
                            <p class="loginform">Phone Number</p>
                        </div>
                        <input id="phone" class="col-7 input-standard" name="phone">
                    </div>
                    <div class="row standard">
                        <div class="col-12 loginform">
                            <input type="checkbox" name="driver" value="on"> Also sign me up as driver!<br>
                        </div>
                    </div>
                    <div class="row margin-top">
                        <div class="col-6">
                            <%--<a href="http://<?php echo $_SERVER['HTTP_HOST']?>/login.php"> <u>Already have an account?</u></a>--%>
                            <br><span class="warning" id="status"></span>
                        </div>
                        <div class="col-6">
                            <input class="button button-green right" type="submit" name="submit" value="REGISTER">
                        </div>
                    </div>
                    <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="row warning-box" id="warning-msg">
                        <span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>
                        <%= request.getAttribute("errorMessage") %>
                    </div>
                    <% } %>
                </form>
            </div>
        </div>
    </body>
</html>

