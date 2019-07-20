package com.groupal.universia.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InscripcionHorario {
	
   
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_inscripcion")
    private Inscripcion	inscripcion;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_semana")
    private Semana	semana;
 
    private Date hora_inicio;
    private Date hora_fin;
    
    @ManyToOne (fetch = FetchType.LAZY, optional = true)
	@JoinColumn( name="id_aula")
    private Aula aula;
    
    
    private Boolean activo;

    
    public Semana getSemana() {
		return semana;
	}
	public void setSemana(Semana semana) {
		this.semana = semana;
	}
	
	public Aula getAula() {
		return aula;
	}
	public void setAula(Aula aula) {
		this.aula = aula;
	}
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
   
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public Inscripcion getInscripcion() {
		return inscripcion;
	}
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}
	
	public Date getHora_inicio() {
		return hora_inicio;
	}
	public void setHora_inicio(Date hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	
	public Date getHora_fin() {
		return hora_fin;
	}
	public void setHora_fin(Date hora_fin) {
		this.hora_fin = hora_fin;
	}
	    
}