// Below Function Executes On Asset Form Submit
function StudentEvent() {
	// Storing Field Values In Variables

	var name = document.getElementById("txtName").value;
	var surname = document.getElementById("txtSurname").value;
	var address = document.getElementById("txtAddress").value;
	var mobile = document.getElementById("txtMobile").value;
	var college = document.getElementById("txtCollege").value;
	var education = document.getElementById("txtEducation").value;
	var paidAmount = document.getElementById("txtPaidAmount").value;
	var pendingAmount = document.getElementById("txtPendingAmount").value;
	var batch = document.getElementById("drpbatchID").value;
	
	var alphabet = /^[A-Za-z]+$/;
	var number = /^\d+$/
	if (name == '' && surname == '' && address == '' && mobile == '' && college == ''
			&& education == '' && paidAmount == '' && pendingAmount == '' && batch === "select") {

		document.getElementById('errorMsg').innerHTML = "All Fields are mandatory";

		return false;
	}

	if(surname == ''){
		document.getElementById('errorMsg').innerHTML = "Last Name is mandatory";
		return false;
	}else if (!surname.match(alphabet)) {
		document.getElementById('errorMsg').innerHTML = "Last Name should be alphabets only";
		return false;
	}else if(name == ''){
		document.getElementById('errorMsg').innerHTML = "Name is mandatory";
		return false;
	}else if (!name.match(alphabet)) {
		document.getElementById('errorMsg').innerHTML = "Name should be alphabets only";
		return false;
	}else if(address == ''){
		document.getElementById('errorMsg').innerHTML = "Address is mandatory";
		return false;
	}else if(mobile == ''){
		document.getElementById('errorMsg').innerHTML = "Mobile Number is mandatory";
		return false;
	}else if (mobile !='' && !mobile.match(number)) {
		document.getElementById('errorMsg').innerHTML = "Mobile No field accepts only Numeric Value";
		return false;
	}else if(college == ''){
		document.getElementById('errorMsg').innerHTML = "Collage/Company is mandatory";
		return false;
	}else if(education == ''){
		document.getElementById('errorMsg').innerHTML = "Education field is mandatory";
		return false;
	}else if(paidAmount == ''){
		document.getElementById('errorMsg').innerHTML = "Paid Amount is mandatory";
		return false;
	}else if (paidAmount !='' && !paidAmount.match(number)) {
		document.getElementById('errorMsg').innerHTML = "Paid Amount field accepts only Numeric Value";
		return false;
	}else if(pendingAmount == ''){
		document.getElementById('errorMsg').innerHTML = "Pending Amount is mandatory";
		return false;
	}else if (pendingAmount !='' && !pendingAmount.match(number)) {
		document.getElementById('errorMsg').innerHTML = "Pending Amount field accepts only Numeric Value";
		return false;
	}else if (batch === "select") {
		document.getElementById('errorMsg').innerHTML = "Please select batch name";
		return false;
	}else{
		return true;
	}
}
