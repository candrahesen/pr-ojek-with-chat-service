window.onload = function(){
    document.getElementById("previous-order").style.display = "block";
    document.getElementById("driver-history").style.display = "none";
}

function hideDriverHistory(id){
    var url = "helpers/ajax/hide_driver_history.php";
    postAjax(url, {id : id}, function(data){
        if (data == true){
            document.getElementById('history-' + id).style.display = "none";
            nbDriver--;
            if(nbDriver==0){
                document.getElementById('history-exist').style.display = "none";
                document.getElementById("no-history").style.display = "block";
            }
        }
    })
}

function hidePreviousHistory(id){
    var url = "helpers/ajax/hide_previous_history.php";
    postAjax(url, {id : id}, function(data){
        if (data == true){
            document.getElementById('previous-' + id).style.display = "none";
            nbMyPrev--;
            if (nbMyPrev == 0) {
                document.getElementById('previous-exist').style.display = "none";
                document.getElementById("no-previous").style.display = "block";
            }
        }
    })
}

function toDriverHistory(){
    document.getElementById("driver-history").style.display = "block";
    document.getElementById("driver-history-tab").className = "col-6 align-center standard-border active";
    document.getElementById("previous-order").style.display = "none";
    document.getElementById("previous-order-tab").className = "col-6 align-center standard-border";
}

function toPreviousOrder(){
    document.getElementById("previous-order").style.display = "block";
    document.getElementById("previous-order-tab").className = "col-6 align-center standard-border active";
    document.getElementById("driver-history").style.display = "none";
    document.getElementById("driver-history-tab").className = "col-6 align-center standard-border";
}