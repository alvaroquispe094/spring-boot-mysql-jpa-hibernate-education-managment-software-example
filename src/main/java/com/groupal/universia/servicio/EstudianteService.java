package com.groupal.universia.servicio;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.Estudiante;
import com.groupal.universia.modelo.QEstudiante;
import com.groupal.universia.repositorio.EstudianteRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class EstudianteService {

	@Autowired
    private EstudianteRepository estudianteRepository;
	
	@Autowired
    private CarreraService carreraService;
  
    public Iterable<Estudiante> listAllStudents() {
        return estudianteRepository.findAll();
    }
    
    public Iterable<Estudiante> listAllStudents(Sort sort) {
        return estudianteRepository.findAll(sort);
    }

    public Page<Estudiante> listAllEstudiantesPagination(BooleanExpression predicate, Pageable pageable) {
        return estudianteRepository.findAll(predicate, pageable);
    }
	
  
    public Estudiante getEstudianteById(Integer id) {
        return estudianteRepository.getOne(id);
    }
    
    public Estudiante getEstudianteById(BooleanExpression predicate) {
        return estudianteRepository.findOne(predicate);
    }
   
    public Estudiante saveEstudiante(Integer id, String nombre, String apellido, Integer documento, String usuario, String clave, String direccion,String fecha, String mail, String telefono,Integer idCarrera, Boolean activo) {
    	
    	Estudiante estudiante = null;
		if (id!=null){
			estudiante = estudianteRepository.getOne(id);
		}else{
			estudiante = new Estudiante();
		}

		estudiante.setNombre(nombre);
		estudiante.setApellido(apellido);
		estudiante.setDocumento(documento);
		estudiante.setUsername(usuario);
		estudiante.setPassword(clave);
		estudiante.setDireccion(direccion);
		estudiante.setFecha(FechaHelper.convertirFechaADate(fecha, "dd/MM/yyyy"));
		estudiante.setMail(mail);
		estudiante.setTelefono(telefono);
		estudiante.setCarrera(carreraService.getCarreraById(idCarrera));
		estudiante.setActivo(activo);
		estudianteRepository.save(estudiante);

		return estudiante;
    }

  
    public void deleteEstudiante(Integer id) {
    	estudianteRepository.delete(id);
    }
	
    public void cambiarEstadoEstudiante(Integer idEstudiante, Boolean estado ) {
		Estudiante estudiante = estudianteRepository.findOne(idEstudiante);
		estudiante.setActivo(estado);
		estudianteRepository.save(estudiante);		
	}
    
    public Estudiante obtenerUsuario(Principal principal) {

    	BooleanExpression consulta = QEstudiante.estudiante.id.ne(0);
    	consulta = consulta.and(QEstudiante.estudiante.username.eq(principal.getName()));
    	Estudiante estudiante = estudianteRepository.findOne(consulta);
    	return estudiante;
    }
    
}
