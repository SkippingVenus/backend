package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.entities.EstadoPropuesta;
import com.softii.laborappbackend.entities.Postulacion;
import com.softii.laborappbackend.repositories.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @PostMapping
    public ResponseEntity<?> crearPostulacion(@RequestBody Postulacion postulacion) {
        // Añadir logs para confirmar recepción de datos
        System.out.println("Recibido postulacion: " + postulacion);
        System.out.println("Trabajo ID: " + postulacion.getTrabajo().getIdtrabajo());
        System.out.println("Cliente ID: " + postulacion.getCliente().getIdcliente());
        System.out.println("Freelancer ID: " + postulacion.getFreelancer().getIdfreelancer());

        // Validar y guardar la postulacion
        postulacion.setEstado(EstadoPropuesta.PENDIENTE); // O cualquier estado inicial que necesites
        postulacionRepository.save(postulacion);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Postulacion>> obtenerPostulaciones() {
        List<Postulacion> postulaciones = postulacionRepository.findAll();
        return ResponseEntity.ok(postulaciones);
    }
}
