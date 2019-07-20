package com.groupal.universia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Integer>, QueryDslPredicateExecutor<Estudiante>{
    
}

