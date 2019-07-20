package com.groupal.universia.servicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FechaHelper {
	
	public static Date convertirFechaHoraADate(String strFecha) {
    	try{
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	Date fecha = dateFormat.parse(strFecha);
	    	return fecha;
    	}catch(ParseException e){
    		e.printStackTrace();
    		return null;
		}
	}
	
	public static Date convertirFechaADate(String strFecha) {
		return convertirFechaADate(strFecha, "dd/MM/yyyy");
	}
	
	public static Date convertirFechaADate(String strFecha, String format) {
    	try{
	    	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    	Date fecha = dateFormat.parse(strFecha);
	    	return fecha;
    	}catch(ParseException e){
    		e.printStackTrace();
    		return null;
		}
	}
	
	public static String convertirFechaAString(Date fecha) {
		if (fecha!=null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String strFecha = dateFormat.format(fecha);
			return strFecha;
		}else
			return null;
	}
    
    public static Long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();  
		long daysBetween = 0;  
		while (date.before(endDate)) {  
			date.add(Calendar.DAY_OF_MONTH, 1);  
			daysBetween++;  
		}  
		return daysBetween; 
	}

}

