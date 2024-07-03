package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.ClienteCreationDTO;
import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteCreationDTO clienteDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteDTO.getIdusuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setUsuario(usuarioOptional.get());

        Cliente nuevoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/usuario/{idusuario}")
    public ResponseEntity<Cliente> obtenerClientePorIdUsuario(@PathVariable Long idusuario) {
        Optional<Cliente> clienteOptional = clienteRepository.findByUsuario_Idusuario(idusuario);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody ClienteCreationDTO clienteDTO) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Cliente cliente = clienteOptional.get();
        cliente.setNombre(clienteDTO.getNombre());

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteDTO.getIdusuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        cliente.setUsuario(usuarioOptional.get());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
