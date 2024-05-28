package com.ulima.curso.softwareii.freelancedev.services;


import com.ulima.curso.softwareii.freelancedev.entities.Freelancer;
import com.ulima.curso.softwareii.freelancedev.entities.Usuario;

import java.util.List;

public interface FreelancerService {
    List<Freelancer> findAll();
    Freelancer save(Freelancer freelancer);
    boolean existByNombre(String nombre);
}