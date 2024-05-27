package Repositorios;

import entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepo extends CrudRepository<Usuario, UUID> {
    boolean existsByNombre(String nombre);
    Optional<Usuario> findByNombre(String nombre);
}