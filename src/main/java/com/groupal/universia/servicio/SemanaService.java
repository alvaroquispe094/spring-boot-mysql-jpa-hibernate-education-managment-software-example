package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Semana;
import com.groupal.universia.repositorio.SemanaRepository;

@Service
public class SemanaService {
	
	@Autowired
    private SemanaRepository semanaRepository;
	
	public Iterable<Semana> listAllSemanas() {
        return semanaRepository.findAll();
    }
	
    public Semana getSemanaById(Integer id) {
        return semanaRepository.findOne(id);
    }

   
    
}
