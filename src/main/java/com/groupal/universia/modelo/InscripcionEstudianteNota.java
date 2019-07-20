package com.groupal.universia.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InscripcionEstudianteNota {
	
   
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_inscripcion_estudiante")
    private InscripcionEstudiante inscripcionEstudiante;
	
	private Integer nota1;
	private Integer nota2;
    private Integer nota_final;
    
    private Boolean activo;
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public InscripcionEstudiante getInscripcionEstudiante() {
		return inscripcionEstudiante;
	}
	public void setInscripcionEstudiante(InscripcionEstudiante inscripcionEstudiante) {
		this.inscripcionEstudiante = inscripcionEstudiante;
	}
	
	public Integer getNota1() {
		return nota1;
	}
	public void setNota1(Integer nota1) {
		this.nota1 = nota1;
	}
	
	public Integer getNota2() {
		return nota2;
	}
	public void setNota2(Integer nota2) {
		this.nota2 = nota2;
	}
	
	public Integer getNota_final() {
		return nota_final;
	}
	public void setNota_final(Integer nota_final) {
		this.nota_final = nota_final;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
    
    
}