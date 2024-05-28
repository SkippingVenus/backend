package com.ulima.curso.softwareii.freelancedev.repositories;


import com.ulima.curso.softwareii.freelancedev.entities.Trabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "trabajo")
public interface TrabajoRepository extends CrudRepository<Trabajo, UUID> {
}