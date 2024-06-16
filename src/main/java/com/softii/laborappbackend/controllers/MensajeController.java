package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.entities.Mensaje;
import com.softii.laborappbackend.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeRepository mensajeRepository;

    // Obtener todos los mensajes
    @GetMapping
    public ResponseEntity<List<Mensaje>> obtenerTodosLosMensajes() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return ResponseEntity.ok(mensajes);
    }

    // Obtener un mensaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerMensajePorId(@PathVariable Long id) {
        Optional<Mensaje> mensajeOptional = mensajeRepository.findById(id);
        return mensajeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo mensaje
    @PostMapping
    public ResponseEntity<Mensaje> crearMensaje(@RequestBody Mensaje mensaje) {
        Mensaje nuevoMensaje = mensajeRepository.save(mensaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMensaje);
    }

    // Actualizar un mensaje existente (puede que no sea necesario en este caso)
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> actualizarMensaje(@PathVariable Long id, @RequestBody Mensaje mensaje) {
        if (mensajeRepository.existsById(id)) {
            mensaje.setIdmensaje(id);
            Mensaje mensajeActualizado = mensajeRepository.save(mensaje);
            return ResponseEntity.ok(mensajeActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        if (mensajeRepository.existsById(id)) {
            mensajeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Otros endpoints personalizados (por ejemplo, obtener mensajes por propuesta)
    // ...
}
