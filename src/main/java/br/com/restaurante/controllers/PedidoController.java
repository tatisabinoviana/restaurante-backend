package br.com.restaurante.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurante.model.Pedido;
import br.com.restaurante.repository.Pedidos;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private Pedidos pedidos;
	
	@PostMapping
	public String salvar(Pedido pedido) {
		this.pedidos.save(pedido);
		return "redirect:/pedidos";
	}
	
	@GetMapping
	public ModelAndView listarPedidos() {
		ModelAndView retorno = new ModelAndView("ListarPedidos");
		retorno.addObject("pedidos", pedidos.findAll());
		retorno.addObject(new Pedido());
		return retorno;
	}
}
