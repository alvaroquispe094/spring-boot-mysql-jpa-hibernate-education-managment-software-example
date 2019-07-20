package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Inscripcion;
import com.groupal.universia.repositorio.EstadoRepository;
import com.groupal.universia.repositorio.InscripcionRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class InscripcionService {

	@Autowired
    private InscripcionRepository inscripcionRepository;
	
	@Autowired
    private EstadoRepository estadoRepository;
	
	 @Autowired
	 private CarreraService carreraService;
	 
	 @Autowired
	 private MateriaService materiaService;
  
    public Iterable<Inscripcion> listAllInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Page<Inscripcion> listAllInscripcionesPagination(BooleanExpression predicate, Pageable pageable) {
        return inscripcionRepository.findAll(predicate, pageable);
    }
	
  
    public Inscripcion getInscripcionById(Integer id) {
        return inscripcionRepository.getOne(id);
    }

   
    public Inscripcion saveInscripcion(Integer id, Integer idCarrera, Integer idMateria , String fechaInicio, String fechaFin, Integer cantidad, Integer idEstado, Boolean activo) {
    	
    	Inscripcion inscripcion = null;
		if (id!=null){
			inscripcion = inscripcionRepository.getOne(id);
		}else{
			inscripcion = new Inscripcion();
		}

		inscripcion.setCarrera(carreraService.getCarreraById(idCarrera));
		inscripcion.setMateria(materiaService.getMateriaById(idMateria));
		inscripcion.setFecha_inicio(FechaHelper.convertirFechaADate(fechaInicio, "dd/MM/yyyy"));
		inscripcion.setFecha_fin(FechaHelper.convertirFechaADate(fechaFin, "dd/MM/yyyy"));
		inscripcion.setCantidad(cantidad);
		inscripcion.setEstado(estadoRepository.getOne(idEstado));
		inscripcion.setActivo(activo);
		inscripcionRepository.save(inscripcion);

		return inscripcion;
    }

  
    public void deleteInscripcion(Integer id) {
    	inscripcionRepository.delete(id);
    }
	
    public void cambiarEstadoInscripcion(Integer idInscripcion, Boolean estado ) {
		Inscripcion inscripcion = inscripcionRepository.findOne(idInscripcion);
//		profesor.setEnabled(estado);
		inscripcion.setActivo(estado);
		inscripcionRepository.save(inscripcion);		
	}
    
    //cambia esl estado de la publicacion de una inscripcion
    public void cambiarEstadoPublicacion(Integer idInscripcion, Integer idEstado) {
    	Inscripcion inscripcion = inscripcionRepository.findOne(idInscripcion);
    	inscripcion.setEstado(estadoRepository.getOne(idEstado));
    	inscripcionRepository.save(inscripcion);

    }
    
    //resta una unidad al cupo de inscripcion
    public void restarCantidadCupos(Integer idInscripcion) {
    	
    	Inscripcion inscripcion = inscripcionRepository.findOne(idInscripcion);
    	inscripcion.setCantidad(inscripcion.getCantidad() -1 );
    	inscripcionRepository.save(inscripcion);
    	
    }
    
  //aumenta una unidad al cupo de inscripcion si alguien se da de baja
    public void sumarCantidadCupos(Integer idInscripcion) {
    	
    	Inscripcion inscripcion = inscripcionRepository.findOne(idInscripcion);
    	inscripcion.setCantidad(inscripcion.getCantidad() + 1);
    	inscripcionRepository.save(inscripcion);
    	
    }
    
    
    
}
