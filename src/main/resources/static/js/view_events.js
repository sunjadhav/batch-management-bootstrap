
// Below Function Executes On Student Form Submit
function ViewEvent() {
// Storing Field Values In Variables

var studentName = document.getElementById("txtSearchStudentName").value;
var batchName = document.getElementById("txtSearchBatchName").value;

// Conditions
	if (studentName == '' && batchName == '' ) {
		document.getElementById('errorMsg').innerHTML = "Atleast one field is required.....!";
		return false;
	}
	return true;
}
/*
//Below Function Executes On Batch Form Submit
function ViewBatchEvent() {
// Storing Field Values In Variables

var startDate = document.getElementById("txtStartAt").value;
var endDate = document.getElementById("txtEndAt").value;
var batchName = document.getElementById("txtBatchName").value;

// Conditions
	if (startDate == '' && endDate == '' && batchName == '' ) {
		document.getElementById('errorMsg').innerHTML = "Batch Name or Start Date and End Date Required.....!";
		return false;
	}
var currentDate = new Date();

var date = currentDate.getDate();
var month = currentDate.getMonth(); //Be careful! January is 0 not 1
var year = currentDate.getFullYear();

var dateString = "0"+(month + 1) + "/" + date + "/" + year;
console.log(dateString);
	if (startDate == ''){
		document.getElementById('txtStartAt').innerHTML = ;
	}
	if (endDate == ''){
		document.getElementById('txtEndAt').innerHTML = dateString;
		return false;
	}
	
}
*/
function isDisabled(){
	var status = document.getElementById("archived").value;
	console.log(status);
	if(status == 'Archived')
		return false;
	return true;
}
