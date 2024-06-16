package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.entities.Rol;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:3000") // Habilita CORS para el frontend
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con este correo electrónico");
        }

        String rolString = usuario.getRol().toString().toUpperCase();
        try {
            Rol rolEnum = Rol.valueOf(rolString);
            usuario.setRol(rolEnum);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rol no válido");
        }

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Crear un mapa con el id del usuario y su rol
        Map<String, Object> response = new HashMap<>();
        response.put("idusuario", nuevoUsuario.getIdusuario());
        response.put("rol", nuevoUsuario.getRol().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setIdusuario(id);
            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String contrasenia = credentials.get("contrasenia");

        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getContrasenia().equals(contrasenia)) {
                Map<String, Object> response = new HashMap<>();
                response.put("idusuario", usuario.getIdusuario());
                response.put("rol", usuario.getRol().toString());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
