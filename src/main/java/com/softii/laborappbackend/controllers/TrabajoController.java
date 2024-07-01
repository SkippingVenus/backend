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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/trabajos")
public class TrabajoController {

    private static final Logger logger = LoggerFactory.getLogger(TrabajoController.class);

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    @Transactional
    public ResponseEntity<?> crearTrabajo(@RequestPart("trabajoData") String trabajoData, @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return saveOrUpdateTrabajo(null, trabajoData, imagen);
    }

    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    @Transactional
    public ResponseEntity<?> actualizarTrabajo(@PathVariable Long id, @RequestPart("trabajoData") String trabajoData, @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return saveOrUpdateTrabajo(id, trabajoData, imagen);
    }

    private ResponseEntity<?> saveOrUpdateTrabajo(Long id, String trabajoData, MultipartFile imagen) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TrabajoCreationDTO trabajoDTO = mapper.readValue(trabajoData, TrabajoCreationDTO.class);

            if (trabajoDTO.getIdcliente() == null) {
                logger.error("ID del cliente es null");
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "ID del cliente no puede ser null"));
            }

            EstadoTrabajo estadoTrabajo = EstadoTrabajo.valueOf(trabajoDTO.getEstado().toUpperCase());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaLimiteDate = formatter.parse(trabajoDTO.getFechaLimite());

            logger.info("Buscando cliente con ID: {}", trabajoDTO.getIdcliente());
            Optional<Cliente> clienteOptional = clienteRepository.findByUsuario_Idusuario(trabajoDTO.getIdcliente());
            if (clienteOptional.isEmpty()) {
                logger.error("Cliente no encontrado con ID: {}", trabajoDTO.getIdcliente());
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Cliente no encontrado con ID: " + trabajoDTO.getIdcliente()));
            }
            Cliente cliente = clienteOptional.get();

            Trabajo trabajo;
            if (id == null) {
                trabajo = new Trabajo();
            } else {
                Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
                if (trabajoOptional.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                trabajo = trabajoOptional.get();
            }

            trabajo.setCliente(cliente);
            trabajo.setTitulo(trabajoDTO.getTitulo());
            trabajo.setDescripcion(trabajoDTO.getDescripcion());
            trabajo.setPresupuesto(trabajoDTO.getPresupuesto());
            trabajo.setFechaLimite(fechaLimiteDate);
            trabajo.setEstado(estadoTrabajo);
            trabajo.setCategoria(trabajoDTO.getCategoria());
            trabajo.setUbicacion(trabajoDTO.getUbicacion());

            if (imagen != null && !imagen.isEmpty()) {
                trabajo.setImagen(imagen.getBytes());
            }

            Trabajo trabajoGuardado = trabajoRepository.save(trabajo);
            return ResponseEntity.status(HttpStatus.CREATED).body(new TrabajoDTO(trabajoGuardado));
        } catch (IllegalArgumentException | ParseException ex) {
            logger.error("Error parsing date or invalid state: ", ex);
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Datos de trabajo no válidos (verifica el formato de la fecha y estado)"));
        } catch (Exception ex) {
            logger.error("Internal server error: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error interno del servidor: " + ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TrabajoDTO>> obtenerTodosLosTrabajos() {
        List<Trabajo> trabajos = trabajoRepository.findAll();
        List<TrabajoDTO> trabajoDTOs = trabajos.stream().map(TrabajoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(trabajoDTOs);
    }

    @GetMapping("/{id}/imagen")
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public ResponseEntity<List<TrabajoDTO>> getTrabajosByClienteId(@PathVariable Long id) {
        List<Trabajo> trabajos = trabajoRepository.findByCliente_Idcliente(id);
        List<TrabajoDTO> trabajoDTOs = trabajos.stream().map(TrabajoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(trabajoDTOs);
    }

    @GetMapping("/estado/{estado}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getTrabajosByEstado(@PathVariable String estado) {
        EstadoTrabajo estadoTrabajo;
        try {
            estadoTrabajo = EstadoTrabajo.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Estado de trabajo no válido"));
        }
        List<Trabajo> trabajos = trabajoRepository.findByEstado(estadoTrabajo);
        List<TrabajoDTO> trabajoDTOs = trabajos.stream().map(TrabajoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(trabajoDTOs);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTrabajo(@PathVariable Long id) {
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
        if (trabajoOptional.isPresent()) {
            trabajoRepository.delete(trabajoOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/aprobar")
    @Transactional
    public ResponseEntity<?> aprobarTrabajo(@PathVariable Long id) {
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
        if (trabajoOptional.isPresent()) {
            Trabajo trabajo = trabajoOptional.get();
            trabajo.setEstado(EstadoTrabajo.APROBADO);
            trabajoRepository.save(trabajo);
            String mensaje = String.format("El trabajo fue aceptado para el cliente %s.", trabajo.getCliente().getNombre());
            return ResponseEntity.ok(Collections.singletonMap("message", mensaje));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Trabajo no encontrado."));
        }
    }

    @PutMapping("/{id}/desaprobar")
    @Transactional
    public ResponseEntity<?> desaprobarTrabajo(@PathVariable Long id) {
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(id);
        if (trabajoOptional.isPresent()) {
            Trabajo trabajo = trabajoOptional.get();
            trabajo.setEstado(EstadoTrabajo.RECHAZADO);
            trabajoRepository.save(trabajo);
            String mensaje = String.format("El trabajo fue desaprobado para el cliente %s.", trabajo.getCliente().getNombre());
            return ResponseEntity.ok(Collections.singletonMap("message", mensaje));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Trabajo no encontrado."));
        }
    }
}
