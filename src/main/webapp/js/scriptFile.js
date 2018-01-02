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
		document.getElementById("sideNavContent").style.width = "300px";
		document.getElementById("noteContainer").style.marginLeft = "250px";
	} else
		document.getElementById("sideNavContent").style.width = "300px";
}

function closeNav() {
	document.getElementById("sideNavContent").style.width = "0px";
	document.getElementById("noteContainer").style.marginLeft = "0px";
}

function deleteNote(noteId) {
	console.log("note id is :- " + noteId)
	$.ajax({
		type : 'DELETE',
		url : "delete/" + noteId
	});
}


function saveToken() {
	var token =  $('#jwt').val();
	console.log(token);
	if(token != null){
	localStorage.setItem('token', token);
	}
}