package services;

import Repositorios.FreelancerRepo;
import Repositorios.RolRepo;
import entities.Freelancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelanceServiceImpl implements FreelancerService{
    @Autowired
    private FreelancerRepo freelancerRepo;

    @Autowired
    private RolRepo rolRepo;

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