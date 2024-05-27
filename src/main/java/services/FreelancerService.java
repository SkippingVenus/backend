package services;


import entities.Freelancer;
import entities.Usuario;
import java.util.List;

public interface FreelancerService {
    List<Freelancer> findAll();
    Freelancer save(Freelancer freelancer);
    boolean existByNombre(String nombre);
}