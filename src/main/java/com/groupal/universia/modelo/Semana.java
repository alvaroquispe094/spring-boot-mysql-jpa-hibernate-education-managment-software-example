package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Semana {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	public static final Integer LUNES = new Integer(1);
	public static final Integer MARTES = new Integer(2);
	public static final Integer MIERCOLES = new Integer(3);
	public static final Integer JUEVES = new Integer(4);
	public static final Integer VIERNES = new Integer(5);
	public static final Integer SABADO = new Integer(6);
	public static final Integer DOMINGO = new Integer(7);
	
	private String nombre;

	
    public Integer getId() {
        return id;
    }
	
    public String getNombre() {
        return nombre;
    }

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
