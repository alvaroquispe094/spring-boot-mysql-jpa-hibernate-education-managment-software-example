package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estado {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	public static final Integer NO_PUBLICADA = new Integer(1);
	public static final Integer PUBLICADA = new Integer(2);
	public static final Integer APROBADA = new Integer(3);
	public static final Integer DESAPROBADA = new Integer(4);
	public static final Integer CURSANDO = new Integer(5);

	
	private String nombre;

	
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
	
    public String getNombre() {
        return nombre;
    }

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
