package mx.tecnm.backend.api.api.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Usuario(
        int id,
        String nombre,
        String email,
        String telefono,
        String sexo,
        LocalDate fecha_nacimiento,
        String contrasena,
        LocalDateTime fecha_registro, // ← CAMBIO
        boolean activo
) {}
