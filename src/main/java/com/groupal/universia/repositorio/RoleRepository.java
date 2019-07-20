package com.groupal.universia.repositorio;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupal.universia.modelo.Role;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
  
}