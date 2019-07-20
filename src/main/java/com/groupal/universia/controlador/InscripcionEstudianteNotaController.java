package com.groupal.universia.controlador;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.groupal.universia.modelo.InscripcionEstudiante;
import com.groupal.universia.modelo.InscripcionEstudianteNota;
import com.groupal.universia.modelo.QEstado;
import com.groupal.universia.modelo.QInscripcionEstudiante;
import com.groupal.universia.modelo.QInscripcionEstudianteNota;
import com.groupal.universia.repositorio.EstadoRepository;
import com.groupal.universia.servicio.CarreraService;
import com.groupal.universia.servicio.EstudianteService;
import com.groupal.universia.servicio.InscripcionEstudianteNotaService;
import com.groupal.universia.servicio.InscripcionEstudianteService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class InscripcionEstudianteNotaController {
	
	
	@Autowired
	private InscripcionEstudianteService inscripcionEstudianteService;
	
	@Autowired
	private InscripcionEstudianteNotaService inscripcionEstudianteNotaService;
	
	@Autowired
	private CarreraService carreraService;
		
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstudianteService estudianteService;
	 
	
	 @RequestMapping(value="/administrarInscripcionEstudianteNotas", method=RequestMethod.GET)
	 protected ModelAndView administrarInscripcionEstudianteNotas(Principal principal, 
			 @PageableDefault Pageable pageable, @RequestParam (defaultValue="",value="carrera", required=false) Integer idCarrera,
			 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (defaultValue="",required=false) String apellido, 
			 @RequestParam (defaultValue="",required=false) String materia) { 	
		 Sort sort = new Sort(Sort.Direction.ASC, "inscripcion.carrera.nombre").and(new Sort(Sort.Direction.ASC, "inscripcion.materia.nombre"));
		 
		 Pageable pageRequest = new PageRequest(pageable.getPageNumber(), 30, sort);
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QInscripcionEstudiante.inscripcionEstudiante.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.nombre.like("%" + nombre + "%"));
		 }
		 if(apellido != null && !apellido.equals("")) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.apellido.like("%" + apellido + "%"));
		 }
		 if(idCarrera != null && !idCarrera.equals("") ) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.inscripcion.carrera.id.eq(idCarrera));
		 }
		 if(materia != null && !materia.equals("")) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.inscripcion.materia.nombre.eq(materia));
		 }
		 

		 Page<InscripcionEstudiante> page = inscripcionEstudianteService.listAllInscripcionEstudiantesPagination(consulta, pageRequest);
		 
	      modelo.put("inscripcionEstudiantes", page);
	      
	      modelo.put("carreras", carreraService.listAllCarreras());
	      modelo.put("carrera", idCarrera);
	      modelo.put("nombre", nombre);
	      modelo.put("apellido",apellido);
	      modelo.put("materia", materia);
//	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));
	      
	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarInscripcionEstudianteNotas");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre+"&apellido="+apellido+"&carrera="+(idCarrera != null ? idCarrera : "")+"&materia="+materia);
		  return new ModelAndView("abmInscripcionEstudianteNota","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/nuevaInscripcionEstudianteNota", method=RequestMethod.GET)
		public ModelAndView populateNuevaInscripcionEstudiante(@RequestParam(value = "idInscripcionEstudiante") Integer idInscripcionEstudiante) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 modelo.put("inscripcionEstudianteNota", new InscripcionEstudianteNota());
		 modelo.put("estado", new Estado());
//		 modelo.put("estudiante", new Estudiante());
		 
		 modelo.put("inscripcionEstudiante", inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante));
		 
		//estados
		 BooleanExpression consulta = QEstado.estado.id.ne(Estado.PUBLICADA);
		 consulta = consulta.and(QEstado.estado.id.ne(Estado.NO_PUBLICADA));
		 consulta = consulta.and(QEstado.estado.id.ne(Estado.CURSANDO));
		 Iterable<Estado> estados = estadoRepository.findAll(consulta);
		 modelo.put("estados", estados);
