package com.groupal.universia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia,Integer>, QueryDslPredicateExecutor<Materia>{
	

}
