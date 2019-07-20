package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aula {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String nombre;
    private Boolean activo;
	
    public Aula() {}
    public Aula(String nombre, Boolean activo) {
    	this.nombre = nombre;
    	this.activo = activo;
    }
    
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
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	

}
