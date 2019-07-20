package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.InscripcionEstudianteNota;
import com.groupal.universia.repositorio.InscripcionEstudianteNotaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class InscripcionEstudianteNotaService {
	
	@Autowired
    private InscripcionEstudianteNotaRepository inscripcionEstudianteNotaRepository;
	
	@Autowired
    private InscripcionEstudianteService inscripcionEstudianteService;
	

	public Iterable<InscripcionEstudianteNota> listAllInscripcionEstudianteNotas() {
        return inscripcionEstudianteNotaRepository.findAll();
    }
	
	public Iterable<InscripcionEstudianteNota> listAllInscripcionEstudianteNotas(BooleanExpression predicate) {
        return inscripcionEstudianteNotaRepository.findAll(predicate);
    }
	
	public Page<InscripcionEstudianteNota> listAllInscripcionEstudianteNotasPagination(BooleanExpression predicate, Pageable pageable) {
        return inscripcionEstudianteNotaRepository.findAll(predicate, pageable);
    }
  
    public InscripcionEstudianteNota getInscripcionEstudianteNotaById(Integer id) {
        return inscripcionEstudianteNotaRepository.findOne(id);
    }

    public InscripcionEstudianteNota getInscripcionEstudianteNotaById(BooleanExpression predicate) {
        return inscripcionEstudianteNotaRepository.findOne(predicate);
    }
   
    public InscripcionEstudianteNota saveInscripcionEstudianteNota(Integer id, Integer inscripcionEstudiante, Integer nota1, Integer nota2,
    		Integer nota_final, Integer idEstado,Boolean activo) {
    	
    	InscripcionEstudianteNota inscripcionEstudianteNota = null;
		if (id!=null){
			inscripcionEstudianteNota = inscripcionEstudianteNotaRepository.findOne(id);
		}else{
			inscripcionEstudianteNota = new InscripcionEstudianteNota();
		}

		inscripcionEstudianteNota.setInscripcionEstudiante(inscripcionEstudianteService.getInscripcionEstudianteById(inscripcionEstudiante));
		inscripcionEstudianteNota.setNota1(nota1);
		inscripcionEstudianteNota.setNota2(nota2);
		inscripcionEstudianteNota.setNota_final(nota_final);
		inscripcionEstudianteNota.setActivo(activo);
		
		inscripcionEstudianteNotaRepository.save(inscripcionEstudianteNota);

		return inscripcionEstudianteNota;
    }

  
    public void deleteInscripcionEstudianteNota(Integer id) {
    	inscripcionEstudianteNotaRepository.delete(id);
    }


	public void cambiarEstadoInscripcionEstudianteNota(Integer idInscripcionEstudianteNota, boolean estado) {
		InscripcionEstudianteNota inscripcionEstudianteNota = inscripcionEstudianteNotaRepository.findOne(idInscripcionEstudianteNota);
		inscripcionEstudianteNota.setActivo(estado);
		
		inscripcionEstudianteNotaRepository.save(inscripcionEstudianteNota);
	}
    
    
    
}
