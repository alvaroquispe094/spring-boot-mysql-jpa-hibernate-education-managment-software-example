package com.groupal.universia.controlador;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.groupal.universia.modelo.InscripcionEstudiante;
import com.groupal.universia.modelo.InscripcionProfesor;
import com.groupal.universia.modelo.Profesor;
import com.groupal.universia.modelo.QInscripcionEstudiante;
import com.groupal.universia.modelo.QInscripcionProfesor;
import com.groupal.universia.servicio.InscripcionEstudianteService;
import com.groupal.universia.servicio.InscripcionProfesorService;
import com.groupal.universia.servicio.InscripcionService;
import com.groupal.universia.servicio.ProfesorService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class ProfesorInscripcionController {
	
	 @Autowired
	 private ProfesorService profesorService;
	
	 @Autowired
	 private InscripcionService inscripcionService;
	 
	 @Autowired
	 private InscripcionProfesorService inscripcionProfesorService;
	 
	 @Autowired
	 private InscripcionEstudianteService inscripcionEstudianteService;
	 
	 @RequestMapping(value="/administrarProfesorInscripciones", method=RequestMethod.GET)
	    protected ModelAndView administrarProfesorInscripciones(Principal principal, @PageableDefault(size = 30, sort="inscripcion.materia.nombre") Pageable pageable,
				 @RequestParam (required=false) String nombre, @RequestParam (defaultValue="",value="materia", required=false) String materia) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();
			 
			 Profesor profesor = profesorService.obtenerUsuario(principal);
			 
			 //todas las inscripciones
			 BooleanExpression consulta = QInscripcionProfesor.inscripcionProfesor.id.ne(0);
			 //todas las inscripciones del profesor
			 consulta = consulta.and(QInscripcionProfesor.inscripcionProfesor.profesor.id.eq(profesor.getId()));
			
			 
			 if(materia != null && !materia.equals("")) {
				 consulta = consulta.and(QInscripcionProfesor.inscripcionProfesor.inscripcion.materia.nombre.like("%" + materia + "%"));
			 }
			 
			 
			 Page<InscripcionProfesor> page = inscripcionProfesorService.listAllInscripcionProfesorsPagination(consulta, pageable);
			 modelo.put("inscripcionProfesores", page);
		     
			 modelo.put("materia", materia);	

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarProfesorInscripciones");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&materia="+materia);
			 return new ModelAndView("abmProfesorInscripcion","modelo",modelo);
	    }
	
	 
	 @RequestMapping(value="/administrarProfesorInscripcionEstudiantes", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionProfesors(Principal principal, @RequestParam( value = "id") Integer idInscripcion, 
			 @PageableDefault(size = 10, sort="estudiante.nombre") Pageable pageable,
			 @RequestParam (required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QInscripcionEstudiante.inscripcionEstudiante.id.ne(0);
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.inscripcion.id.eq(idInscripcion));
		 
		 if(nombre != null && nombre != "") {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.nombre.like("%" + nombre + "%"));
		 }	

		 Page<InscripcionEstudiante> page = inscripcionEstudianteService.listAllInscripcionEstudiantesPagination(consulta, pageable);
		 
	      modelo.put("inscripcionEstudiantes", page);
	      modelo.put("nombre", nombre);	
	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarEstudianteInscripcionEstudiantes");
		  modelo.put("cantidad", page.getTotalElements());
	    	
		  return new ModelAndView("abmProfesorInscripcionEstudiante","modelo",modelo);
	 }
	
}