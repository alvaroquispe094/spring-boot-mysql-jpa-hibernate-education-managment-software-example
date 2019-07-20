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

import com.groupal.universia.modelo.InscripcionProfesor;
import com.groupal.universia.modelo.QInscripcionProfesor;
import com.groupal.universia.servicio.InscripcionProfesorService;
import com.groupal.universia.servicio.InscripcionService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class EstudianteInscripcionProfesorController {
	
	
	@Autowired
	private InscripcionProfesorService inscripcionProfesorService;
	
	@Autowired
	private InscripcionService inscripcionService;
	 
	
	 @RequestMapping(value="/administrarEstudianteInscripcionProfesores", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionProfesors(Principal principal, @RequestParam( value = "id") Integer idInscripcion, 
			 @PageableDefault(size = 30, sort="profesor.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QInscripcionProfesor.inscripcionProfesor.id.ne(0);
		 consulta = consulta.and(QInscripcionProfesor.inscripcionProfesor.inscripcion.id.eq(idInscripcion));
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QInscripcionProfesor.inscripcionProfesor.profesor.nombre.like("%" + nombre + "%"));
		 }	

		 Page<InscripcionProfesor> page = inscripcionProfesorService.listAllInscripcionProfesorsPagination(consulta, pageable);
		 
	      modelo.put("inscripcionProfesores", page);
	      modelo.put("nombre", nombre);	
	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarEstudianteInscripcionProfesores");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmEstudianteInscripcionProfesor","modelo",modelo);
	 }
	 
	 
	
	
}