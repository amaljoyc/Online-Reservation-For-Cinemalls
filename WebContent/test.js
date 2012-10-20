function AJAXInteraction(url, callback) {

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
		// make a HTTP GET request to the URL asynchronously
		req.open("GET", url, true);
		req.send(null);
	};
}

function validateUserId() {
	var target = document.getElementById("userid");
	var url = "Register?id=" + encodeURIComponent(target.value);
	var ajax = new AJAXInteraction(url, validateCallback);
	ajax.doGet();
}

function validateCallback(responseXML) {

	var msg = responseXML.getElementsByTagName("valid")[0].firstChild.nodeValue;

	if (msg == "false") {

		var mdiv = document.getElementById("userIdMessage");

		// set the style on the div to invalid

		mdiv.className = "bp_invalid";
		mdiv.innerHTML = "Invalid User Id";
		var submitBtn = document.getElementById("submit_btn");
		submitBtn.disabled = true;

	} else {

		var mdiv = document.getElementById("userIdMessage");

		// set the style on the div to valid

		mdiv.className = "bp_valid";
		mdiv.innerHTML = "Valid User Id";
		var submitBtn = document.getElementById("submit_btn");
		submitBtn.disabled = false;
	}
}