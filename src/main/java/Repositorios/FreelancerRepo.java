package Repositorios;

import entities.Freelancer;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;

public interface FreelancerRepo extends CrudRepository<Freelancer, UUID> {
    boolean existsByNombre(String nombre);
    Optional<Freelancer> findByNombre(String nombre);
}