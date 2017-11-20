window.onload = function(){
    var inputs = document.getElementsByTagName("input");
    Array.prototype.forEach.call(inputs, function(element) {
        if(element.name!="submit"){
            element.value = "";
        }
    });
}

var nameOK = false;
var userOK = false;
var passOK = false;
var emailOK = false;
var phoneOK = false;

var arr = {
            "name" : nameOK,
            "email" : emailOK,
            "username" : userOK,
            "phone" : phoneOK,
            "password" : passOK
        };
function validateEmail(email, ){
    var emailRegEx = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return emailRegEx.test(email);
}

function validateName(name){
    return (name.length <= 20);
}

function validatePhoneNo(phone){
    var regexPhone = /^[0-9].{8,11}$/;
    return regexPhone.test(phone);
}

for(var item in arr){
    console.log(item);
    document.getElementById(item).onclick = function(){
        document.getElementById("status").innerHTML = "";
        document.getElementById(this.id).style.border = "solid 0.5px gray";
    }
}

document.getElementById("name").onkeyup = function(){
    var name = document.getElementById("name").value;
    if(validateName(name)){
        nameOK = true;
        document.getElementById("name").style.border = "solid 0.5px gray";
    } else {
        nameOK = false;
        document.getElementById("name").style.border = "solid 2px red";
    }
}

document.getElementById("username").onkeyup = function(){
    var username = document.getElementById("username").value;
    var validUser = document.getElementById("username-ok");
    var notValidUser = document.getElementById("username-no"); 
    if (username.length > 0){
        var url = "helpers/ajax/validation.php?username=" + username;
        getAjax(url, function(data){
            if(data == "available"){
                validUser.style.display = "inline";
                notValidUser.style.display = "none";
                userOK = true;
            }
            else {
                notValidUser.style.display = "inline";
                validUser.style.display = "none";
                userOK = false;
            }
        })
    } else {
        notValidUser.style.display = "none";
        validUser.style.display = "none";
        userOK = false;
    }
};

document.getElementById("email").onkeyup = function(){
    var email = document.getElementById("email").value;
    var validEmail = document.getElementById("email-ok");
    var notValidEmail = document.getElementById("email-no");
    
    if (validateEmail(email)){
        var url = "helpers/ajax/validation.php?email=" + email;
        getAjax(url, function(data){
            if(data == "available"){
                validEmail.style.display = "inline";
                notValidEmail.style.display = "none";
                emailOK = true;
            }
            else {
                validEmail.style.display = "none";
                notValidEmail.style.display = "inline";
                emailOK = false;
            }
        });
    } else {
        validEmail.style.display = "none";
        notValidEmail.style.display = "inline";
        emailOK = false;
    }
}

document.getElementById("phone").onkeyup = function(){
    var phone = document.getElementById("phone").value;
    if(validatePhoneNo(phone)){
        phoneOK = true;
        document.getElementById("phone").style.border = "solid 0.5px gray";
    } else {
        phoneOK = false;
        document.getElementById("phone").style.border = "solid 2px red";
    }
}

document.getElementById("confirm-password").onkeyup = function() {
    var pass = document.getElementById("password").value;
    var confirmedPass = document.getElementById("confirm-password").value;
    if (confirmedPass != pass){
        passOK = false;
        document.getElementById("confirm-password").style.border = "solid 2px red";
    } else {
        passOK = true;
        document.getElementById("confirm-password").style.border = "solid 0.5px gray";
    }
}

document.getElementById("signup-form").onsubmit = function (){
    if (nameOK && emailOK && userOK && phoneOK && passOK){
        console.log("form can be submitted");
        return true;
    } else {
        for(var key in arr){
            if(!arr[key]){
                document.getElementById(key).style.border = "solid 2px red";
            }
        }
        document.getElementById("confirm-password").style.border = "solid 0.5px gray";
        document.getElementById("confirm-password").value = "";
        document.getElementById("status").innerHTML = "field must be set";
        console.log("something wrong");
        console.log(nameOK + " " + emailOK + " " + userOK + " " + phoneOK + " " + passOK);
        return false;
    }
}