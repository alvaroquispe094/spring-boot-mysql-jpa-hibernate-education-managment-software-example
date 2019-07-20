package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Carrera;
import com.groupal.universia.repositorio.CarreraRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CarreraService {
	
	@Autowired
    private CarreraRepository carreraRepository;
	
	public Iterable<Carrera> listAllCarreras() {
        return carreraRepository.findAll();
    }
	
	public Iterable<Carrera> listAllCarreras(Sort sort) {
        return carreraRepository.findAll(sort);
    }
	
	public Page<Carrera> listAllCarrerasPagination(BooleanExpression predicate, Pageable pageable) {
        return carreraRepository.findAll(predicate, pageable);
    }
  
    public Carrera getCarreraById(Integer id) {
        return carreraRepository.findOne(id);
    }

   
    public Carrera saveCarrera(Integer id, String nombre, Boolean activo) {
    	
    	Carrera carrera = null;
		if (id!=null){
			carrera = carreraRepository.findOne(id);
		}else{
			carrera = new Carrera();
		}

		carrera.setNombre(nombre);
		carrera.setActivo(activo);
		
		carreraRepository.save(carrera);

		return carrera;
    }

  
    public void deleteProfesor(Integer id) {
    	carreraRepository.delete(id);
    }


	public void cambiarEstadoCarrera(Integer idCarrera, boolean estado) {
		Carrera carrera = carreraRepository.findOne(idCarrera);
		carrera.setActivo(estado);
		
		carreraRepository.save(carrera);
	}
    
    
    
}
