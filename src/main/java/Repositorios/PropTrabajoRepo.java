package Repositorios;


import java.util.UUID;

@RepositoryRestResource(path = "proptrabajo")
public interface PropTrabajoRepo extends CrudRepository<PropTrabajo, UUID> {
}