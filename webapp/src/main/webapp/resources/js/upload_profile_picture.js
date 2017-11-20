function uploadFinish(){
	var filename = document.getElementById("uploadBtn");
	document.getElementById('uploadFile').value = filename.files[0].name;
}