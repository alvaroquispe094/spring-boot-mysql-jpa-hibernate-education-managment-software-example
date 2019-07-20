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
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.universia.modelo.Inscripcion;
import com.groupal.universia.modelo.InscripcionProfesor;
import com.groupal.universia.modelo.Profesor;
import com.groupal.universia.modelo.QInscripcionProfesor;
import com.groupal.universia.servicio.InscripcionProfesorService;
import com.groupal.universia.servicio.InscripcionService;
import com.groupal.universia.servicio.ProfesorService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class InscripcionProfesorController {
	
	
	@Autowired
	private InscripcionProfesorService inscripcionProfesorService;
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@Autowired
	private ProfesorService profesorService;
	
	 
	
	 @RequestMapping(value="/administrarInscripcionProfesores", method=RequestMethod.GET)
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
		 
	      modelo.put("inscripcionProfesors", page);
	      modelo.put("nombre", nombre);	
	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarInscripcionProfesores");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmInscripcionProfesor","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaInscripcionProfesor", method=RequestMethod.GET)
		public ModelAndView populateNuevaInscripcionProfesor(@RequestParam(value = "idInscripcion") Integer idInscripcion) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 modelo.put("inscripcionProfesor", new InscripcionProfesor());
		 modelo.put("profesor", new Profesor());
		 
		 modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));
		 modelo.put("profesores", profesorService.listAllProfesores());
		 		 
		 return new ModelAndView("inscripcionProfesorForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarInscripcionProfesor", method=RequestMethod.GET)
		public ModelAndView editarInscripcionProfesor(@RequestParam (value="id") Integer idInscripcionProfesor, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			InscripcionProfesor inscripcionProfesor = inscripcionProfesorService.getInscripcionProfesorById(idInscripcionProfesor);
			
			//profesor Seleccionado
			modelo.put("profesor", profesorService.getProfesorById(inscripcionProfesor.getProfesor().getId()));
			
			
			modelo.put("inscripcionProfesor", inscripcionProfesor);
			modelo.put("inscripcion", inscripcionService.getInscripcionById(inscripcionProfesor.getInscripcion().getId()));
			modelo.put("profesores", profesorService.listAllProfesores());

			return new ModelAndView("inscripcionProfesorForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarInscripcionProfesor", method = RequestMethod.POST)
	 protected ModelAndView guardarInscripcionProfesor(@RequestParam(required=false) Integer id, @RequestParam Integer idInscripcion,
			 @RequestParam(value="profesor") Integer idProfesor, 
			 @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
//		 ModelAndView mav = new ModelAndView(new RedirectView("administrarInscripcionProfesors?message=La inscripcionProfesor ha sido guardada"));
//		 mav.addObject("message", "La inscripcionProfesor ha sido dada de alta");
//		 InscripcionProfesor inscripcionProfesor = new InscripcionProfesor();
		 inscripcionProfesorService.saveInscripcionProfesor(id, idInscripcion, idProfesor, activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarInscripcionProfesores?id="+idInscripcion+ "&message=El estudiante ha sido guardado"));
    }
	 
	
	 @RequestMapping(value="/activarInscripcionProfesor", method=RequestMethod.GET)
		public ModelAndView activarInscripcionProfesor(@RequestParam (value="id") Integer idInscripcionProfesor, Principal principal) {
			inscripcionProfesorService.cambiarEstadoInscripcionProfesor(idInscripcionProfesor, true);
			InscripcionProfesor inscripcionProfesor = inscripcionProfesorService.getInscripcionProfesorById(idInscripcionProfesor);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionProfesor.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionProfesores?id="+inscripcion.getId()+ "&message=La inscripcionProfesor ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarInscripcionProfesor", method=RequestMethod.GET)
		public ModelAndView desactivarInscripcionProfesor(@RequestParam (value="id") Integer idInscripcionProfesor, Principal principal) {
			inscripcionProfesorService.cambiarEstadoInscripcionProfesor(idInscripcionProfesor, false);
			InscripcionProfesor inscripcionProfesor = inscripcionProfesorService.getInscripcionProfesorById(idInscripcionProfesor);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionProfesor.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionProfesores?id="+inscripcion.getId()+ "&message=La inscripcionProfesor ha sido desactivada"));
		} 
	
	
}