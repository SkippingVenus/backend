package com.ulima.curso.softwareii.freelancedev.repositories;


import com.ulima.curso.softwareii.freelancedev.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends CrudRepository<Cliente, UUID> {
    boolean existsByNombre(String nombre);
    Optional<Cliente> findByNombre(String nombre);
}