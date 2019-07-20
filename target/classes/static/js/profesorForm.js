$(document).ready(function() {
   
	$("#profesorForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "exist",
			        type: "post",
			        data: {
				          username: function() { 
				              return $("#username").val();
				          },
				          id: function() { 
				              return $("#id").val();
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