function AJAXInteraction(url, callback, parms) {

	var req = init();
	req.onreadystatechange = processRequest;

	function init() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}

	function processRequest() {
		// readyState of 4 signifies request is complete
		if (req.readyState == 4) {
			// status of 200 signifies sucessful HTTP call
			if (req.status == 200) {
				if (callback)
					callback(req.responseXML);
			}
		}
	}

	this.doGet = function() {
		url = url + "?" + parms;
		req.open("GET", url, true);
		req.send(null);
	};

	this.doPost = function() {
		// make a HTTP POST request to the URL asynchronously
		req.open("POST", url, true);
		http.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		http.setRequestHeader("Content-length", params.length);
		http.setRequestHeader("Connection", "close");
		req.send(parms);
	};
}
function confirmNoExist() {
	var newemail = document.getElementById("fname");
	var mdiv = document.getElementById("divid");
	if (newemail.value == "--name--") {
		alert("no new film name is given");
	} else {
		// set the style on the div to invalid
		mdiv.innerHTML = "<font color='blue'>Working....<font>";
		var url = "http://localhost:8080/CineMall/AddFilm.do";
		var params = "fname=" + encodeURIComponent(newemail.value);
		var ajax = new AJAXInteraction(url, validateCallback, params);
		ajax.doGet();
	}
}

function userEditer(user){
	document.getElementById('edit').className="edit_avail";
	document.forms['edit'].usrid.value = user.value;
}

function validateCallback(responseXML) {

	var msg = responseXML.getElementsByTagName("valid")[0].firstChild.nodeValue;

	if (msg == "false") {
		var mdiv = document.getElementById("divid");
		// set the style on the div to invalid
		mdiv.innerHTML = "<font color='red'>An Error Occured!!<br> Data not set<font>";
		alert('A film allredy existsin the database by the same name');
	} else {
		var mdiv = document.getElementById("divid");

		// set the style on the div to valid
		mdiv.innerHTML = "<font color='green'>" + msg + "<font>";
		var mdiv = document.getElementById("divid");
		mdiv.innerHTML = "<font color='green'>" + msg + "<font>";
	}
}