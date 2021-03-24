package br.com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurante.model.Mesa;

public interface Mesas extends JpaRepository<Mesa, Long>{

}
