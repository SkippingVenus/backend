package com.softii.laborappbackend.controllers;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import java.text.ParseException;

import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import com.softii.laborappbackend.dto.TrabajoCreationDTO;
import com.softii.laborappbackend.entities.EstadoTrabajo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoRepository trabajoRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> crearTrabajo(@RequestPart("trabajoData") String trabajoData, @RequestPart("imagen") MultipartFile imagen) {
        try {
            TrabajoCreationDTO trabajoDTO = new ObjectMapper().readValue(trabajoData, TrabajoCreationDTO.class);
            EstadoTrabajo estadoTrabajo = EstadoTrabajo.valueOf(trabajoDTO.getEstado().toUpperCase());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaLimiteDate = formatter.parse(trabajoDTO.getFechaLimite());

            Trabajo nuevoTrabajo = new Trabajo();
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
            return ResponseEntity.status(HttpStatus.CREATED).body(trabajoGuardado);
        } catch (IllegalArgumentException | ParseException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Datos de trabajo no válidos (verifica el formato de la fecha y estado)"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error interno del servidor: " + ex.getMessage()));
        }
    }
}


