$(document).ready(function() {
   
	$("#inscripcionHorarioForm").validate();
	 
	 $('#horaInicio').datetimepicker({
		 format : 'HH:mm',
		 pickDate:false,
		 autoclose: true,
		 changeYear: "true",
			changeMonth: "true",
			yearRange: "-40:+0",
		 
		});
	 
	 $('#horaFin').datetimepicker({
		 format : 'HH:mm',
		 pickDate:false,
		 autoclose: true,
		 changeYear: "true",
			changeMonth: "true",
			yearRange: "-40:+0",
		});
	 
//	 $('#bootstrapSelectForm').bootstrapValidator();
	
});