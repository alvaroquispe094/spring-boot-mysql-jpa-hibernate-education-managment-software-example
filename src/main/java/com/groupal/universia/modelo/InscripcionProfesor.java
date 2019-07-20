package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InscripcionProfesor {
	
   
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_inscripcion")
    private Inscripcion inscripcion;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_profesor")
    private Profesor profesor;
        
    
    private Boolean activo;
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Inscripcion getInscripcion() {
		return inscripcion;
	}
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}
	
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
    
    
}