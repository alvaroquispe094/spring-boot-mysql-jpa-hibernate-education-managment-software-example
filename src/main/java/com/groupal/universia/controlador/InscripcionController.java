package com.groupal.universia.controlador;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupal.universia.modelo.Carrera;
import com.groupal.universia.modelo.CarreraMateria;
import com.groupal.universia.modelo.Estado;
import com.groupal.universia.modelo.Inscripcion;
import com.groupal.universia.modelo.Materia;
import com.groupal.universia.modelo.QCarreraMateria;
import com.groupal.universia.modelo.QInscripcion;
import com.groupal.universia.servicio.CarreraMateriaService;
import com.groupal.universia.servicio.CarreraService;
import com.groupal.universia.servicio.InscripcionService;
import com.groupal.universia.servicio.MateriaService;
import com.querydsl.core.types.dsl.BooleanExpression;


@Controller
public class InscripcionController {
	
	 @Autowired
	 private InscripcionService inscripcionService;
	 
	 @Autowired
	 private CarreraService carreraService;
	 
	 @Autowired
	 private CarreraMateriaService carreraMateriaService;
	 
	 @Autowired
	 private MateriaService materiaService;


	 @RequestMapping(value="/administrarInscripciones", method=RequestMethod.GET)
	    protected ModelAndView administrarInscripciones(Principal principal, @PageableDefault(size = 30, sort="carrera.nombre") Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String materia, @RequestParam (defaultValue="",value="carrera", required=false) Integer idCarrera) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QInscripcion.inscripcion.id.ne(0);
			 
			 if(materia != null && !materia.equals("")) {
				 consulta = consulta.and(QInscripcion.inscripcion.materia.nombre.like("%" + materia + "%"));
			 }
			 
			 if(idCarrera != null) {
				 consulta = consulta.and(QInscripcion.inscripcion.carrera.id.eq(idCarrera));
			 }
			 
			 Page<Inscripcion> page = inscripcionService.listAllInscripcionesPagination(consulta, pageable);
			 modelo.put("inscripciones", page);
		     
