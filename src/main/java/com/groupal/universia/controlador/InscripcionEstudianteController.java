package com.groupal.universia.controlador;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.groupal.universia.modelo.QInscripcionEstudiante;
import com.groupal.universia.servicio.EstudianteService;
import com.groupal.universia.servicio.InscripcionEstudianteService;
import com.groupal.universia.servicio.InscripcionService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class InscripcionEstudianteController {
	
	
	@Autowired
	private InscripcionEstudianteService inscripcionEstudianteService;
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@Autowired
	private EstudianteService estudianteService;
	
	 
	
	 @RequestMapping(value="/administrarInscripcionEstudiantes", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionEstudiantes(Principal principal, @RequestParam( value = "id") Integer idInscripcion, 
			 @PageableDefault(size = 30, sort="estudiante.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
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
		  modelo.put("url", "administrarInscripcionEstudiantes");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmInscripcionEstudiante","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaInscripcionEstudiante", method=RequestMethod.GET)
		public ModelAndView populateNuevaInscripcionEstudiante(@RequestParam(value = "idInscripcion") Integer idInscripcion) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 InscripcionEstudiante inscripcionEstudiante = new InscripcionEstudiante();
		 Estado estado = new Estado();
		 estado.setId(5);
		 inscripcionEstudiante.setEstado(estado);
		 modelo.put("inscripcionEstudiante", inscripcionEstudiante);
		 modelo.put("estudiante", new Estudiante());
		 
		 modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("estudiantes", estudianteService.listAllStudents(sort));
		 		 
		 return new ModelAndView("inscripcionEstudianteForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarInscripcionEstudiante", method=RequestMethod.GET)
		public ModelAndView editarInscripcionEstudiante(@RequestParam (value="id") Integer idInscripcionEstudiante, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante);
			
			//materia Seleccionada
			modelo.put("estudiante", estudianteService.getEstudianteById(inscripcionEstudiante.getEstudiante().getId()));
			
			
			modelo.put("inscripcionEstudiante", inscripcionEstudiante);
			modelo.put("inscripcion", inscripcionService.getInscripcionById(inscripcionEstudiante.getInscripcion().getId()));
			modelo.put("estudiantes", estudianteService.listAllStudents());

			return new ModelAndView("inscripcionEstudianteForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarInscripcionEstudiante", method = RequestMethod.POST)
	 protected ModelAndView guardarInscripcionEstudiante(@RequestParam(required=false) Integer id, @RequestParam Integer idInscripcion,
			 @RequestParam(value="estudiante") Integer idEstudiante,  @RequestParam(value="estado", defaultValue="5") Integer idEstado,
			 @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){

		 inscripcionEstudianteService.saveInscripcionEstudiante(id, idInscripcion, idEstudiante, idEstado, activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarInscripcionEstudiantes?id="+idInscripcion+ "&message=El estudiante ha sido guardado"));
    }
	 
	
	 @RequestMapping(value="/activarInscripcionEstudiante", method=RequestMethod.GET)
		public ModelAndView activarInscripcionEstudiante(@RequestParam (value="id") Integer idInscripcionEstudiante, Principal principal) {
			inscripcionEstudianteService.cambiarEstadoInscripcionEstudiante(idInscripcionEstudiante, true);
			InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionEstudiante.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionEstudiantes?id="+inscripcion.getId()+ "&message=La inscripcionEstudiante ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarInscripcionEstudiante", method=RequestMethod.GET)
		public ModelAndView desactivarInscripcionEstudiante(@RequestParam (value="id") Integer idInscripcionEstudiante, Principal principal) {
			inscripcionEstudianteService.cambiarEstadoInscripcionEstudiante(idInscripcionEstudiante, false);
			InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionEstudiante.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionEstudiantes?id="+inscripcion.getId()+ "&message=La inscripcionEstudiante ha sido desactivada"));
		} 
	    
	    
	    
	    	
	
}