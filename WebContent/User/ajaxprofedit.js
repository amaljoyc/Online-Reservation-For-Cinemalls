function chngePassword() {
	var mdiv = document.getElementById("divid");
	mdiv.innerHTML = "<form ><table><tr><th colspan='2'>Change password</th></tr><tr><td>OldPassword</td><td><input type='password' id='oldpass'>"
			+ "</td></tr><tr><td>New password:</td><td><input type='password'id='newpass'></td></tr><tr><td>Confirm password:</td><td><input type='password'id='conpass'></td></tr><tr><td colspan='2'>"
			+ "<input type='button' onmouseup='changePass()' value='Change'/></td></tr></table></form>";
	FadeElem(MessageDiv, 255, 255, 0, 255, 255, 255);
}
function chngeEMail() {
	var mdiv = document.getElementById("divid");
	mdiv.innerHTML = "<form ><table><tr><th colspan='2'>Change Email</th></tr><tr><td>New Email Id:</td><td><input type='text' id='newemail'>"
			+ "</td></tr><tr><td colspan='2'>"
			+ "<input type='button' onmouseup='changeEm()' value='Change'/></td></tr></table></form>";
	FadeElem(MessageDiv, 255, 255, 0, 255, 255, 255);
}
function chngeAddr() {
	var mdiv = document.getElementById("divid");
	mdiv.innerHTML = "<form ><table><tr><th colspan='2'>Change Address</th></tr><tr><td>New Address</td><td><textarea id='add' rows='3' cols='40'></textarea>"
			+ "</td></tr><tr><td colspan='2'>"
			+ "<input type='button' onmouseup='changeAdd()' value='Change'/></td></tr></table></form>";
	FadeElem(MessageDiv, 255, 255, 0, 255, 255, 255);
}
function chngePhnNo() {
	var mdiv = document.getElementById("divid");
	mdiv.innerHTML = "<form ><table><tr><th colspan='2'>Change Phone Number</th></tr><tr><td>New Pnhone Number:</td><td><input type='text' id='newphn'>"
			+ "</td></tr><tr><td colspan='2'>"
			+ "<input type='button' onmouseup='changePhn()' value='Change'/></td></tr></table></form>";
	FadeElem(MessageDiv, 255, 255, 0, 255, 255, 255);
}

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
			//alert("fff");
			// status of 200 signifies sucessful HTTP call
			if (req.status == 200) {
				//alert("fffffff fdsf");
				if (callback){
					//alert("fffffff fdsf");
					callback(req.responseXML);
				}
			}
		}
		//}catch(er){
			//alert('An error occured'+er);
		//}
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

function changePass() {
	var oldpass = document.getElementById("oldpass");
	var newpass = document.getElementById("newpass");
	var conpass = document.getElementById("conpass");
	var edit = document.getElementsByName("edit");
	var mdiv = document.getElementById("status");
	if (oldpass.value == "") {
		alert("no Old Password is given");
	}else if(newpass.value == ""){
		alert("no New  Password is given");
	}else if(conpass.value == ""){
		alert("no Confirm Password is given");
	}
		else {
	// set the style on the div to invalid
	mdiv.innerHTML = "<font color='blue'>Working....<font>";
	var url = "http://localhost:8080/CineMall/EditUserData.do";
	var params = "oldpass=" + encodeURIComponent(oldpass.value) + "&newpass="
			+ encodeURIComponent(newpass.value) + "&conpass="
			+ encodeURIComponent(conpass.value) + "&edit="
			+ encodeURIComponent(edit.value);
	var ajax = new AJAXInteraction(url, validateCallback, params);
	ajax.doGet();}
}
function changeEm() {
	var newemail = document.getElementById("newemail");
	var edit = document.getElementsByName("edit");
	var mdiv = document.getElementById("status");
	if (newemail.value == "") {
		alert("no email id is given");
	} else {
		// set the style on the div to invalid
		mdiv.innerHTML = "<font color='blue'>Working....<font>";
		var url = "http://localhost:8080/CineMall/EditUserData.do";
		var params = "email=" + encodeURIComponent(newemail.value) + "&edit="
				+ encodeURIComponent(edit.value);
		var ajax = new AJAXInteraction(url, validateCallback, params);
		ajax.doGet();
	}
}
function changeAdd() {

	var add = document.getElementById("add");
	var edit = document.getElementsByName("edit");
	var mdiv = document.getElementById("status");
	if (add.value == "") {
		alert("no Address is given");
	} else {
	// set the style on the div to invalid
	mdiv.innerHTML = "<font color='blue'>Working....<font>";
	var url = "http://localhost:8080/CineMall/EditUserData.do";
	var params = "add=" + encodeURIComponent(add.value) + "&edit="
			+ encodeURIComponent(edit.value);
	var ajax = new AJAXInteraction(url, validateCallback, params);
	ajax.doGet();}
}
function changePhn() {
	var phn = document.getElementById("newphn");
	var edit = document.getElementsByName("edit");
	var mdiv = document.getElementById("status");
	if (phn.value == "") {
		alert("no Phne number is given");
	} else {
	// set the style on the div to invalid
	mdiv.innerHTML = "<font color='blue'>Working....<font>";
	var url = "http://localhost:8080/CineMall/EditUserData.do";
	var params = "phn=" + encodeURIComponent(phn.value) + "&edit="
			+ encodeURIComponent(edit.value);
	var ajax = new AJAXInteraction(url, validateCallback, params);
	ajax.doGet();}
}


function validateCallback(responseXML) {

	var msg = responseXML.getElementsByTagName("valid")[0].firstChild.nodeValue;

	if (msg == "false") {
		//msg = responseXML.getElementsByTagName("err")[1].firstChild.nodeValue;
		var mdiv = document.getElementById("status");
		// set the style on the div to invalid
		mdiv.innerHTML = "<font color='red'>"+msg+"<font>";
		var mdiv = document.getElementById("divid");
		mdiv.innerHTML = "<font color='red'>An Error Occured!!<br> Data not re-set<font>";
	} else {
		var mdiv = document.getElementById("status");

		// set the style on the div to valid
		mdiv.innerHTML = "<font color='green'>" + msg + "<font>";
		var mdiv = document.getElementById("divid");
		mdiv.innerHTML = "<font color='green'>" + msg + "<font>";
	}
}


function findAllMoviesFor(selected_date){
	var url = "http://localhost:8080/CineMall/ReserveTicket";
	var ajax = new AJAXInteraction(url, evalidateCall, selected_date);
	ajax.doGet();
	//alert(selected_date);
}


function evalidateCall(responseXML) {
	//alert("i nere");
	var msg = responseXML.getElementsByTagName("valid")[0].firstChild.nodeValue;
	alert(msg);
	window.location += '&' +msg;
	if (msg != "false") {
		msg=msg.split(";");
		var newhtml="<select>";
		for(i=0;i<msg.length;i++){
			newhtml+="<option value='"+msg[i]+"'>"+msg[i]+"</option>";
		}
		newhtml+="</select>";
		var mdiv = document.getElementById("this_div");
		// set the style on the div to invalid
		mdiv.innerHTML = newhtml;
	}
}