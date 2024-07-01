package com.softii.laborappbackend.init;

import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByCorreo("admin@hotmail.com")) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setCorreo("admin@hotmail.com");
            admin.setContrasena("admin");
            admin.setRol("ADMIN");
            admin.setEdad(30); // Establece la edad predeterminada para el admin
            admin.setSexo("Hombre"); // Establece el sexo predeterminado para el admin
            admin.setNumero("123456789"); // Establece el número predeterminado para el admin
            usuarioRepository.save(admin);
        }
    }
}
