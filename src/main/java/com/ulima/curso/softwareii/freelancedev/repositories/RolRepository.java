package com.ulima.curso.softwareii.freelancedev.repositories;

import com.ulima.curso.softwareii.freelancedev.entities.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RolRepository extends CrudRepository<Rol, UUID> {
    Optional<Rol> findByNombre(String nombre);
}