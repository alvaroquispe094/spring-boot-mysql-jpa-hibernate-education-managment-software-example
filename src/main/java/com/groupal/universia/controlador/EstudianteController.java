package com.groupal.universia.controlador;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
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

import com.groupal.universia.modelo.Carrera;
import com.groupal.universia.modelo.Estudiante;
import com.groupal.universia.modelo.Profesor;
import com.groupal.universia.modelo.QEstudiante;
import com.groupal.universia.modelo.QProfesor;
import com.groupal.universia.modelo.QUsuario;
import com.groupal.universia.modelo.Role;
import com.groupal.universia.modelo.Usuario;
import com.groupal.universia.repositorio.RoleRepository;
import com.groupal.universia.servicio.CarreraService;
import com.groupal.universia.servicio.EstudianteService;
import com.groupal.universia.servicio.ProfesorService;
import com.groupal.universia.servicio.UsuarioService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class EstudianteController {
	
	 @Autowired
	 private EstudianteService estudianteService;
	 
	 @Autowired
	 private ProfesorService profesorService;
	 
	 @Autowired
	 private UsuarioService usuarioService;
	 
	 @Autowired
	 private CarreraService carreraService;
	 
	 @Autowired
	 private RoleRepository roleRepository;
	 
	

	 @RequestMapping(value="/administrarEstudiantes", method=RequestMethod.GET)
	    protected ModelAndView administrarEstudiantes(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (value="carrera",required=false) Integer idCarrera) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QEstudiante.estudiante.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QEstudiante.estudiante.nombre.like("%" + nombre + "%"));
			 }
			 
			 if(idCarrera != null) {
					consulta = consulta.and(QEstudiante.estudiante.carrera.id.eq(idCarrera));
				 }
			 
			 Page<Estudiante> page = estudianteService.listAllEstudiantesPagination(consulta, pageable);
			 modelo.put("estudiantes", page);
			 
			 modelo.put("carreras", carreraService.listAllCarreras());
			 
		     modelo.put("nombre", nombre);
		     modelo.put("carrera", idCarrera);
		     
		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarEstudiantes");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre);
			 return new ModelAndView("abmEstudiante","modelo",modelo);
	    }
		
	 
	 @RequestMapping(value="/nuevoEstudiante", method=RequestMethod.GET)
	 public ModelAndView populateNuevoEstudiante() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("estudiante", new Estudiante());
		 modelo.put("carrera", new Carrera());
		 
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("carreras", carreraService.listAllCarreras(sort));
		 modelo.put("url", "guardarEstudiante");		 
		 return new ModelAndView("estudianteForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarEstudiante", method=RequestMethod.GET)
		public ModelAndView editarEstudiante(@RequestParam (value="id") Integer idEstudiante, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Estudiante estudiante = estudianteService.getEstudianteById(idEstudiante);
			
			//carrera Seleccionada
			modelo.put("carrera", carreraService.getCarreraById(estudiante.getCarrera().getId()));
			
			modelo.put("carreras", carreraService.listAllCarreras());
			modelo.put("estudiante", estudiante);
			modelo.put("url", "guardarEstudiante");
			return new ModelAndView("estudianteForm","modelo",modelo);
		}
	 
	 @RequestMapping(value = "/guardarEstudiante", method = RequestMethod.POST)
	 protected ModelAndView guardarEstudiante(@RequestParam Integer id, @RequestParam String nombre,  
    		@RequestParam String apellido, @RequestParam Integer documento, @RequestParam String username, @RequestParam String password,  
    		@RequestParam String direccion, @RequestParam String fecha, @RequestParam  String mail,
    		@RequestParam String telefono, @RequestParam(value="carrera") Integer idCarrera,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
        
//		 String encoded=new BCryptPasswordEncoder().encode(password);
		 estudianteService.saveEstudiante(id,nombre,apellido,documento,username,password,direccion,fecha,mail,telefono,idCarrera,activo);
		 System.out.println("save estudiante");
		 if(id==null) {
			 Role role = new Role();
			 role.setUsername(username);
			 role.setRole("ROLE_ESTUDIANTE");
			 roleRepository.save(role);
			 System.out.println("save role");
		 }
	
		 return new ModelAndView(new RedirectView("administrarEstudiantes?message=El estudiante ha sido guardado"));
    }
	
	 
	@RequestMapping(value="/activarEstudiante", method=RequestMethod.GET)
		public ModelAndView activarEstudiante(@RequestParam (value="id") Integer idEstudiante, Principal principal) {
			estudianteService.cambiarEstadoEstudiante(idEstudiante, true);
			return new ModelAndView(new RedirectView("administrarEstudiantes?message=El estudiante ha sido activado"));
	}
	    
	 @RequestMapping(value="/desactivarEstudiante", method=RequestMethod.GET)
		public ModelAndView desactivarrEstudiante(@RequestParam (value="id") Integer idEstudiante, Principal principal) {
			estudianteService.cambiarEstadoEstudiante(idEstudiante, false);
			return new ModelAndView(new RedirectView("administrarEstudiantes?message=El estudiante ha sido desactivado"));
	}  
	
	 
	 @RequestMapping(value = "exist", method = RequestMethod.POST)
	 @ResponseBody
	 public boolean isBrandNameExists(HttpServletResponse response,
	         @RequestParam String username, @RequestParam(required=false) Integer id) throws IOException {
		 		
		 BooleanExpression consulta = QEstudiante.estudiante.username.eq(username);
		 Estudiante estudiante = estudianteService.getEstudianteById(consulta);
		 
		 BooleanExpression consulta2 = QProfesor.profesor.username.eq(username); 
		 Profesor profesor = profesorService.getProfesorById(consulta2);
		 
		 BooleanExpression consulta3 = QUsuario.usuario.username.eq(username);		 
		 Usuario usuario = usuarioService.getUsuarioById(consulta3);
		 
		 Boolean validar;
		 
		 if(estudiante == null && profesor==null && usuario == null) {
			 validar =true;
		 }else {
			 if(id==null) {
				 validar=false;
			 }else {
				 validar=true;
			 }
		 }
		 
	     return validar;
	 }
 
	 @RequestMapping(value="/modificarEstudianteDatos", method=RequestMethod.GET)
		public ModelAndView modificarEstudianteDatos( Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

		 	 Estudiante estudiante = estudianteService.obtenerUsuario(principal);
		 	
//			Estudiante estudiante = estudianteService.getEstudianteById(idEstudiante);
			
			//carrera Seleccionada
			modelo.put("carrera", carreraService.getCarreraById(estudiante.getCarrera().getId()));
			
			modelo.put("carreras", carreraService.listAllCarreras());
			modelo.put("estudiante", estudiante);
			modelo.put("url", "guardarEstudianteMisDatos");

			return new ModelAndView("estudianteForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarEstudianteMisDatos", method = RequestMethod.POST)
	 protected ModelAndView guardarEstudianteMisDatos(@RequestParam Integer id, @RequestParam String nombre,  
    		@RequestParam String apellido, @RequestParam Integer documento, @RequestParam String username, @RequestParam String password,  
    		@RequestParam String direccion, @RequestParam String fecha, @RequestParam  String mail,
    		@RequestParam String telefono, @RequestParam(value="carrera") Integer idCarrera,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
        

		 estudianteService.saveEstudiante(id,nombre,apellido,documento,username,password,direccion,fecha,mail,telefono,idCarrera,activo);				
	
		 return new ModelAndView(new RedirectView("modificarEstudianteDatos?message=El estudiante ha sido guardado"));
    }
	 
}