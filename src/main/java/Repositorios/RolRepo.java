package Repositorios;

import java.util.Optional;
import java.util.UUID;

public interface RolRepo extends CrudRepository<Rol, UUID> {
    Optional<Rol> findByNombre(String nombre);
}