package mx.tecnm.backend.api.api.models;

import java.time.LocalDate;

public record Usuario(
        int id,
        String nombre,
        String email,
        String telefono,
        String sexo,
        LocalDate fecha_nacimiento,
        String contrasena,
        LocalDate fecha_registro
) {}
