//Below Function Executes On Batch Form Submit
function BatchEvent() {
	// Storing Field Values In Variables

	var id = document.getElementById("txtbatchID").value;
	var name = document.getElementById("txtName").value;
	var timing = document.getElementById("txtTiming").value;
	var duration = document.getElementById("txtDuration").value;
	
	var alphanumeric = /^(?=.*\d)(?=.*[a-zA-Z]).{2,15}$/;
	
	if (id == '' && name == '' && timing == '' && duration == '' ) {
		document.getElementById('errorMsg').innerHTML = "All fields are mandatory";
		return false;
	}

	if(id == ''){
		document.getElementById('errorMsg').innerHTML = "Batch Id is mandatory field";
		return false;
	}else if (!id.match(alphanumeric)) {
		document.getElementById('errorMsg').innerHTML = "Batch Id should be alpha numeric";
		return false;
	}else if(name == ''){
		document.getElementById('errorMsg').innerHTML = "Batch Name is mandatory field";
		return false;
	}else if (timing =='' ) {
		document.getElementById('errorMsg').innerHTML = "Batch Timing is mandatory field";
		return false;
	}else if (duration =='' ) {
		document.getElementById('errorMsg').innerHTML = "Batch Duration is mandatory field";
		return false;
	}else {
		return true;
	}
}
