package com.ulima.curso.softwareii.freelancedev.repositories;

import com.ulima.curso.softwareii.freelancedev.entities.Freelancer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface FreelancerRepository extends CrudRepository<Freelancer, UUID> {
    boolean existsByNombre(String nombre);
    Optional<Freelancer> findByNombre(String nombre);
}