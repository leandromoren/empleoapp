package com.portalempleos.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portalempleos.empleos.model.Perfiles;
import com.portalempleos.empleos.model.Usuarios;
import com.portalempleos.empleos.model.Vacante;
import com.portalempleos.empleos.service.CategoriasService;
import com.portalempleos.empleos.service.UsuariosService;
import com.portalempleos.empleos.service.VacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoriasService serviceCategorias;
	
	@Autowired
	private VacantesService serviceVacantes;
	
	@Autowired
	private UsuariosService serviceUsuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String mostrarLogout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return "[" + texto + "] Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println(username);
		
		for(GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("Rol del usuario: " + rol.getAuthority());
		}
		
		if(session.getAttribute("usuario") == null) {
			Usuarios usuario = serviceUsuarios.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);//Almacena datos en la sesion del usuario	
		}
		
		return "redirect:/";
	}
	
	/**
	 *InitBinder para Strings si los detecta vacíos en el data binding los setea a NULL 
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando: " + vacante);
		
		
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante,matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes",lista);
		return "home";
	}
	
	@GetMapping("/table")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas(); //Regresa la lista de vacantes
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuarios usuario) {
		return "formRegistro";
	}
	
	
	@PostMapping("/signup")
	public String guardaRegistro(Usuarios usuarios, RedirectAttributes attributes) {
		
		String pwdPlano = usuarios.getPassword();
		String pwdEncriptar = passwordEncoder.encode(pwdPlano);
		
		usuarios.setPassword(pwdEncriptar); //Se guarda la contraseña encriptada
		usuarios.setEstatus(1); //Activado por defecto
		usuarios.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfiles perfil = new Perfiles();
		perfil.setId(3);
		usuarios.agregar(perfil);
		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		serviceUsuarios.guardar(usuarios);
		attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
		return "redirect:/usuarios/index";
	}
	
	
	/*@GetMapping("/details")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de comunicaciones");
		vacante.setDescripcion("Se solicita ing. para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(85000.0);
		
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	
	@GetMapping("/list")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de sistemas");
		lista.add("Auxiliar de contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}*/
	
	@GetMapping("/")
	public String mostrarHome(Model model) /* <model> es el parametro mas comun para usar */ {
		return "home";
	}
	
	@ModelAttribute //Puedo agregar al modelo todos los atributos que quiera y todos van a estar disponibles para todos los metodos declarados en este controlador
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		model.addAttribute("search", vacanteSearch);
	}
	
	
}
