package com.portalempleos.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portalempleos.empleos.model.Solicitud;
import com.portalempleos.empleos.model.Usuarios;
import com.portalempleos.empleos.model.Vacante;
import com.portalempleos.empleos.service.SolicitudesService;
import com.portalempleos.empleos.service.UsuariosService;
import com.portalempleos.empleos.service.VacantesService;
import com.portalempleos.empleos.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	
	/**
	 * El valor es el directorio
	 * en donde se guardaran los archivos de los Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
	
	@Autowired
	private VacantesService serviceVacantes;
	
	@Autowired
	private UsuariosService serviceUsuarios;
	
	@Autowired
	private SolicitudesService serviceSolicitudes;
	
	
	

	 /**
		 * Metodo que muestra la lista de solicitudes sin paginacion
		 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
		 * @return
		 */
	    @GetMapping("/index") 
		public String mostrarIndex(Model model) {
            List<Solicitud> lista = serviceSolicitudes.buscarTodas();
	    	model.addAttribute("solicitudes", lista);
			return "solicitudes/listSolicitudes";
			
		}
	    

	    /**
		 * Metodo que muestra la lista de solicitudes con paginacion
		 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
		 * @return
		 */
		@GetMapping("/indexPaginate")
		public String mostrarIndexPaginado(Model model, Pageable page) {
			Page<Solicitud> lista = serviceSolicitudes.buscarTodas(page);
			model.addAttribute("solicitudes", lista);
			return "solicitudes/listSolicitudes";
			
		}
		
	
		/**
		 * Metodo para renderizar el formulario para aplicar para una Vacante
		 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
		 * @return
		 */
		@GetMapping("/create/{idVacante}")
		public String crear(Solicitud solicitud,@PathVariable Integer idVacante, Model model) {
			// Traemos los detalles de la Vacante seleccionada para despues mostrarla en la vista
            Vacante vacante = serviceVacantes.buscarPorId(idVacante);
            model.addAttribute("vacante", vacante);
			return "solicitudes/formSolicitud";
			
		}
		
		/**
		 * Metodo que guarda la solicitud enviada por el usuario en la base de datos
		 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
		 * @return
		 */
		@PostMapping("/save")
		public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
				@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication) {	
			
			String username = authentication.getName();
			
			if (result.hasErrors()){
				System.out.println("Existieron errores");
				return "solicitudes/formSolicitud";
			}
			
			if (!multiPart.isEmpty()) {
				//String ruta = "c:/empleos/files-cv/"; // Windows
				String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
				if (nombreArchivo!=null){ // El archivo (CV) si se subio				
					solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
				}	
			}
			
			// Buscamos el objeto Usuario en BD
			Usuarios usuario = serviceUsuarios.buscarPorUsername(username);
			solicitud.setUsuarios(usuario); // Referenciamos la solicitud con el usuario 
			solicitud.setFecha(new Date());
			
			// Guadamos el objeto solicitud en la bd
			serviceSolicitudes.guardar(solicitud);
			attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");
			
			return "redirect:/";	
			
		}
		
		/**
		 * Metodo para eliminar una solicitud
		 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
		 * @param idSolicitud
	     * @param attributes
		 * @return
		 */
		@GetMapping("/delete/{id}")
		public String eliminar(@PathVariable("id") int idSolicitud,RedirectAttributes attributes) {
			serviceSolicitudes.eliminar(idSolicitud);
			attributes.addFlashAttribute("msg", "La solicitud fue eliminada!");
			return "redirect:/solicitudes/indexPaginate";
			
		}
		
				
		/**
		 * Personalizamos el Data Binding para todas las propiedades de tipo Date
		 * @param webDataBinder
		 */
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}
		
}
