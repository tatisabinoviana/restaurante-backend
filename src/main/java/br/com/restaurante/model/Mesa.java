package br.com.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name ="increment", strategy = "incremet")
	private Long id;
	
	@NotBlank(message = "Nome de identificação do Cliente é obrigatório!")
	private String nomeMesa;
	
}
