package com.groupal.universia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.InscripcionHorario;

@Repository
public interface InscripcionHorarioRepository extends JpaRepository<InscripcionHorario,Integer>, QueryDslPredicateExecutor<InscripcionHorario>{
    
}

