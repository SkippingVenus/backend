package com.ulima.curso.softwareii.freelancedev.repositories;


import com.ulima.curso.softwareii.freelancedev.entities.PropTrabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "proptrabajo")
public interface PropTrabajoRepository extends CrudRepository<PropTrabajo, UUID> {
}