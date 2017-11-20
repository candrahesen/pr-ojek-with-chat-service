var rating = 0;
var destination = null;;
var pickingpoint = null;
var idDriver = 0;
var comment = null;
var driver
function changeTo(element){
    var id = Number(element.id.charAt(7));
    for(var i = 1; i <= 5; i++){
        if(i<=id){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

function changeBack(){
    for(var i=1; i <= 5; i++){
        if(i<=rating){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

function rateThis(element){
    var id = Number(element.id.charAt(7));
    rating = id;
    for(var i = 1; i <= 5; i++){
        if(i<=rating){
            document.getElementById("rating-"+i).style.color = "orange";
        } else {
            document.getElementById("rating-"+i).style.color = "gray";
        }
    }
}

function openOrder(step) {
    var i, taborder, tablinks,active,tabchosen;
    tablinks = document.getElementsByClassName("form-order-default");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace("form-order-default", "form-order");
    }
    active=document.getElementById(step);
	active.className=document.getElementById(step).className.replace("form-order","form-order-default");
	taborder = document.getElementsByClassName("taborder");
	for (i = 0; i < taborder.length; i++) {
		taborder[i].className=taborder[i].className.replace("button-progress-now","button-plain");
	}
    tabchosen = document.getElementById("tab-"+step);
	tabchosen.className=tabchosen.className.replace("button-plain","button-progress-now");
}

document.getElementById('buttonnext').onclick = function(){
    pickingpoint = document.getElementById('pickingpoint').value;
    destination = document.getElementById('destination').value;
	var url = "helpers/ajax/location_validation.php";
	postAjax(url, {dest:destination, loc: pickingpoint}, function(data){
		console.log(data);
		if((data!="salah")&&(pickingpoint!=destination)){
			var preferreddriver = document.getElementById('preferreddriver').value;
			var prefer = document.getElementById('prefer');
			var url1 = 'helpers/ajax/getprefdriver.php?id_active='+idCustomer;
			var url2 = 'helpers/ajax/getdriver.php?id_active='+idCustomer;
			postAjax(url1, {dest:destination, loc: pickingpoint, pref:preferreddriver}, function(data1){
				var createElement = document.getElementById('prefer-driver');
				var prefdetail = JSON.parse(data1);
				var addRow="";
				if(prefdetail.hasOwnProperty('answer')){
					addRow+= "<div id='no-pref'>Nothing to display :(</div>";
				} else {
					if(prefdetail.length>0){
						for(var i = 0; i < prefdetail.length; i++){
							addRow +=  "<div class='row'>";
							addRow += "<div class='col-4'>";
							addRow += "<div class='picture driver-picture'>";
							addRow += "<img src='"+prefdetail[i]['profile_pic_url']+"'>";
							addRow += "</div>";
							addRow += "</div>";
							addRow += "<div class='col-8 driver-detail'>";
							addRow += "<div class='driver-name'>";
							addRow += prefdetail[i]['name'];
							addRow += "</div>";
							addRow += "<div class='driver-rating'>";
							addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>"+prefdetail[i]['rating']+"</span>("+prefdetail[i]['votes']+" votes)";
							addRow += "</div>";
							addRow += "<div class='row'>";
							addRow += "<a href='#' class='button button-success right' id='"+prefdetail[i]['ID']+"' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
							addRow += "</div></div></div>";
						}
					}else{
						addRow+= "<div id='no-pref'>Nothing to display :(</div>";
					}
				}
				createElement.innerHTML=addRow;
			});
			postAjax(url2, {dest:destination, loc: pickingpoint, pref:preferreddriver}, function(data2){
				console.log(data2);
				var driversdetail = JSON.parse(data2);
				var createElement = document.getElementById('other-driver');
				var addRow="";
				if(driversdetail.hasOwnProperty('answer')){
					addRow+= "<div id='no-other-driver'>Nothing to display :(</div>";
				} else {
					if(driversdetail.length>0){
						for(var i = 0; i < driversdetail.length; i++){
							addRow +=  "<div class='row'>";
							addRow += "<div class='col-4'>";
							addRow += "<div class='picture driver-picture'>";
							addRow += "<img src='"+driversdetail[i]['profile_pic_url']+"'>";
							addRow += "</div>";
							addRow += "</div>";
							addRow += "<div class='col-8 driver-detail'>";
							addRow += "<div class='driver-name'>";
							addRow += driversdetail[i]['name'];
							addRow += "</div>";
							addRow += "<div class='driver-rating'>";
							addRow += "<span style='color:orange'>&#9734;</span><span class='rating'>"+driversdetail[i]['rating']+"</span>("+driversdetail[i]['votes']+" votes)";
							addRow += "</div>";
							addRow += "<div class='row'>";
							addRow += "<a href='#' class='button button-success right' id='"+driversdetail[i]['ID']+"' onclick='selectDriver(this)'>I CH0OSE YOU!</a>";
							addRow += "</div></div></div>";
						}
					}else{
						addRow+= "<div id='no-other-driver'>Nothing to display :(</div>";
					}
				}
				createElement.innerHTML=addRow;
			});
			openOrder('select-driver');
		}else{
			var d1 = document.getElementById("pickingpoint");
			d1.className+=" error";
			var d2 = document.getElementById("destination");
			d2.className+=" error";
			document.getElementById('warning-msg-loc').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Location can\'t be found';
			document.getElementById('warning-msg-loc').style.display = 'block';
		}
	});
}

document.getElementById('pickingpoint').onkeyup = function(event){
    var character = event.which;
    if (character>40 || character <37){
        var locNow = document.getElementById('pickingpoint').value;
        var suggestTag = document.getElementById('suggest-pickingpoint');
        if(locNow.length > 0){
            var url = "helpers/ajax/location_searcher.php?location=" + locNow;
            getAjax(url, function(data){
                suggestTag.innerHTML = '';
                var suggestion = JSON.parse(data);
                for(var i = 0; i < suggestion.length; i++){
                    var option = document.createElement("option");
                    option.setAttribute("value", suggestion[i]);
                    suggestTag.appendChild(option);
                }
            });
        } else {
            suggestTag.innerHTML = '';
        }
    }
}
document.getElementById('destination').onkeyup = function(event){
    var character = event.which;
    if (character>40 || character <37){
        var locNow = document.getElementById('destination').value;
        var suggestTag = document.getElementById('suggest-destination');
        if(locNow.length > 0){
            var url = "helpers/ajax/location_searcher.php?location=" + locNow;
            getAjax(url, function(data){
                suggestTag.innerHTML = '';
                var suggestion = JSON.parse(data);
                for(var i = 0; i < suggestion.length; i++){
                    var option = document.createElement("option");
                    option.setAttribute("value", suggestion[i]);
                    suggestTag.appendChild(option);
                }
            });
        } else {
            suggestTag.innerHTML = '';
        }
    }
}

function selectDriver(element){
    idDriver = element.id;
	document.getElementById('modalverifyorder').style.display = "block";
}
// When the user clicks on <span> (x), close the modal
document.getElementById("no-order").onclick = function() {
    document.getElementById('modalverifyorder').style.display = "none";
}
document.getElementById("yes-order").onclick = function() {
    document.getElementById('modalverifyorder').style.display = "none";
	var url= "helpers/ajax/getdriverbyid.php";
	postAjax(url, {iddriver:idDriver}, function(data){
		console.log(data);
		var elementdriverpict = document.getElementById('driver-pict').getElementsByTagName('img')[0];
		var elementdriveruname = document.getElementById('driver-username');
		var elementdrivername = document.getElementById('driver-fullname');
		var driverdetail = JSON.parse(data);
		if(driverdetail.hasOwnProperty('answer')){
			
		} else {
			if(driverdetail.length>0){
				for(var i = 0; i < driverdetail.length; i++){
					elementdriverpict.src = driverdetail[i]['profile_pic_url'];
					elementdriveruname.innerHTML = '@'+driverdetail[i]['username'];
					elementdrivername.innerHTML = driverdetail[i]['name'];
				}
			}
		}
	});
    openOrder("complete-order");
}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == document.getElementById('modalverifyorder')) {
        document.getElementById('modalverifyorder').style.display = "none";
    }
}
document.getElementById('submit-order').onclick = function(){
	var url = "helpers/ajax/submit_order.php";
	if(rating> 0){
		comment = document.getElementById('comment-area').value;
		postAjax(url, {iddriver:idDriver, idcust:idCustomer, dest:destination, loc:pickingpoint, rate:rating, comm:comment}, function(data){
			if(data == "ok"){
				document.getElementById('modalsubmit').style.display = "block";
			} else {
				document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Sorry, an error occured!';
				document.getElementById('warning-msg-submit').style.display = 'block';
			}
		});
	} else {
		document.getElementById('warning-msg-submit').innerHTML = '<span class="closebtn" onclick="this.parentElement.style.display = &quot;none&quot;;">&times;</span>Please enter rating for our driver';
		document.getElementById('warning-msg-submit').style.display = 'block';
	}
}

// When the user clicks on OK, close the modal
document.getElementById("verifysubmit").onclick = function() {
    document.getElementById('modalsubmit').style.display = "none";
	window.location = "order.php?id_active="+idCustomer;
}