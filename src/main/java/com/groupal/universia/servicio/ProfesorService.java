package com.groupal.universia.servicio;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Profesor;
import com.groupal.universia.modelo.QProfesor;
import com.groupal.universia.repositorio.ProfesorRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class ProfesorService {
	
	@Autowired
    private ProfesorRepository profesorRepository;
	
	public Iterable<Profesor> listAllProfesores() {
        return profesorRepository.findAll();
    }

	public Page<Profesor> listAllProfesoresPagination(BooleanExpression predicate, Pageable pageable) {
        return profesorRepository.findAll(predicate, pageable);
    }
	
  
    public Profesor getProfesorById(Integer id) {
        return profesorRepository.getOne(id);
    }

    public Profesor getProfesorById(BooleanExpression predicate) {
        return profesorRepository.findOne(predicate);
    }
   
    public Profesor saveProfesor(Integer id, String nombre, String apellido,Integer documento, String usuario, String clave, String direccion,String fecha, String mail, String telefono, String titulo, Boolean activo) {
    	
    	Profesor profesor = null;
		if (id!=null){
			profesor = profesorRepository.getOne(id);
		}else{
			profesor = new Profesor();
		}

		profesor.setNombre(nombre);
		profesor.setApellido(apellido);
		profesor.setDocumento(documento);
		profesor.setUsername(usuario);
		profesor.setPassword(clave);
		profesor.setDireccion(direccion);
		profesor.setFecha(FechaHelper.convertirFechaADate(fecha, "dd/MM/yyyy"));
		profesor.setMail(mail);
		profesor.setTelefono(telefono);
		profesor.setTitulo(titulo);
		profesor.setActivo(activo);
		
		profesorRepository.save(profesor);

		return profesor;
    }

  
    public void deleteProfesor(Integer id) {
    	profesorRepository.delete(id);
    }

	public void cambiarEstadoProfesor(Integer idProfesor, Boolean estado ) {
		Profesor profesor = profesorRepository.findOne(idProfesor);
//		profesor.setEnabled(estado);
		profesor.setActivo(estado);
		profesorRepository.save(profesor);		
	}
	
	public Profesor obtenerUsuario(Principal principal) {

    	BooleanExpression consulta = QProfesor.profesor.id.ne(0);
    	consulta = consulta.and(QProfesor.profesor.username.eq(principal.getName()));
    	Profesor profesor = profesorRepository.findOne(consulta);
    	return profesor;
    }
}
