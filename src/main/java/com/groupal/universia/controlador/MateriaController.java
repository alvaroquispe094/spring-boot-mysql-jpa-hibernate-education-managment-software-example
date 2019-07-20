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

import com.groupal.universia.modelo.Materia;
import com.groupal.universia.modelo.QMateria;
import com.groupal.universia.servicio.MateriaService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class MateriaController {
	
	@Autowired
	private MateriaService materiaService;
	
	
	 @RequestMapping(value="/administrarMaterias", method=RequestMethod.GET)
	 protected ModelAndView administrarMaterias(Principal principal, @PageableDefault(size = 30) Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) {
		 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();

		 BooleanExpression consulta = QMateria.materia.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			consulta = consulta.and(QMateria.materia.nombre.like("%" + nombre + "%"));
		 }	
		 
		 Page<Materia> page = materiaService.listAllMateriasPagination(consulta, pageable);
		 modelo.put("materias", page);
		 modelo.put("nombre", nombre);		 
		 	
		 //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "administrarMaterias");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&nombre="+nombre);
		 return new ModelAndView("abmMateria","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaMateria", method=RequestMethod.GET)
		public ModelAndView populateNuevaMateria() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("materia", new Materia());
		 		 
		 return new ModelAndView("materiaForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarMateria", method=RequestMethod.GET)
		public ModelAndView editarMateria(@RequestParam (value="id") Integer idMateria, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();
//		 	ModelAndView mav = new ModelAndView("carreraForm");

			Materia materia = materiaService.getMateriaById(idMateria);
			modelo.put("materia", materia);
//			mav.addObject("modelo", modelo);
			
//			mav.addObject("carrera", carrera);
			
			return new ModelAndView("materiaForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarMateria", method = RequestMethod.POST)
	 protected ModelAndView guardarMateria(@RequestParam(required=false) Integer id, @RequestParam String nombre, 
			 								@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
//		 ModelAndView mav = new ModelAndView(new RedirectView("administrarCarreras?message=La carrera ha sido guardada"));
//		 mav.addObject("message", "La carrera ha sido dada de alta");
//		 Carrera carrera = new Carrera();
		 materiaService.saveMateria(id,nombre,activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarMaterias?message=La materia ha sido guardada"));
    }
	 
	
	 @RequestMapping(value="/activarMateria", method=RequestMethod.GET)
		public ModelAndView activarMateria(@RequestParam (value="id") Integer idMateria, Principal principal) {
			materiaService.cambiarEstadoMateria(idMateria, true);
			return new ModelAndView(new RedirectView("administrarMaterias?message=La materia ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarMateria", method=RequestMethod.GET)
		public ModelAndView desactivarMateria(@RequestParam (value="id") Integer idMateria, Principal principal) {
			materiaService.cambiarEstadoMateria(idMateria, false);
			return new ModelAndView(new RedirectView("administrarMaterias?message=La materia ha sido desactivada"));
		} 
	
	
}