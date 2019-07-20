package com.groupal.universia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer>,QueryDslPredicateExecutor<Carrera>{
	
	
//	Page<Carrera> findAll(Predicate predicate, Pageable pageable, Sort sort);
}
