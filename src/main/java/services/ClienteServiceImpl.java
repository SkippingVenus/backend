package service;

import Repositorios.ClienteRepo;
import Repositorios.RolRepo;
import entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.ClienteService;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private RolRepo rolRepo;

    @Override
    public List<Cliente> findAll() {
        return null;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return null;
    }

    @Override
    public boolean existByNombre(String nombre) {
        return false;
    }
}