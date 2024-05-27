package services;

import entities.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    boolean existByNombre(String nombre);
}