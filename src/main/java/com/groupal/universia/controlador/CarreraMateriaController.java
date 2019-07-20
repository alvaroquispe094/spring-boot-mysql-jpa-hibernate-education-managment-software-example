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

import com.groupal.universia.modelo.Carrera;
import com.groupal.universia.modelo.CarreraMateria;
import com.groupal.universia.modelo.Materia;
import com.groupal.universia.modelo.QCarreraMateria;
import com.groupal.universia.servicio.CarreraMateriaService;
import com.groupal.universia.servicio.CarreraService;
import com.groupal.universia.servicio.MateriaService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CarreraMateriaController {
	
	
	@Autowired
	private CarreraMateriaService carreraMateriaService;
	
	@Autowired
	private CarreraService carreraService;
	
	@Autowired
	private MateriaService materiaService;
	 
	
	 @RequestMapping(value="/administrarCarreraMaterias", method=RequestMethod.GET)
	 protected ModelAndView administrarCarreraMaterias(Principal principal, @RequestParam( value = "id") Integer idCarrera, 
			 @PageableDefault(size = 30, sort="materia.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QCarreraMateria.carreraMateria.id.ne(0);
		 consulta = consulta.and(QCarreraMateria.carreraMateria.carrera.id.eq(idCarrera));
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QCarreraMateria.carreraMateria.materia.nombre.like("%" + nombre + "%"));
		 }	

//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 Pageable pageRequest = new PageRequest(0, 10, sort);
//		 Pageable page = new Pageable(10,"id");
		 
		 Page<CarreraMateria> page = carreraMateriaService.listAllCarreraMateriasPagination(consulta, pageable);
		 
	      modelo.put("carreraMaterias", page);
	      modelo.put("nombre", nombre);	
	      modelo.put("carrera", carreraService.getCarreraById(idCarrera));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarCarreraMaterias");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmCarreraMateria","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaCarreraMateria", method=RequestMethod.GET)
		public ModelAndView populateNuevaCarreraMateria(@RequestParam(value = "idCarrera") Integer idCarrera) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 modelo.put("carreraMateria", new CarreraMateria());
		 modelo.put("materia", new Materia());
		 
		 modelo.put("carrera", carreraService.getCarreraById(idCarrera));
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("materias", materiaService.listAllMaterias(sort));
		 		 
		 return new ModelAndView("carreraMateriaForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarCarreraMateria", method=RequestMethod.GET)
		public ModelAndView editarCarreraMateria(@RequestParam (value="id") Integer idCarreraMateria, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			CarreraMateria carreraMateria = carreraMateriaService.getCarreraMateriaById(idCarreraMateria);
			
			//materia Seleccionada
			modelo.put("materia", materiaService.getMateriaById(carreraMateria.getMateria().getId()));
			
			
			modelo.put("carreraMateria", carreraMateria);
			modelo.put("carrera", carreraService.getCarreraById(carreraMateria.getCarrera().getId()));
			modelo.put("materias", materiaService.listAllMaterias());

			return new ModelAndView("carreraMateriaForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarCarreraMateria", method = RequestMethod.POST)
	 protected ModelAndView guardarCarreraMateria(@RequestParam(required=false) Integer id, @RequestParam Integer idCarrera,@RequestParam(value="materia") Integer idMateria, 
			 								@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
//		 ModelAndView mav = new ModelAndView(new RedirectView("administrarCarreraMaterias?message=La carreraMateria ha sido guardada"));
//		 mav.addObject("message", "La carreraMateria ha sido dada de alta");
//		 CarreraMateria carreraMateria = new CarreraMateria();
		 carreraMateriaService.saveCarreraMateria(id, idCarrera, idMateria, activo);
		
		 
		 return new ModelAndView(new RedirectView("administrarCarreraMaterias?id="+idCarrera+ "&message=La materia ha sido guardada"));
    }
	 
	
	 @RequestMapping(value="/activarCarreraMateria", method=RequestMethod.GET)
		public ModelAndView activarCarreraMateria(@RequestParam (value="id") Integer idCarreraMateria, Principal principal) {
			carreraMateriaService.cambiarEstadoCarreraMateria(idCarreraMateria, true);
			CarreraMateria carreraMateria = carreraMateriaService.getCarreraMateriaById(idCarreraMateria);
			Carrera carrera = carreraService.getCarreraById(carreraMateria.getCarrera().getId());
			return new ModelAndView(new RedirectView("administrarCarreraMaterias?id="+carrera.getId()+ "&message=La carreraMateria ha sido activada"));
		}
	    
	    @RequestMapping(value="/desactivarCarreraMateria", method=RequestMethod.GET)
		public ModelAndView desactivarCarreraMateria(@RequestParam (value="id") Integer idCarreraMateria, Principal principal) {
			carreraMateriaService.cambiarEstadoCarreraMateria(idCarreraMateria, false);
			CarreraMateria carreraMateria = carreraMateriaService.getCarreraMateriaById(idCarreraMateria);
			Carrera carrera = carreraService.getCarreraById(carreraMateria.getCarrera().getId());
			return new ModelAndView(new RedirectView("administrarCarreraMaterias?id="+carrera.getId()+ "&message=La carreraMateria ha sido desactivada"));
		} 
	
	
}