function validate(element){
    document.getElementById('warning-msg').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>';
	var name = document.getElementById('name').value;
	var phone = document.getElementById('phone').value;

	var nameOK = (name.length <= 20);
	var regexPhone = /^[0-9].{8,11}$/;
    var phoneOK = regexPhone.test(phone);
    if (!nameOK){
        document.getElementById('warning-msg').style.display = 'block';
    	document.getElementById('name').style.border = 'solid 1px red';
        document.getElementById('warning-msg').innerHTML += '<div>Please enter name with no more than 20 char</div>';
    } else {
    	document.getElementById('name').style.border = 'solid 0.5px gray';
    }

    if (!phoneOK){
        document.getElementById('warning-msg').style.display = 'block';
    	document.getElementById('phone').style.border = 'solid 1px red';
        document.getElementById('warning-msg').innerHTML += '<div>Please enter correct value for phone number</div>';
    } else {
    	document.getElementById('phone').style.border = 'solid 0.5px gray';
    }
	return (nameOK && phoneOK);
}