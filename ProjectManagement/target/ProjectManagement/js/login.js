function login(){
	
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var obj = { "username":username,
				 "password" : password };
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
		if (this.readyState == 4 && this.status == 200) {
			alert("Api Response: " + JSON.stringify(this.responseText));
		}
	};
	xhttp.open("POST", "api/login", true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	alert("Sending request for: "+JSON.stringify(obj))
	xhttp.send(obj);
}
