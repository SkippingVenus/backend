package com.ulima.curso.softwareii.freelancedev.services;

import com.ulima.curso.softwareii.freelancedev.entities.Cliente;
import com.ulima.curso.softwareii.freelancedev.entities.Usuario;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    boolean existByNombre(String nombre);
}