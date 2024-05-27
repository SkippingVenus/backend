package Repositorios;


import entities.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepo extends CrudRepository<Cliente, UUID> {
    boolean existsByNombre(String nombre);
    Optional<Cliente> findByNombre(String nombre);
}