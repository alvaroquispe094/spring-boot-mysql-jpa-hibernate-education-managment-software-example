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

import com.groupal.universia.modelo.Carrera;
import com.groupal.universia.modelo.QCarrera;
import com.groupal.universia.servicio.CarreraService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CarreraController {
	
	
	@Autowired
	private CarreraService carreraService;
	 
	
	 @RequestMapping(value="/administrarCarreras", method=RequestMethod.GET)
	 protected ModelAndView administrarCarreras(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QCarrera.carrera.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QCarrera.carrera.nombre.like("%" + nombre + "%"));
		 }	

//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 Pageable pageRequest = new PageRequest(0, 10, sort);
//		 Pageable page = new Pageable(10,"id");
		 
		 Page<Carrera> page = carreraService.listAllCarrerasPagination(consulta, pageable);
		 
	      modelo.put("carreras", page);
	      modelo.put("nombre", nombre);		 

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarCarreras");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);	
		  return new ModelAndView("abmCarrera","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaCarrera", method=RequestMethod.GET)
		public ModelAndView populateNuevaCarrera() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("carrera", new Carrera());
		 		 
		 return new ModelAndView("carreraForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarCarrera", method=RequestMethod.GET)
		public ModelAndView editarCarrera(@RequestParam (value="id") Integer idCarrera, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Carrera carrera = carreraService.getCarreraById(idCarrera);
			modelo.put("carrera", carrera);
			
			return new ModelAndView("carreraForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarCarrera", method = RequestMethod.POST)
	 protected ModelAndView guardarCarrera(@RequestParam(required=false) Integer id, @RequestParam String nombre, 
			 								@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		 carreraService.saveCarrera(id,nombre,activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarCarreras?message=La carrera ha sido guardada"));
    }
	 
	
	 @RequestMapping(value="/activarCarrera", method=RequestMethod.GET)
		public ModelAndView activarCarrera(@RequestParam (value="id") Integer idCarrera, Principal principal) {
			carreraService.cambiarEstadoCarrera(idCarrera, true);
			return new ModelAndView(new RedirectView("administrarCarreras?message=La carrera ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarCarrera", method=RequestMethod.GET)
		public ModelAndView desactivarCarrera(@RequestParam (value="id") Integer idCarrera, Principal principal) {
			carreraService.cambiarEstadoCarrera(idCarrera, false);
			return new ModelAndView(new RedirectView("administrarCarreras?message=La carrera ha sido desactivada"));
		} 
	
	
}