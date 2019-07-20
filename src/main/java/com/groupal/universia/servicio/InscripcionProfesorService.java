package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.InscripcionProfesor;
import com.groupal.universia.repositorio.InscripcionProfesorRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class InscripcionProfesorService {
	
	@Autowired
    private InscripcionProfesorRepository inscripcionProfesorRepository;
	
	@Autowired
    private InscripcionService inscripcionService;
	
	@Autowired
    private ProfesorService profesorService;
	
	public Iterable<InscripcionProfesor> listAllInscripcionProfesors() {
        return inscripcionProfesorRepository.findAll();
    }
	
	public Page<InscripcionProfesor> listAllInscripcionProfesorsPagination(BooleanExpression predicate, Pageable pageable) {
        return inscripcionProfesorRepository.findAll(predicate, pageable);
    }
  
    public InscripcionProfesor getInscripcionProfesorById(Integer id) {
        return inscripcionProfesorRepository.findOne(id);
    }

   
    public InscripcionProfesor saveInscripcionProfesor(Integer id, Integer idinscripcion, Integer idProfesor, Boolean activo) {
    	
    	InscripcionProfesor inscripcionProfesor = null;
		if (id!=null){
			inscripcionProfesor = inscripcionProfesorRepository.findOne(id);
		}else{
			inscripcionProfesor = new InscripcionProfesor();
		}

		inscripcionProfesor.setInscripcion(inscripcionService.getInscripcionById(idinscripcion));
		inscripcionProfesor.setProfesor(profesorService.getProfesorById(idProfesor));

		inscripcionProfesor.setActivo(activo);
		
		inscripcionProfesorRepository.save(inscripcionProfesor);

		return inscripcionProfesor;
    }

  
    public void deleteInscripcionProfesor(Integer id) {
    	inscripcionProfesorRepository.delete(id);
    }


	public void cambiarEstadoInscripcionProfesor(Integer idInscripcionProfesor, boolean estado) {
		InscripcionProfesor inscripcionProfesor = inscripcionProfesorRepository.findOne(idInscripcionProfesor);
		inscripcionProfesor.setActivo(estado);
		
		inscripcionProfesorRepository.save(inscripcionProfesor);
	}
    
    
    
}
