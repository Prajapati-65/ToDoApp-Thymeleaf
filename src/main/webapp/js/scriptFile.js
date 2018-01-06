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

function viewTax(id) {
	console.log("all details"+id);
	$.ajax({
		type : 'GET',
		url : "viewTax/" + id
	});
}

function saveToken() {
	var token = $('#jwt').val();
	console.log(token);
	if (token != null) {
		localStorage.setItem('token', token);
	}
}

function getUrlData (urlToSend) {
	$.ajax({
		method : 'POST',
		url : 'user/getUrlData',
		headers : {
			'url' : urlToSend,
			'token' :  localStorage.getItem('token')
		}
	});
}

function fbAsyncSocialShare(note) {
	FB.init({
		appId : '127036578094516',
		status : true,
		cookie : true,
		xfbml : true,
		version : 'v2.4'
	});

	FB.ui({
		method : 'share_open_graph',
		action_type : 'og.likes',
		action_properties : JSON.stringify({
			object : {
				'og:title' : note.title,
				'og:description' :note.description
			}
		})
	}, function(response) {
		if (response && !response.error_message) {
			alert('Posting completed.');
		} else {
			alert('Error while posting');
		}
	});
};
