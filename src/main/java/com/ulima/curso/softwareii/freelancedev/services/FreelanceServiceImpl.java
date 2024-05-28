package com.ulima.curso.softwareii.freelancedev.services;

import com.ulima.curso.softwareii.freelancedev.entities.Freelancer;
import com.ulima.curso.softwareii.freelancedev.repositories.FreelancerRepository;
import com.ulima.curso.softwareii.freelancedev.repositories.RolRepository;
import com.ulima.curso.softwareii.freelancedev.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelanceServiceImpl implements FreelancerService {
    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Freelancer> findAll() {
        return null;
    }

    @Override
    public Freelancer save(Freelancer freelancer) {
        return null;
    }

    @Override
    public boolean existByNombre(String nombre) {
        return false;
    }
}