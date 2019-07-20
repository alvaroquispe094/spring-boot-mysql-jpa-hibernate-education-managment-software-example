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

import com.groupal.universia.modelo.Aula;
import com.groupal.universia.modelo.QAula;
import com.groupal.universia.servicio.AulaService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class AulaController {

	@Autowired
	private AulaService aulaService;
	
	 
	
	 @RequestMapping(value="/administrarAulas", method=RequestMethod.GET)
	 protected ModelAndView administrarAulas(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) {
		  
		 Map<String,Object> modelo = new HashMap<String, Object>();
		
		 BooleanExpression consulta = QAula.aula.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			consulta = consulta.and(QAula.aula.nombre.like("%" + nombre + "%"));
		 }	

		 Page<Aula> page = aulaService.listAllAulasPagination(consulta, pageable);
		 
	      modelo.put("aulas", page);
	      modelo.put("nombre", nombre);		 

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarAulas");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmAula","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaAula", method=RequestMethod.GET)
		public ModelAndView populateNuevaAula() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("aula", new Aula());
		 		 
		 return new ModelAndView("aulaForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarAula", method=RequestMethod.GET)
		public ModelAndView editarAula(@RequestParam (value="id") Integer idAula, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();
//		 	ModelAndView mav = new ModelAndView("aulaForm");

			Aula aula = aulaService.getAulaById(idAula);
			modelo.put("aula", aula);
//			mav.addObject("modelo", modelo);
			
//			mav.addObject("aula", aula);
			
			return new ModelAndView("aulaForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarAula", method = RequestMethod.POST)
	 protected ModelAndView guardarAula(@RequestParam(required=false) Integer id, @RequestParam String nombre, 
			 								@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
//		 ModelAndView mav = new ModelAndView(new RedirectView("administrarAulas?message=La aula ha sido guardada"));
//		 mav.addObject("message", "La aula ha sido dada de alta");
//		 Aula aula = new Aula();
		 aulaService.saveAula(id,nombre,activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarAulas?message=La aula ha sido guardada"));
    }
	 
	
	 @RequestMapping(value="/activarAula", method=RequestMethod.GET)
		public ModelAndView activarAula(@RequestParam (value="id") Integer idAula, Principal principal) {
			aulaService.cambiarEstadoAula(idAula, true);
			return new ModelAndView(new RedirectView("administrarAulas?message=La aula ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarAula", method=RequestMethod.GET)
		public ModelAndView desactivarAula(@RequestParam (value="id") Integer idAula, Principal principal) {
			aulaService.cambiarEstadoAula(idAula, false);
			return new ModelAndView(new RedirectView("administrarAulas?message=La aula ha sido desactivada"));
		} 
	
	
}