$(document).ready(function(){
    $("#view").hide();
    $("#ocultar").click(function(){ 

    $("#cuerpo").hide();
    $("#view").fadeIn();
    
    setTimeout(function(){
                    $("#view").fadeOut("slow");
                    $("#cuerpo").delay(1000).fadeIn("slow");
               }, 5000);
        
    });
});