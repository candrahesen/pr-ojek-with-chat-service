function deleteLocation(id, pref){
    var delElmt = document.getElementById("row-"+pref);
    var location = delElmt.getElementsByClassName('location-name')[0].textContent;
    var url = 'helpers/ajax/delete_location.php';
    console.log(id);
    postAjax(url, {id: id, loc: location}, function(data){
        console.log(data);
    });
    delElmt.parentNode.removeChild(delElmt);  
    for(var i = pref+1; i <= nbPref; i++){
        console.log(i)
        var row = document.getElementById("row-"+i).getElementsByClassName('location-pref')[0];
        var prefNow = row.innerHTML - 1;
        row.innerHTML = prefNow;
        document.getElementById("row-"+i).setAttribute("id", "row-" + prefNow);
        var deleteElmt = document.getElementById('delete-link-'+i);
        deleteElmt.setAttribute("id", "delete-link-"+prefNow);
        deleteElmt.setAttribute("onclick", "deleteLocation("+id+","+prefNow+")");
        var editElmt = document.getElementById('edit-link-'+i);
        editElmt.setAttribute("id", "edit-link-"+prefNow);
        editElmt.setAttribute("onclick", "editLocation("+id+","+prefNow+")");
    }
    nbPref--;
    if(nbPref == 0){
        document.getElementById('no-data-row').style.display = 'table-row';
    }
}

document.getElementById('location').onkeyup = function(event){
    var character = event.which;
    if (character>40 || character <37){
        var locNow = document.getElementById('location').value;
        var suggestTag = document.getElementById('suggestLoc');
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

document.getElementById('submit-add-location').onclick = function(){
    var location = document.getElementById('location').value;
    var suggestList = document.getElementById('suggestLoc').childNodes;
    var found = false;
    var i = 0;
    while(!found && i < suggestList.length){
        if(location == suggestList[i].value){
            found = true;
        } else {
            i++;
        }
    } if (found){
        var url = 'add_preferred_location.php';
        postAjax(url, {id: id, loc: location, pref: nbPref+1}, function(data){
            console.log(data);
            if(data == true){
                var addRow = "<td class='number'>"+(nbPref+1)+"</td><td class='location'>"+location+"</td>";
                addRow += "<td class='option'><div class='row'><div class='col-6'><a class='edit-icon' id='edit-"+(nbPref+1)+" onclick='editLocation("+id+","+(nbPref+1)+")'><i class='material-icons md-36'>edit</i></a>";
                addRow += "</div><div class='col-6'>"+"<a class='delete-icon' id='delete-"+(nbPref+1)+" onclick='deleteLocation("+id+","+(nbPref+1)+")'><i class='material-icons md-36'>clear</i></a>";
                addRow += "</div></div></td>";
                var createElement = document.createElement('tr');
                createElement.setAttribute("id", "row-"+(nbPref+1));
                createElement.innerHTML = addRow;
                document.getElementById('location_table').appendChild(createElement);
                nbPref++;
            } else {
                console.log('nooo');
            }
        });
    }
}

function editLocation(id, pref){
    var editElmt = document.getElementById("row-"+pref);
    var location = editElmt.getElementsByClassName('location')[0].textContent;
    var pref_num = editElmt.getElementsByClassName('number')[0].textContent;
    var delete_box = document.getElementById('delete-'+pref);
    document.getElementById('edit-link-'+pref).style.display='none';
    document.getElementById('delete-link-'+pref).style.display='none';
    document.getElementById('submit-edited-location-'+pref).style.display='block';
    // editElmt.getElementsByClassName('location-name')[0].innerHTML = ""
    // document.getElementById('loc-row-'+pref).style.display = "block"
    editElmt.getElementsByClassName('location-pref')[0].innerHTML = ""
    document.getElementById('pref-row-'+pref).style.display = "block"
    // var new_loc = document.getElementById('loc-row-'+pref).value
    // var new_pref = document.getElementById('num-row-'+pref).value
    delete_box.innerHTML = ""
}
