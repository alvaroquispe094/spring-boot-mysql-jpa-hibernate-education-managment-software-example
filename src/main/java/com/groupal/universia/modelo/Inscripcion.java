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
public class Inscripcion {
	
   
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Date fecha_inicio;
	private Date fecha_fin;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_carrera")
    private Carrera carrera;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_materia")
    private Materia materia;
        
    private Integer cantidad;
    
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="id_estado")
    private Estado estado;
    
    private Boolean activo;
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

   
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Carrera getCarrera() {
		return carrera;
	}
	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
    
    
}