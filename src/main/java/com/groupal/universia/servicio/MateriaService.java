package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Materia;
import com.groupal.universia.repositorio.MateriaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class MateriaService {
	
	@Autowired
    private MateriaRepository materiaRepository;
	
	public Iterable<Materia> listAllMaterias() {
        return materiaRepository.findAll();
    }
	
	public Iterable<Materia> listAllMaterias(Sort sort) {
        return materiaRepository.findAll(sort);
    }
	
	
	public Page<Materia> listAllMateriasPagination(BooleanExpression predicate, Pageable pageable) {
        return materiaRepository.findAll(predicate, pageable);
    }
  
    public Materia getMateriaById(Integer id) {
        return materiaRepository.findOne(id);
    }

   
    public Materia saveMateria(Integer id, String nombre, Boolean activo) {
    	
    	Materia materia = null;
		if (id!=null){
			materia = materiaRepository.findOne(id);
		}else{
			materia = new Materia();
		}

		materia.setNombre(nombre);
		materia.setActivo(activo);
		
		materiaRepository.save(materia);

		return materia;
    }

  
    public void deleteMateria(Integer id) {
    	materiaRepository.delete(id);
    }


	public void cambiarEstadoMateria(Integer idMateria, boolean estado) {
		Materia materia = materiaRepository.findOne(idMateria);
		materia.setActivo(estado);
		
		materiaRepository.save(materia);
	}
    
    
    
}
