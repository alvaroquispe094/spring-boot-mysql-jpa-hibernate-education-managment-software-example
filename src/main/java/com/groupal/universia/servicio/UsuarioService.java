package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Usuario;
import com.groupal.universia.repositorio.UsuarioRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	public Iterable<Usuario> listAllUsuarioes() {
        return usuarioRepository.findAll();
    }

	public Page<Usuario> listAllUsuariosPagination(BooleanExpression predicate, Pageable pageable) {
        return usuarioRepository.findAll(predicate, pageable);
    }
	
  
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.getOne(id);
    }
    
    public Usuario getUsuarioById(BooleanExpression predicate) {
        return usuarioRepository.findOne(predicate);
    }
    

   
    public Usuario saveUsuario(Integer id, String username, String clave, String mail, Boolean activo) {
    	
    	Usuario usuario = null;
		if (id!=null){
			usuario = usuarioRepository.getOne(id);
		}else{
			usuario = new Usuario();
		}

		
		usuario.setUsername(username);
		usuario.setPassword(clave);
		
		usuario.setMail(mail);
		
		usuario.setActivo(activo);
		
		usuarioRepository.save(usuario);

		return usuario;
    }

  
    public void deleteUsuario(Integer id) {
    	usuarioRepository.delete(id);
    }

	public void cambiarEstadoUsuario(Integer idUsuario, Boolean estado ) {
		Usuario usuario = usuarioRepository.findOne(idUsuario);
		usuario.setActivo(estado);
		usuarioRepository.save(usuario);		
	}
	
	
}
