package com.portalempleos.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portalempleos.empleos.model.Categoria;
import com.portalempleos.empleos.service.CategoriasService;

@Controller
@RequestMapping(value="/categorias") /* Cuando quiera realizar una peticion get a la url index, 
voy a tener que utilizar la peticion /categorias/index */
public class CategoriasController {
	

	@Autowired
   	private CategoriasService serviceCategorias;
	
	
	
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
    	model.addAttribute("categorias", lista);
		return "categorias/listCategorias";		
	}
	
	
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategorias";
	}
	
	//Funciona
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		
		try {
			serviceCategorias.eliminar(idCategoria);
			attributes.addFlashAttribute("msg", "¡Categoria eliminada correctamente!");
		}catch(Exception ex){
			attributes.addFlashAttribute("msg", "No es posible eliminar la categoría seleccionada :(");
			System.out.println(ex);
		}
		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria",categoria);
		return "categorias/formCategorias";
	}
	
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategorias";
		}	
		
		// Guadamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "¡Los datos de la categoria fueron guardados con exito!");		
		return "redirect:/categorias/index";
	}
	
	
}
