package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Materia {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String nombre;
    private Boolean activo;
	
    public Materia() {}
    public Materia(String nombre, Boolean activo) {
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
