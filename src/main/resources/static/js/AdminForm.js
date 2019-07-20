$(document).ready(function() {
   
	$("#adminForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "existAdmin",
			        type: "post",
			        data: {
				          username: function() { 
				              return $("#username").val();
				          },
			          }
					
			        }
			      }
			    },
		messages: {
			username: {
				required: "This field is required",
	            remote: "The user exist!!"
	        }
	    }	  
	});
	
	$("#fecha").datepicker({
		format: 'dd/mm/yyyy',
		autoclose: true,
		language: 'es'
		
		});

	
});