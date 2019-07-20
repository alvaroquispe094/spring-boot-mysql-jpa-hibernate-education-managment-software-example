package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Aula;
import com.groupal.universia.repositorio.AulaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class AulaService {
	
	@Autowired
    private AulaRepository aulaRepository;
	
	public Iterable<Aula> listAllAulas() {
        return aulaRepository.findAll();
    }
	
	public Iterable<Aula> listAllAulas(Sort sort) {
        return aulaRepository.findAll(sort);
    }
	
	public Page<Aula> listAllAulasPagination(BooleanExpression predicate, Pageable pageable) {
        return aulaRepository.findAll(predicate, pageable);
    }
  
    public Aula getAulaById(Integer id) {
        return aulaRepository.findOne(id);
    }

   
    public Aula saveAula(Integer id, String nombre, Boolean activo) {
    	
    	Aula aula = null;
		if (id!=null){
			aula = aulaRepository.findOne(id);
		}else{
			aula = new Aula();
		}

		aula.setNombre(nombre);
		aula.setActivo(activo);
		
		aulaRepository.save(aula);

		return aula;
    }

  
    public void deleteProfesor(Integer id) {
    	aulaRepository.delete(id);
    }


	public void cambiarEstadoAula(Integer idAula, boolean estado) {
		Aula aula = aulaRepository.findOne(idAula);
		aula.setActivo(estado);
		
		aulaRepository.save(aula);
	}
    
    
    
}
