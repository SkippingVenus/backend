package Repositorios;


import entities.Trabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "trabajo")
public interface TrabajoRepo extends CrudRepository<Trabajo, UUID> {
}