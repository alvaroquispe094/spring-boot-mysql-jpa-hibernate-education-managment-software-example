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

import com.groupal.universia.modelo.Profesor;
import com.groupal.universia.modelo.QProfesor;
import com.groupal.universia.modelo.Role;
import com.groupal.universia.repositorio.RoleRepository;
import com.groupal.universia.servicio.ProfesorService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class ProfesorController {
	
	
	
	 @Autowired
	 private ProfesorService profesorService;
	 
	 @Autowired
	 private RoleRepository roleRepository;

	 @RequestMapping(value="/administrarProfesores", method=RequestMethod.GET)
	    protected ModelAndView administrarProfesores(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String nombre) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QProfesor.profesor.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QProfesor.profesor.nombre.like("%" + nombre + "%"));
			 }	
			 
			 Page<Profesor> page = profesorService.listAllProfesoresPagination(consulta, pageable);

		     modelo.put("profesores", page);
		     modelo.put("nombre", nombre);

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarProfesores");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre);
			 return new ModelAndView("abmProfesor","modelo",modelo);	    
	 }
	 
	 @RequestMapping(value="/nuevoProfesor", method=RequestMethod.GET)
	 public ModelAndView populateNuevoProfesor() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("profesor", new Profesor());
		 modelo.put("url", "guardarProfesor");		 
		 return new ModelAndView("profesorForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarProfesor", method=RequestMethod.GET)
		public ModelAndView editarProfesor(@RequestParam (value="id") Integer idProfesor, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Profesor profesor = profesorService.getProfesorById(idProfesor);
			modelo.put("profesor", profesor);
			modelo.put("url", "guardarProfesor");
			return new ModelAndView("profesorForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarProfesor", method = RequestMethod.POST)
	 protected ModelAndView guardarprofesor(@RequestParam Integer id, @RequestParam String nombre,  
    		@RequestParam (required=false) String apellido, @RequestParam (required=false) Integer documento, @RequestParam (required=false) String username, 
    		@RequestParam (required=false) String password, @RequestParam (required=false) String direccion, @RequestParam (required=false) String fecha, 
    		@RequestParam (required=false) String mail, @RequestParam (required=false) String telefono, @RequestParam (required=false) String titulo,
    		@RequestParam(required=false, defaultValue="true") Boolean activo,Principal principal){
		 
//		 if(id==null) {
//			 password=new BCryptPasswordEncoder().encode(password);
//		 }
		 profesorService.saveProfesor(id,nombre,apellido,documento,username,password,direccion,fecha,mail,telefono, titulo, activo);
		 System.out.println("save profesor");
		
		 if(id==null) {
			 Role role = new Role();
			 role.setUsername(username);
			 role.setRole("ROLE_PROFESOR");
			 roleRepository.save(role);
			 System.out.println("save role");
		 }
		 return new ModelAndView(new RedirectView("administrarProfesores?message=El profesor ha sido guardado"));
    }
	 
	 @RequestMapping(value="/activarProfesor", method=RequestMethod.GET)
		public ModelAndView activarProfesor(@RequestParam (value="id") Integer idProfesor, Principal principal) {
			profesorService.cambiarEstadoProfesor(idProfesor, true);
			return new ModelAndView(new RedirectView("administrarProfesores?message=El profesor ha sido activado"));
		}
	    
    @RequestMapping(value="/desactivarProfesor", method=RequestMethod.GET)
	public ModelAndView desactivarProfesor(@RequestParam (value="id") Integer idProfesor, Principal principal) {
		profesorService.cambiarEstadoProfesor(idProfesor, false);
		return new ModelAndView(new RedirectView("administrarProfesores?message=El profesor ha sido desactivado"));
	}  
    
    @RequestMapping(value="/modificarProfesorDatos", method=RequestMethod.GET)
	public ModelAndView modificarProfesorDatos( Principal principal) {
	 	Map<String, Object> modelo = new HashMap<String, Object>();

	 	Profesor profesor = profesorService.obtenerUsuario(principal);
	 	
//	 	Profesor profesor = profesorService.getProfesorById(idProfesor);
		modelo.put("profesor", profesor);
		modelo.put("url", "guardarProfesorMisDatos");
		return new ModelAndView("profesorForm","modelo",modelo);
	}
	
    
    @RequestMapping(value = "/guardarProfesorMisDatos", method = RequestMethod.POST)
	 protected ModelAndView guardarprofesorMisDatos(@RequestParam Integer id, @RequestParam String nombre,  
   		@RequestParam (required=false) String apellido, @RequestParam (required=false) Integer documento, @RequestParam (required=false) String username, 
   		@RequestParam (required=false) String password, @RequestParam (required=false) String direccion, @RequestParam (required=false) String fecha, 
   		@RequestParam (required=false) String mail, @RequestParam (required=false) String telefono, @RequestParam (required=false) String titulo,
   		@RequestParam(required=false, defaultValue="true") Boolean activo,Principal principal){

		 profesorService.saveProfesor(id,nombre,apellido,documento,username,password,direccion,fecha,mail,telefono, titulo, activo);

		 return new ModelAndView(new RedirectView("modificarProfesorDatos?message=El profesor ha sido guardado"));
   }
	
}