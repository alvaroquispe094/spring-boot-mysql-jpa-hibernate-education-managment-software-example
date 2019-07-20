package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.CarreraMateria;
import com.groupal.universia.repositorio.CarreraMateriaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CarreraMateriaService {
	
	@Autowired
    private CarreraMateriaRepository carreraMateriaRepository;
	
	@Autowired
    private CarreraService carreraService;
	
	@Autowired
    private MateriaService materiaService;
	
	public Iterable<CarreraMateria> listAllCarreraMaterias() {
        return carreraMateriaRepository.findAll();
    }
	
	public Iterable<CarreraMateria> listAllCarreraMaterias(BooleanExpression predicate) {
        return carreraMateriaRepository.findAll(predicate);
    }
	
	
	public Page<CarreraMateria> listAllCarreraMateriasPagination(BooleanExpression predicate, Pageable pageable) {
        return carreraMateriaRepository.findAll(predicate, pageable);
    }
  
    public CarreraMateria getCarreraMateriaById(Integer id) {
        return carreraMateriaRepository.findOne(id);
    }

   
    public CarreraMateria saveCarreraMateria(Integer id, Integer idCarrera, Integer idMateria, Boolean activo) {
    	
    	CarreraMateria carreraMateria = null;
		if (id!=null){
			carreraMateria = carreraMateriaRepository.findOne(id);
		}else{
			carreraMateria = new CarreraMateria();
		}

		carreraMateria.setCarrera(carreraService.getCarreraById(idCarrera));
		if(idMateria != null) {
			carreraMateria.setMateria(materiaService.getMateriaById(idMateria));
		}
		carreraMateria.setActivo(activo);
		
		carreraMateriaRepository.save(carreraMateria);

		return carreraMateria;
    }

  
    public void deleteProfesor(Integer id) {
    	carreraMateriaRepository.delete(id);
    }


	public void cambiarEstadoCarreraMateria(Integer idCarreraMateria, boolean estado) {
		CarreraMateria carreraMateria = carreraMateriaRepository.findOne(idCarreraMateria);
		carreraMateria.setActivo(estado);
		
		carreraMateriaRepository.save(carreraMateria);
	}
    
    
    
}
