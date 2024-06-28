package com.softii.laborappbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softii.laborappbackend.dto.TrabajoCreationDTO;
import com.softii.laborappbackend.dto.TrabajoDTO;
import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.EstadoTrabajo;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> crearTrabajo(@RequestPart("trabajoData") String trabajoData, @RequestPart("imagen") MultipartFile imagen) {
        try {
            TrabajoCreationDTO trabajoDTO = new ObjectMapper().readValue(trabajoData, TrabajoCreationDTO.class);
            EstadoTrabajo estadoTrabajo = EstadoTrabajo.valueOf(trabajoDTO.getEstado().toUpperCase());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaLimiteDate = formatter.parse(trabajoDTO.getFechaLimite());

            Optional<Cliente> clienteOptional = clienteRepository.findById(trabajoDTO.getIdcliente());
            if (clienteOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Cliente no encontrado"));
            }
            Cliente cliente = clienteOptional.get();

            Trabajo nuevoTrabajo = new Trabajo();
            nuevoTrabajo.setCliente(cliente);
            nuevoTrabajo.setTitulo(trabajoDTO.getTitulo());
            nuevoTrabajo.setDescripcion(trabajoDTO.getDescripcion());
            nuevoTrabajo.setPresupuesto(trabajoDTO.getPresupuesto());
            nuevoTrabajo.setFechaLimite(fechaLimiteDate);
            nuevoTrabajo.setEstado(estadoTrabajo);
            nuevoTrabajo.setCategoria(trabajoDTO.getCategoria());
            nuevoTrabajo.setUbicacion(trabajoDTO.getUbicacion());

            if (!imagen.isEmpty()) {
                nuevoTrabajo.setImagen(imagen.getBytes());
            }

            Trabajo trabajoGuardado = trabajoRepository.save(nuevoTrabajo);
            return ResponseEntity.status(HttpStatus.CREATED).body(new TrabajoDTO(trabajoGuardado));
        } catch (IllegalArgumentException | ParseException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Datos de trabajo no válidos (verifica el formato de la fecha y estado)"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error interno del servidor: " + ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TrabajoDTO>> obtenerTodosLosTrabajos() {
        List<Trabajo> trabajos = trabajoRepository.findAll();
        List<TrabajoDTO> trabajoDTOs = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            trabajoDTOs.add(new TrabajoDTO(trabajo));
        }
        return ResponseEntity.ok(trabajoDTOs);
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
        if (trabajoOptional.isPresent() && trabajoOptional.get().getImagen() != null) {
            byte[] imagen = trabajoOptional.get().getImagen();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<TrabajoDTO>> getTrabajosByClienteId(@PathVariable Long id) {
        List<Trabajo> trabajos = trabajoRepository.findByClienteIdcliente(id);
        List<TrabajoDTO> trabajoDTOs = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            trabajoDTOs.add(new TrabajoDTO(trabajo));
        }
        return ResponseEntity.ok(trabajoDTOs);
    }

    private String generarImagenUrl(Long trabajoId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/trabajos/")
                .path(trabajoId.toString())
                .path("/imagen")
                .toUriString();
    }
}
