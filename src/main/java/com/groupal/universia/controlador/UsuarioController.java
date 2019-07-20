package com.groupal.universia.controlador;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.universia.modelo.QUsuario;
import com.groupal.universia.modelo.Role;
import com.groupal.universia.modelo.Usuario;
import com.groupal.universia.repositorio.RoleRepository;
import com.groupal.universia.servicio.UsuarioService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class UsuarioController {
	
	
	
	 @Autowired
	 private UsuarioService usuarioService;
	 
	 @Autowired
	 private RoleRepository roleRepository;

	 @RequestMapping(value="/administrarUsuarios", method=RequestMethod.GET)
	    protected ModelAndView administrarUsuarioes(Principal principal, @PageableDefault(size = 30) Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (defaultValue="",required=false) String mail) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QUsuario.usuario.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QUsuario.usuario.username.like("%" + nombre + "%"));
			 }	
			 
			 if(mail != null && !mail.equals("")) {
					consulta = consulta.and(QUsuario.usuario.mail.like("%" + mail + "%"));
				 }	
			 
			 Page<Usuario> page = usuarioService.listAllUsuariosPagination(consulta, pageable);

		     modelo.put("usuarios", page);
		     modelo.put("nombre", nombre);
		     modelo.put("mail", mail);

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarUsuarios");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre+"&mail="+mail);	
			 return new ModelAndView("abmUsuario","modelo",modelo);	    
	 }
	 
	 @RequestMapping(value="/nuevoUsuario", method=RequestMethod.GET)
	 public ModelAndView populateNuevoUsuario() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("usuario", new Usuario());
		 		 
		 return new ModelAndView("usuarioForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/editarUsuario", method=RequestMethod.GET)
		public ModelAndView editarUsuario(@RequestParam (value="id") Integer idUsuario, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Usuario usuario = usuarioService.getUsuarioById(idUsuario);
			modelo.put("usuario", usuario);

			return new ModelAndView("usuarioForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST)
	 protected ModelAndView guardarusuario(@RequestParam Integer id, @RequestParam String username, 
			@RequestParam String password, @RequestParam (required=false) String mail,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
//		 if(id==null) {
//			 password=new BCryptPasswordEncoder().encode(password);
//		 }
		 usuarioService.saveUsuario(id, username, password, mail, activo);
		 System.out.println("save usuario");
		
		 if(id==null) {
			 Role role = new Role();
			 role.setUsername(username);
			 role.setRole("ROLE_ADMIN");
			 roleRepository.save(role);
			 System.out.println("save role");
		 }
		 return new ModelAndView(new RedirectView("administrarUsuarios?message=El usuario administrador ha sido dado de alta"));
    }
	 
	 @RequestMapping(value="/activarUsuario", method=RequestMethod.GET)
		public ModelAndView activarUsuario(@RequestParam (value="id") Integer idUsuario, Principal principal) {
			usuarioService.cambiarEstadoUsuario(idUsuario, true);
			return new ModelAndView(new RedirectView("administrarUsuarios?message=El usuario ha sido activado"));
		}
	    
    @RequestMapping(value="/desactivarUsuario", method=RequestMethod.GET)
	public ModelAndView desactivarUsuario(@RequestParam (value="id") Integer idUsuario, Principal principal) {
		usuarioService.cambiarEstadoUsuario(idUsuario, false);
		return new ModelAndView(new RedirectView("administrarUsuarios?message=El usuario ha sido desactivado"));
	}  
	
    @RequestMapping(value = "existAdmin", method = RequestMethod.POST)
	 @ResponseBody
	 public boolean isBrandNameExists(HttpServletResponse response,
	         @RequestParam String username) throws IOException {
		 		
		 BooleanExpression consulta = QUsuario.usuario.username.eq(username);
		 
		 Usuario usuario = usuarioService.getUsuarioById(consulta);
		 Boolean validar;
		 
		 if(usuario == null) {
			 validar =true;
		 }else {
			 validar=false;
		 }
		 
	     return validar;
	 }
	
}