			 modelo.put("carreras", carreraService.listAllCarreras());
		     modelo.put("carrera", idCarrera);
		     modelo.put("materia", materia);
		     
		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarInscripciones");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&materia="+materia+"&carrera="+(idCarrera != null ? idCarrera : ""));
			 return new ModelAndView("abmInscripcion","modelo",modelo);
	    }
		
	 
	 @RequestMapping(value="/nuevaInscripcion", method=RequestMethod.GET)
	 public ModelAndView populateNuevaInscripcion() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("inscripcion", new Inscripcion());
		 modelo.put("carrera", new Carrera());
		 modelo.put("materia", new Materia());
		 
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("carreras", carreraService.listAllCarreras(sort));
		 modelo.put("materias", materiaService.listAllMaterias(sort));
		 
		 return new ModelAndView("inscripcionForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarInscripcion", method=RequestMethod.GET)
		public ModelAndView editarInscripcion(@RequestParam (value="id") Integer idInscripcion, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Inscripcion inscripcion = inscripcionService.getInscripcionById(idInscripcion);
			
			//carrera Seleccionada
			modelo.put("carrera", carreraService.getCarreraById(inscripcion.getCarrera().getId()));
			//materia Seleccionada
			List<Materia> materias = new ArrayList<Materia>();
			BooleanExpression consulta = QCarreraMateria.carreraMateria.carrera.id.eq(inscripcion.getCarrera().getId());
			Iterable<CarreraMateria> carreraMaterias = carreraMateriaService.listAllCarreraMaterias(consulta);
			for(CarreraMateria carreraMateria: carreraMaterias) {
				materias.add(carreraMateria.getMateria());
			}
			modelo.put("materia", materiaService.getMateriaById(inscripcion.getMateria().getId()));
			
			
			modelo.put("carreras", carreraService.listAllCarreras());
			modelo.put("materias", materias);
			modelo.put("inscripcion", inscripcion);

			return new ModelAndView("inscripcionForm","modelo",modelo);
		}
	 
	 @RequestMapping(value = "/guardarInscripcion", method = RequestMethod.POST)
	 protected ModelAndView guardarInscripcion(@RequestParam Integer id,  
			 @RequestParam(value="carrera") Integer idCarrera, @RequestParam(value="materia") Integer idMateria,
			 @RequestParam String fechaInicio,  @RequestParam String fechaFin, @RequestParam Integer cantidad,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
        
		 inscripcionService.saveInscripcion(id,idCarrera, idMateria, fechaInicio, fechaFin,cantidad,Estado.NO_PUBLICADA,activo);
		 System.out.println("save inscripcion");
		 
		 return new ModelAndView(new RedirectView("administrarInscripciones?message=La inscripcion ha sido dada de alta"));
    }
	
	 
	@RequestMapping(value="/activarInscripcion", method=RequestMethod.GET)
		public ModelAndView activarInscripcion(@RequestParam (value="id") Integer idInscripcion, Principal principal) {
			inscripcionService.cambiarEstadoInscripcion(idInscripcion, true);
			return new ModelAndView(new RedirectView("administrarInscripciones?message=La inscripcion ha sido activada"));
	}
	    
	 @RequestMapping(value="/desactivarInscripcion", method=RequestMethod.GET)
		public ModelAndView desactivarrInscripcion(@RequestParam (value="id") Integer idInscripcion, Principal principal) {
			inscripcionService.cambiarEstadoInscripcion(idInscripcion, false);
			return new ModelAndView(new RedirectView("administrarInscripciones?message=La inscripcion ha sido desactivada"));
	}
	 
	 
	 @RequestMapping(value="/publicarInscripcion", method=RequestMethod.GET)
		public ModelAndView publicarInscripcion(@RequestParam (value="id") Integer idInscripcion, Principal principal) {
//			Usuario usuario = usuarioServiceManager.obtenerUsuario(principal);
//			Turno turno = turnoManager.get(idTurno);
			inscripcionService.cambiarEstadoPublicacion(idInscripcion, Estado.PUBLICADA);
			
			return new ModelAndView(new RedirectView("administrarInscripciones?message=La inscripcion ha sido publicada"));
			
		}
	 
	 @RequestMapping(value="/despublicarInscripcion", method=RequestMethod.GET)
		public ModelAndView despublicarInscripcion(@RequestParam (value="id") Integer idInscripcion, Principal principal) {
//			Usuario usuario = usuarioServiceManager.obtenerUsuario(principal);
//			Turno turno = turnoManager.get(idTurno);
			inscripcionService.cambiarEstadoPublicacion(idInscripcion, Estado.NO_PUBLICADA);
			
			return new ModelAndView(new RedirectView("administrarInscripciones?message=La inscripcion ha sido despublicada"));
			
		}
	 
	 
	 @RequestMapping(value="/inscripcionValidarStock", produces="application/json")
		public @ResponseBody void inscripcionValidarStock(@RequestParam(required=false) Integer idInscripcion, HttpServletResponse response) throws IOException { 

		 	Boolean stock = true;
		 	
		 	//logica para validar si hay o no stock
		 	Inscripcion inscripcion = inscripcionService.getInscripcionById(idInscripcion);
		 	if(inscripcion.getCantidad() < 1) {
		 		stock = false;
		 	}
		 	
		 
			response.setContentType("application/x-json");
			response.setCharacterEncoding("ISO-8859-1");
			response.getWriter().print(stock);

		}
	 
	 
	 @RequestMapping(value="/carreraMaterias",  produces="application/json",  method = RequestMethod.GET)
		public @ResponseBody void carreraMaterias(@RequestParam Integer idCarrera, HttpServletResponse response, Principal principal) throws IOException {
			Carrera carrera = carreraService.getCarreraById(idCarrera);
			List<Materia> materias = new ArrayList<Materia>(); 
			 
			//obtener las materias de la carrera
			BooleanExpression consulta = QCarreraMateria.carreraMateria.carrera.id.eq(carrera.getId());
			Iterable<CarreraMateria> carreraMaterias = carreraMateriaService.listAllCarreraMaterias(consulta);
			
			for(CarreraMateria carreraMateria: carreraMaterias) {
				if(carreraMateria.getActivo()) {
					materias.add(carreraMateria.getMateria());
				}
			}	
			
			for(Materia materia: materias) {
				System.out.println("materia: "+materia.getNombre());
			}

			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(materias);
			response.setContentType("application/x-json");
			response.setCharacterEncoding("ISO-8859-1");
			response.getWriter().print(jsonData);
			
//			Gson gson = new Gson();
//			String jsonData = gson.toJson(materias);
//			response.setContentType("application/x-json");
//			response.setCharacterEncoding("ISO-8859-1");
//			response.getWriter().print(jsonData);
		}
	
}