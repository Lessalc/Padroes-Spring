package com.luciano.padroesprojetospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luciano.padroesprojetospring.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
