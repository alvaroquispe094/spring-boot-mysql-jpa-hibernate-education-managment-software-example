package com.groupal.universia.controlador;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.universia.modelo.Estado;
import com.groupal.universia.modelo.Estudiante;
import com.groupal.universia.modelo.Inscripcion;
import com.groupal.universia.modelo.InscripcionEstudiante;
import com.groupal.universia.modelo.QInscripcion;
import com.groupal.universia.modelo.QInscripcionEstudiante;
import com.groupal.universia.servicio.EstudianteService;
import com.groupal.universia.servicio.InscripcionEstudianteService;
import com.groupal.universia.servicio.InscripcionService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class EstudianteInscripcionController {
	
	 @Autowired
	 private EstudianteService estudianteService;
	
	 @Autowired
	 private InscripcionService inscripcionService;
	 
	 @Autowired
	 private InscripcionEstudianteService inscripcionEstudianteService;
	 
	 @RequestMapping(value="/administrarEstudianteInscripciones", method=RequestMethod.GET)
	    protected ModelAndView administrarInscripciones(Principal principal, @PageableDefault(size = 30, sort="materia.nombre") Pageable pageable,
				 @RequestParam (defaultValue="",value="materia", required=false) String materia) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();
			 
			 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
			 
			 //todas las inscripciones
			 BooleanExpression consulta = QInscripcion.inscripcion.id.ne(0);
			 //solo las inscripciones de la carrera del estudiante
			 consulta = consulta.and(QInscripcion.inscripcion.carrera.id.eq(estudiante.getCarrera().getId()));
			 //solo las inscripciones publicadas
			 consulta = consulta.and(QInscripcion.inscripcion.estado.id.eq(Estado.PUBLICADA));
			 
			 //validar mostrar solo las inscripciones a las que no se inscribio
			 BooleanExpression consulta2 = QInscripcionEstudiante.inscripcionEstudiante.estudiante.id.eq(estudiante.getId()); //obtengo todas la inscripciones del estudiante
			 Iterable<InscripcionEstudiante> inscripcionEstudiantes = inscripcionEstudianteService.listAllInscripcionEstudiantes(consulta2);
			 List<Integer> idListado = new ArrayList<Integer>();
			 for(InscripcionEstudiante inscripcionEstudiante:inscripcionEstudiantes) {
				 idListado.add(inscripcionEstudiante.getInscripcion().getId());
			 }
			 if(idListado.size() != 0) {
			 	for(Integer id :idListado) { // agrega a la condicion que no sean los que ya estan en la lista
				 	consulta = consulta.and(QInscripcion.inscripcion.id.ne(id));
			 	}
			 }	
			 
			 
			 if(materia != null && !materia.equals("")) {
				 consulta = consulta.and(QInscripcion.inscripcion.materia.nombre.like("%" + materia + "%"));
			 }
			 
			 
			 Page<Inscripcion> page = inscripcionService.listAllInscripcionesPagination(consulta, pageable);
			 modelo.put("inscripciones", page);
		     
			 modelo.put("materia", materia);	

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarEstudianteInscripciones");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&materia="+materia); 
			 return new ModelAndView("abmEstudianteInscripcion","modelo",modelo);
	    }
	
	 
	 @RequestMapping(value = "/guardarEstudianteInscripcion", method = RequestMethod.GET)
	 protected ModelAndView guardarInscripcionEstudiante(@RequestParam(required=false) Integer id, @RequestParam Integer idInscripcion,
			 @RequestParam(value="estado", defaultValue="5") Integer idEstado,
			 @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
		 
		 inscripcionEstudianteService.saveInscripcionEstudiante(id, idInscripcion, estudiante.getId(), idEstado, activo);
		 inscripcionService.restarCantidadCupos(idInscripcion);
		
		 
		 return new ModelAndView(new RedirectView("administrarEstudianteInscripciones?message=Has sido inscrito en la materia seleccionada"));
    }
	 
	 @RequestMapping(value = "/eliminarEstudianteInscripcion", method = RequestMethod.GET)
	 protected ModelAndView eliminarEstudianteInscripcion(@RequestParam(value="id",required=false) Integer idInscripcionEstudiante, 
			  Principal principal){
		 InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante);
		 inscripcionEstudianteService.deleteInscripcionEstudiante(idInscripcionEstudiante);
		 inscripcionService.sumarCantidadCupos(inscripcionEstudiante.getInscripcion().getId());
		 
		 return new ModelAndView(new RedirectView("administrarEstudianteInscripcionMaterias?message=Has sido borrado en la materia"));
    } 
	
	 @RequestMapping(value = "/eliminarInscripcionEstudiante", method = RequestMethod.GET)
	 protected ModelAndView eliminarInscripcionEstudiante(@RequestParam(value="id",required=false) Integer idInscripcion, 
			  Principal principal){
		 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
		 
		 //traigo la inscripcion estudiante correspondiente
		 BooleanExpression consulta = QInscripcionEstudiante.inscripcionEstudiante.id.ne(0);
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.inscripcion.id.eq(idInscripcion));
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.id.eq(estudiante.getId()));
		 
		 InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(consulta);
		 inscripcionEstudianteService.deleteInscripcionEstudiante(inscripcionEstudiante.getId());
		 inscripcionService.sumarCantidadCupos(inscripcionEstudiante.getInscripcion().getId());
		 
		 return new ModelAndView(new RedirectView("administrarEstudianteInscripciones?message=Has sido borrado en la materia"));
    } 
	
}