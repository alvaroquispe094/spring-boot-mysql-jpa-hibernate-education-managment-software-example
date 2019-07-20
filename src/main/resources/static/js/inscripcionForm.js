$(document).ready(function() {
   
	$("#inscripcionForm").validate();
	 
	 $('#fechaInicio').datepicker({
		 format: 'dd/mm/yyyy',
		 language: 'es',
		 autoclose: true
		
		 
		});
	 
	 $('#fechaFin').datepicker({
		 format: 'dd/mm/yyyy',
		 language: 'es',
		 autoclose: true
	
		});
	 
	 
	 $("#carrera").on("change", function(){
			$("#materia option").remove();
			var idCarrera = $("#carrera option:selected").val();
			console.log("idCarrera: "+idCarrera)
			$.getJSON("carreraMaterias?idCarrera="+idCarrera, function(materias) {
				console.log("materia: "+materias);
				$("#materia").append("<option data-hidden='true' value=''>Seleccione Materia");
				$.each(materias, function(i,materia) {
					console.log(materia);
					
					$("#materia").append("<option class='opcion'  value="+materia.id+"> "+materia.nombre+"</option>");
					
//					$(".dropdown-menu").selectpicker("refresh");
				});
				
//				$("#materia").selectpicker("refresh");	
				
			});
			
			
		});
	 
	 
//	 $("#carrera").on('change', function(){
//		 $("#materia option").remove();
//		 var idCarrera = $("#carrera option:selected").val();				
//		 console.log("idCarrera: "+idCarrera)
//		 $.ajax({
//			type: "GET",
//			url: 'carreraMaterias',
//			contentType: "application/json;charset=ISO-8859-1",
//			dataType: "json",
//			data: 
//				{	
//					idCarrera: idCarrera
//
//				},
//			
//			success: function(response){
//				console.log("sadsfasf")
//				console.log(JSON.stringify(response));
//				
//			},
//			error: function(XMLHttpRequest, textStatus, errorThrown) {
//			     alert("text status:"+textStatus+"|||| errorThrow: "+errorThrown);
//			  }	
//		 });
//	}); 
	
	
});