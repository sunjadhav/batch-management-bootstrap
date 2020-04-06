

$(document).ready(function(){

	
	$('#login_form').submit(function(e) {
	    var username = $('#txtUserName').val();
	    var password = $('#txtPassword').val();

	 
	    $(".error").remove();
	 
	    if (username.length < 1 && password.length<1) {
	      $('#errorMsg').val('All fields are required');
		    e.preventDefault();

	    }
	    
	    else
	    	{
//	    	$(location).attr('href', '/login')
	    	 if (username.length < 1 ) {
	   	      $('#errorMsg').val('User Name is required');
	   		    e.preventDefault();

	   	    }
	    	 if (password.length < 1 ) {
		   	      $('#errorMsg').val('Password is required');
		   		    e.preventDefault();

		   	    }
		    	 	    	 
	   	    	return;
	    	}
	});
});
