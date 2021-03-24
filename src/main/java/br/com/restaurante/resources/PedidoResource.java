package br.com.restaurante.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.restaurante.model.Pedido;
import br.com.restaurante.repository.Pedidos;
//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/pedidos")
public class PedidoResource {
	
	@Autowired
	private Pedidos pedidosRepository;

//	@ApiOperation("Inclui um pedido por vez.")
	@PostMapping
	public ResponseEntity<Pedido> save(@Valid @RequestBody Pedido pedido) {
		pedidosRepository.save(pedido);
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}
	
//	@ApiOperation("Consulta todos os pedidos, retornando uma lista.")
	@GetMapping
	public ResponseEntity<List<Pedido>> getAll() {
		List<Pedido> pedidos = pedidosRepository.findAll();
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}
	
//    @ApiOperation("Consulta um pedido pelo id.")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Pedido>> getById(@PathVariable Long id) {
    	Optional<Pedido> pedido;
    	try {
    		pedido = pedidosRepository.findById(id);
    		return new ResponseEntity<>(pedido, HttpStatus.OK);
    	} catch (NoSuchElementException e) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
	
//  @ApiOperation("Consulta um pedido pela mesa.")
//	@GetMapping(path = "/{mesa.id}")
//	public ResponseEntity<Optional<Pedido>> getById(@PathVariable Long id) {
//	  	Optional<Pedido> pedido;
//	  	try {
//	  		pedido = pedidosRepository.findById(id);
//	  		return new ResponseEntity<>(pedido, HttpStatus.OK);
//	  	} catch (NoSuchElementException e) {
//	  		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	  	}
//	  }
//	
////    @ApiOperation("Exclui um pedido pelo id.")
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<Optional<Pedido>> deleteById(@PathVariable Long id) {
//        try {
//            pedidosRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
	
//    @ApiOperation("Atualiza um pedido pelo id.")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        return pedidosRepository.findById(id)
            .map(pedido -> {
            pedido.setItem(pedidoAtualizado.getItem());
            pedido.setValor(pedidoAtualizado.getValor());
            pedido.setSituacao(pedidoAtualizado.getSituacao());
            Pedido pedidoAtual = pedidosRepository.save(pedido);
            return ResponseEntity.ok().body(pedidoAtual);
        }).orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
