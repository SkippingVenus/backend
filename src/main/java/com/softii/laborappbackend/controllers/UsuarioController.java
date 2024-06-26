package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.UsuarioCreationDTO;
import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(loginData.getCorreo());
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                if (usuario.getContrasena().equals(loginData.getContrasena())) {
                    String rol = usuario.getRol();
                    Map<String, Object> response = new HashMap<>();
                    response.put("usuario", usuario);

                    if ("CLIENTE".equals(rol)) {
                        Optional<Cliente> clienteOptional = clienteRepository.findByUsuario_Idusuario(usuario.getIdusuario());
                        if (clienteOptional.isPresent()) {
                            Cliente cliente = clienteOptional.get();
                            response.put("idcliente", cliente.getIdcliente());
                        } else {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Cliente no encontrado"));
                        }
                    }

                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Credenciales incorrectas"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Usuario no encontrado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error en el servidor"));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/perfil/{id}", produces = "application/json")
    public ResponseEntity<?> obtenerPerfilUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Usuario no encontrado"));
        }
    }

    @GetMapping(value = "/{id}/imagen", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent() && usuarioOptional.get().getImagen() != null) {
            byte[] imagen = usuarioOptional.get().getImagen();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreationDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setCorreo(usuarioDTO.getCorreo());
            usuario.setContrasena(usuarioDTO.getContrasena());
            usuario.setRol(usuarioDTO.getRol().toUpperCase());
            usuario.setEdad(usuarioDTO.getEdad());
            usuario.setSexo(usuarioDTO.getSexo());
            usuario.setNumero(usuarioDTO.getNumero());
            if (usuarioDTO.getImagen() != null && !usuarioDTO.getImagen().isEmpty()) {
                usuario.setImagen(usuarioDTO.getImagen().getBytes());
            }

            Usuario nuevoUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioCreationDTO usuarioDTO) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Usuario no encontrado"));
            }

            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setCorreo(usuarioDTO.getCorreo());
            usuarioExistente.setContrasena(usuarioDTO.getContrasena());
            usuarioExistente.setRol(usuarioDTO.getRol().toUpperCase());
            usuarioExistente.setEdad(usuarioDTO.getEdad());
            usuarioExistente.setSexo(usuarioDTO.getSexo());
            usuarioExistente.setNumero(usuarioDTO.getNumero());
            if (usuarioDTO.getImagen() != null && !usuarioDTO.getImagen().isEmpty()) {
                usuarioExistente.setImagen(usuarioDTO.getImagen().getBytes());
            }

            Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
