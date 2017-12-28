$('document').ready(function() {
});

function toogleNav() {
	var sideNav = document.getElementById("sideNavContent").style.width;
	if (sideNav == "0px") {
		openNav();
	} else {
		closeNav();
	}
}

function openNav() {

	if (screen.width > 1000) {
		document.getElementById("sideNavContent").style.width = "250px";
		document.getElementById("noteContainer").style.marginLeft = "300px";
	} else
		document.getElementById("sideNavContent").style.width = "250px";
}

function closeNav() {
	document.getElementById("sideNavContent").style.width = "0px";
	document.getElementById("noteContainer").style.marginLeft = "0px";
}

function deleteNote(noteId) {
	alert("2");
	console.log("id : " + noteId)
	$.ajax({
		type : 'DELETE',
		url : "delete/" + noteId
	});
}

function saveToken() {
	alert("3");
	var token =  $('#jwt').val();
	console.log(token);
	if(token != null){
		alert("5");
	localStorage.setItem('token', token);
	}
}