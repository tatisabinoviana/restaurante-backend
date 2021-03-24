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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.restaurante.model.Mesa;
import br.com.restaurante.repository.Mesas;
//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/mesas")
public class MesaResource {
	
	@Autowired
	private Mesas mesasRepository;

//	@ApiOperation("Cadastra uma mesa por vez.")
	@PostMapping
	public ResponseEntity<Mesa> save(@Valid @RequestBody Mesa mesa) {
		mesasRepository.save(mesa);
		return new ResponseEntity<>(mesa, HttpStatus.OK);
	}
	
//	@ApiOperation("Consulta todas as mesas, retornando uma lista.")
	@GetMapping
	public ResponseEntity<List<Mesa>> getAll() {
		List<Mesa> mesas = mesasRepository.findAll();
		return new ResponseEntity<>(mesas, HttpStatus.OK);
	}
	
//    @ApiOperation("Consulta uma mesa pelo id.")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Mesa>> getById(@PathVariable Long id) {
    	Optional<Mesa> mesa;
    	try {
    		mesa = mesasRepository.findById(id);
    		return new ResponseEntity<>(mesa, HttpStatus.OK);
    	} catch (NoSuchElementException e) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
//    @ApiOperation("Atualiza uma mesa pelo id.")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Mesa> update(@PathVariable Long id, @RequestBody Mesa mesaAtualizada) {
        return mesasRepository.findById(id)
            .map(mesa -> {
            mesa.setNomeMesa(mesaAtualizada.getNomeMesa());
            Mesa mesaAtual = mesasRepository.save(mesa);
            return ResponseEntity.ok().body(mesaAtual);
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