//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 modelo.put("estudiantes", estudianteService.listAllStudents(sort));
		 		 
		 return new ModelAndView("inscripcionEstudianteNotaForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarInscripcionEstudianteNota", method=RequestMethod.GET)
		public ModelAndView editarInscripcionEstudiante(@RequestParam (value="id") Integer idInscripcionEstudiante, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			InscripcionEstudiante inscripcionEstudiante = inscripcionEstudianteService.getInscripcionEstudianteById(idInscripcionEstudiante);
			modelo.put("inscripcionEstudiante", inscripcionEstudiante);
			
			BooleanExpression consulta2 = QInscripcionEstudianteNota.inscripcionEstudianteNota.id.ne(0);
			consulta2 = consulta2.and(QInscripcionEstudianteNota.inscripcionEstudianteNota.inscripcionEstudiante.id.eq(idInscripcionEstudiante));
			InscripcionEstudianteNota inscripcionEstudianteNota = inscripcionEstudianteNotaService.getInscripcionEstudianteNotaById(consulta2);
			modelo.put("inscripcionEstudianteNota", inscripcionEstudianteNota);
			
			//estado selccionado
			modelo.put("estado", inscripcionEstudianteNota.getInscripcionEstudiante().getEstado());
			
			//estados
			BooleanExpression consulta = QEstado.estado.id.ne(Estado.PUBLICADA);
			consulta = consulta.and(QEstado.estado.id.ne(Estado.NO_PUBLICADA));
			consulta = consulta.and(QEstado.estado.id.ne(Estado.CURSANDO));
			Iterable<Estado> estados = estadoRepository.findAll(consulta);
			modelo.put("estados", estados);

			//			modelo.put("inscripcion", inscripcionService.getInscripcionById(inscripcionEstudiante.getInscripcion().getId()));
//			modelo.put("estudiantes", estudianteService.listAllStudents());

			return new ModelAndView("inscripcionEstudianteNotaForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarInscripcionEstudianteNota", method = RequestMethod.POST)
	 protected ModelAndView guardarInscripcionEstudiante(@RequestParam(required=false) Integer id, @RequestParam Integer idInscripcionEstudiante,
			 @RequestParam Integer nota1, @RequestParam Integer nota2, @RequestParam Integer notaFinal, @RequestParam(value="estado") Integer idEstado,
			 @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){

		 //guarda la nota del estudiante
		 inscripcionEstudianteNotaService.saveInscripcionEstudianteNota(id, idInscripcionEstudiante, nota1, nota2, notaFinal, idEstado, activo);
		
		 //cambia el estado de la cursa del estudiante
		 inscripcionEstudianteService.cambiarEstadoCursada(idInscripcionEstudiante, idEstado);
		 
		 return new ModelAndView(new RedirectView("administrarInscripcionEstudianteNotas?message=La nota del estudiante se ha guardado"));
    }	   
	 
	 
	 @RequestMapping(value="/inscripcionEstudianteVerNota", method=RequestMethod.GET)
		public ModelAndView inscripcionEstudianteVerNota(@RequestParam (value="id") Integer idInscripcionEstudiante, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			
			BooleanExpression consulta2 = QInscripcionEstudianteNota.inscripcionEstudianteNota.id.ne(0);
			consulta2 = consulta2.and(QInscripcionEstudianteNota.inscripcionEstudianteNota.inscripcionEstudiante.id.eq(idInscripcionEstudiante));
			InscripcionEstudianteNota inscripcionEstudianteNota = inscripcionEstudianteNotaService.getInscripcionEstudianteNotaById(consulta2);
			modelo.put("inscripcionEstudianteNota", inscripcionEstudianteNota);
			System.out.println("inscripcionEstudiante: "+inscripcionEstudianteNota.getInscripcionEstudiante().getEstudiante().getNombre());

			return new ModelAndView("abmInscripcionEstudianteVerNota","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrarMisNotas", method=RequestMethod.GET)
	 protected ModelAndView administrarMisNotas(Principal principal, 
			 @PageableDefault(size = 10, sort="inscripcion.carrera.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String materia) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
		 
		 BooleanExpression consulta = QInscripcionEstudiante.inscripcionEstudiante.id.ne(0);
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.id.eq(estudiante.getId()));
		 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estado.id.ne(Estado.CURSANDO));
		 
		 if(materia != null && !materia.equals("")) {
			 consulta = consulta.and(QInscripcionEstudiante.inscripcionEstudiante.estudiante.nombre.like("%" + materia + "%"));
		 }	

		 Page<InscripcionEstudiante> page = inscripcionEstudianteService.listAllInscripcionEstudiantesPagination(consulta, pageable);
		 
	      modelo.put("inscripcionEstudiantes", page);
	      modelo.put("materia", materia);	
//	      modelo.put("inscripcion", inscripcionService.getInscripcionById(idInscripcion));

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "administrarMisNotas");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&materia="+materia);	
		  return new ModelAndView("abmInscripcionEstudianteNota","modelo",modelo);
	 }
	    	
	
}