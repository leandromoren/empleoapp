package com.portalempleos.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portalempleos.empleos.model.Vacante;
import com.portalempleos.empleos.service.CategoriasService;
import com.portalempleos.empleos.service.VacantesService;
import com.portalempleos.empleos.util.Utileria;


@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Autowired
	private VacantesService serviceVacantes;
	
	@Autowired
	private CategoriasService categoriasService;
	
	//Inyecto el valor de la propiedad
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		//Regresa la lista de vacantes
		List<Vacante> lista = serviceVacantes.buscarTodas();
		
		//Agrega el modelo al listado de vacantes
		model.addAttribute("vacante", lista);
		return "vacantes/listVacante";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacante", lista);
		return "vacantes/listVacante";
	}

	
	
	//Metodo para crear vacantes de trabajo
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
	    return "vacantes/formVacante";	
	}
	

	
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, 
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		if(result.hasErrors()) {
			
			//Desplegar los errores en la consola
			for(ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			return "vacantes/formVacante";
		}
		
		if(!multiPart.isEmpty()) {
			//String ruta = "c:/empleosapp/img-vacantes/";
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			//Si la imagen se subio
			if(nombreImagen != null) {
				vacante.setImagen(nombreImagen);
			}
		}
		//cuando llegue el objeto vacante al controlador, se agrega automaticamente a la lista
		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro guardado correctamente");
	
		return "redirect:/vacantes/indexPaginate";
	}
	

	
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg","La vacante fue eliminada!");
		return "redirect:/vacantes/indexPaginate";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante",vacante);
		
		return "vacantes/formVacante";
		
	}
	
	
	//Las url con llaves significa que es dinamica o sea que puede cambiar
	@GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
    	model.addAttribute("vacante", vacante); 
		return "detalle";
    }
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", categoriasService.buscarTodas());
	}
	
	
	
	//Establece el dataBinding para la fecha ya que al no usar
	//los request param en el metodo guardar, tiraba error porque 
	//no sabia de que tipo era el objecto fecha, si era string o date.
	@InitBinder
	public void initBinder(WebDataBinder wdb) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		wdb.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, false));
	}
}
