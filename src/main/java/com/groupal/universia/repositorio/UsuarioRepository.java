package com.groupal.universia.repositorio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.Usuario;

@Transactional
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>, QueryDslPredicateExecutor<Usuario>{
    
}

