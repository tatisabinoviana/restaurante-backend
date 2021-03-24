package br.com.restaurante.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurante.model.Mesa;
import br.com.restaurante.repository.Mesas;

@Controller
@RequestMapping("/mesas")
public class MesaController {
	
//	@RequestMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@Autowired
	private Mesas mesas;
	
	@PostMapping
	public String salvar(Mesa mesa) {
		this.mesas.save(mesa);
		return "redirect:/mesas";
	}
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView retorno = new ModelAndView("ListarMesas");
		retorno.addObject("mesas", mesas.findAll());
		retorno.addObject(new Mesa());
		return retorno;
	}
}
