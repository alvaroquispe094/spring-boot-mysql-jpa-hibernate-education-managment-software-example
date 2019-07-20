$(document).ready(function() {
	
	
});

function validarStock(cantidad){

			console.log("cantidad: "+cantidad);
			if ( cantidad  <=0 ) {
				mensajeDeError = 'Cupos completos.';
				console.log("en el if: "+cantidad);
				alert(mensajeDeError);
				return false;
			}else{
				console.log("en el else: "+cantidad);

				return true;
			}
	
	return valido;

}