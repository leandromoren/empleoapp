package com.portalempleos.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portalempleos.empleos.model.Usuarios;
import com.portalempleos.empleos.service.UsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosService serviceUsuarios;
	
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Usuarios> lista = serviceUsuarios.buscarTodos();
		model.addAttribute("usuarios",lista);
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
		serviceUsuarios.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "Usuario eliminado correctamente");
		return "redirect:/usuarios/index";
	}
	
}
