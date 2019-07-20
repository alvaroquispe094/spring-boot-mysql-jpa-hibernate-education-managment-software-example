package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.InscripcionEstudiante;
import com.groupal.universia.repositorio.EstadoRepository;
import com.groupal.universia.repositorio.InscripcionEstudianteRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class InscripcionEstudianteService {
	
	@Autowired
    private InscripcionEstudianteRepository inscripcionEstudianteRepository;
	
	@Autowired
    private InscripcionService inscripcionService;
	
	@Autowired
    private EstudianteService estudianteService;
	
	@Autowired
    private EstadoRepository estadoRepository;
	
	public Iterable<InscripcionEstudiante> listAllInscripcionEstudiantes() {
        return inscripcionEstudianteRepository.findAll();
    }
	
	public Iterable<InscripcionEstudiante> listAllInscripcionEstudiantes(BooleanExpression predicate) {
        return inscripcionEstudianteRepository.findAll(predicate);
    }
	
	public Page<InscripcionEstudiante> listAllInscripcionEstudiantesPagination(BooleanExpression predicate, Pageable pageable) {
        return inscripcionEstudianteRepository.findAll(predicate, pageable);
    }
  
    public InscripcionEstudiante getInscripcionEstudianteById(Integer id) {
        return inscripcionEstudianteRepository.findOne(id);
    }

    public InscripcionEstudiante getInscripcionEstudianteById(BooleanExpression predicate) {
        return inscripcionEstudianteRepository.findOne(predicate);
    }
   
    public InscripcionEstudiante saveInscripcionEstudiante(Integer id, Integer idinscripcion, Integer idEstudiante,Integer idEstado, Boolean activo) {
    	
    	InscripcionEstudiante inscripcionEstudiante = null;
		if (id!=null){
			inscripcionEstudiante = inscripcionEstudianteRepository.findOne(id);
		}else{
			inscripcionEstudiante = new InscripcionEstudiante();
		}

		inscripcionEstudiante.setInscripcion(inscripcionService.getInscripcionById(idinscripcion));
		inscripcionEstudiante.setEstudiante(estudianteService.getEstudianteById(idEstudiante));
		inscripcionEstudiante.setEstado(estadoRepository.findOne(idEstado));
		inscripcionEstudiante.setActivo(activo);
		
		inscripcionEstudianteRepository.save(inscripcionEstudiante);

		return inscripcionEstudiante;
    }

  
    public void deleteInscripcionEstudiante(Integer id) {
    	inscripcionEstudianteRepository.delete(id);
    }


	public void cambiarEstadoInscripcionEstudiante(Integer idInscripcionEstudiante, boolean estado) {
		InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteRepository.findOne(idInscripcionEstudiante);
		inscripcionEstudiante.setActivo(estado);
		
		inscripcionEstudianteRepository.save(inscripcionEstudiante);
	}
    
	//cambia esl estado de la cursada de una inscripcionEstudiante
    public void cambiarEstadoCursada(Integer idInscripcionEstudiante, Integer idEstado) {
    	InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteRepository.findOne(idInscripcionEstudiante);
    	inscripcionEstudiante.setEstado(estadoRepository.getOne(idEstado));
    	inscripcionEstudianteRepository.save(inscripcionEstudiante);

    }
    
}
