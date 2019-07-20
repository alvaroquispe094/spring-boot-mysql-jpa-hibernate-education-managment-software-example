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

import com.groupal.universia.modelo.Estudiante;
import com.groupal.universia.modelo.InscripcionEstudiante;
import com.groupal.universia.modelo.QInscripcionEstudiante;
import com.groupal.universia.servicio.EstudianteService;
import com.groupal.universia.servicio.InscripcionEstudianteService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class EstudianteInscripcionMateriaController {
	

	@Autowired
	private EstudianteService estudianteService;
	
	@Autowired
	private InscripcionEstudianteService inscripcionEstudianteService;
	 
	
	 @RequestMapping(value="/administrarEstudianteInscripcionMaterias", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionMaterias(Principal principal,  
			 @PageableDefault(size = 30, sort="inscripcion.materia.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
		 
		 BooleanExpression consulta = QInscripcionEstudiante.inscripcionEstudiante.estudiante.id.ne(0);
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.id.eq(estudiante.getId()));
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.inscripcion.materia.nombre.like("%" + nombre + "%"));
		 }	

		 Page<InscripcionEstudiante> page = inscripcionEstudianteService.listAllInscripcionEstudiantesPagination(consulta, pageable);
		 

	      modelo.put("inscripcionEstudiantes", page);
	      modelo.put("nombre", nombre);	
	      

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarEstudianteInscripcionMaterias");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmEstudianteInscripcionMateria","modelo",modelo);
	 }
	 
	 
	
	
}