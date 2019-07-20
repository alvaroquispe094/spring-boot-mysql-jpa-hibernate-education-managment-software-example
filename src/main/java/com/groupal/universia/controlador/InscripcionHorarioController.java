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

import com.groupal.universia.modelo.Aula;
import com.groupal.universia.modelo.Inscripcion;
import com.groupal.universia.modelo.InscripcionHorario;
import com.groupal.universia.modelo.QInscripcionHorario;
import com.groupal.universia.modelo.Semana;
import com.groupal.universia.servicio.AulaService;
import com.groupal.universia.servicio.InscripcionHorarioService;
import com.groupal.universia.servicio.InscripcionService;
import com.groupal.universia.servicio.SemanaService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class InscripcionHorarioController {
	
	
	@Autowired
	private InscripcionHorarioService inscripcionHorarioService;
	
	@Autowired
	private InscripcionService inscripcionService;
	
	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private SemanaService semanaService; 
	
	
	 @RequestMapping(value="/administrarInscripcionHorarios", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionHorarios(Principal principal, @RequestParam( value = "id") Integer idInscripcion, 
			 @PageableDefault(size = 30) Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QInscripcionHorario.inscripcionHorario.id.ne(0);
		 consulta = consulta.and(QInscripcionHorario.inscripcionHorario.inscripcion.id.eq(idInscripcion));
		 
		 if(nombre != null && !nombre.equals("")) {
//			 consulta = consulta.and(QInscripcionHorario.inscripcionHorario.hora_inicio.like("%" + nombre + "%"));
		 }	

		 Page<InscripcionHorario> page = inscripcionHorarioService.listAllInscripcionHorariosPagination(consulta, pageable);
		 
	      modelo.put("inscripcionHorarios", page);
	      modelo.put("nombre", nombre);	
	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarInscripcionHorarios");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "");
		  return new ModelAndView("abmInscripcionHorario","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaInscripcionHorario", method=RequestMethod.GET)
		public ModelAndView populateNuevaInscripcionHorario(@RequestParam(value = "idInscripcion") Integer idInscripcion) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 modelo.put("inscripcionHorario", new InscripcionHorario());
		 modelo.put("semana", new Semana());
		 modelo.put("aula", new Aula());
		 
		 modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("aulas", aulaService.listAllAulas(sort));
		 modelo.put("semanas", semanaService.listAllSemanas());
		 
		 return new ModelAndView("inscripcionHorarioForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarInscripcionHorario", method=RequestMethod.GET)
		public ModelAndView editarInscripcionHorario(@RequestParam (value="id") Integer idInscripcionHorario, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			InscripcionHorario inscripcionHorario = inscripcionHorarioService.getInscripcionHorarioById(idInscripcionHorario);
			
			//materia Seleccionada
//			modelo.put("estudiante", estudianteService.getEstudianteById(inscripcionHorario.getEstudiante().getId()));
			modelo.put("semana", semanaService.getSemanaById(inscripcionHorario.getSemana().getId()));
			modelo.put("aula", aulaService.getAulaById(inscripcionHorario.getAula().getId()));
			
			modelo.put("inscripcionHorario", inscripcionHorario);
			modelo.put("inscripcion", inscripcionService.getInscripcionById(inscripcionHorario.getInscripcion().getId()));
			modelo.put("aulas", aulaService.listAllAulas());
			 modelo.put("semanas", semanaService.listAllSemanas());

			return new ModelAndView("inscripcionHorarioForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarInscripcionHorario", method = RequestMethod.POST)
	 protected ModelAndView guardarInscripcionHorario(@RequestParam(required=false) Integer id, @RequestParam Integer idInscripcion,
			 @RequestParam(value="aula") Integer idAula, @RequestParam(value="semana") Integer idSemana,
			 @RequestParam String horaInicio,  @RequestParam String horaFin,
			 @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){

		 inscripcionHorarioService.saveInscripcionHorario(id, idInscripcion, idSemana, horaInicio, horaFin, idAula, activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarInscripcionHorarios?id="+idInscripcion+ "&message=El horario ha sido guardado"));
    }
	 
	
	 @RequestMapping(value="/activarInscripcionHorario", method=RequestMethod.GET)
		public ModelAndView activarInscripcionHorario(@RequestParam (value="id") Integer idInscripcionHorario, Principal principal) {
			inscripcionHorarioService.cambiarEstadoInscripcionHorario(idInscripcionHorario, true);
			InscripcionHorario inscripcionHorario = inscripcionHorarioService.getInscripcionHorarioById(idInscripcionHorario);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionHorario.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionHorarios?id="+inscripcion.getId()+ "&message=La inscripcionHorario ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarInscripcionHorario", method=RequestMethod.GET)
		public ModelAndView desactivarInscripcionHorario(@RequestParam (value="id") Integer idInscripcionHorario, Principal principal) {
			inscripcionHorarioService.cambiarEstadoInscripcionHorario(idInscripcionHorario, false);
			InscripcionHorario inscripcionHorario = inscripcionHorarioService.getInscripcionHorarioById(idInscripcionHorario);
			Inscripcion inscripcion = inscripcionService.getInscripcionById(inscripcionHorario.getInscripcion().getId());
			return new ModelAndView(new RedirectView("administrarInscripcionHorarios?id="+inscripcion.getId()+ "&message=La inscripcionHorario ha sido desactivada"));
		} 
	
	
}