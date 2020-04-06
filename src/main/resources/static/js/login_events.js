function LoginEvent() {

	var username = document.getElementById("txtUserName").value;
	var password = document.getElementById("txtPassword").value;

	if (username == '' && password == '') {
		document.getElementById('errorMsg').value = "All fields are required";
		return false;
	} else if (username == '') {

		document.getElementById('errorMsg').value = "User Name is required";
		return false;
	} else if (password == '') {
		document.getElementById('errorMsg').value = "Password is required";
		return false;
	} else {
		return true;
	}
}
