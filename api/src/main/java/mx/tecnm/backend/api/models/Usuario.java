package mx.tecnm.backend.api.models;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Usuario(
        int id,
        String nombre,
        String email,
        String telefono,
        String sexo,
        LocalDate fechaNacimiento,
        String contrasena,
        LocalDateTime fechaRegistro,
        boolean estado
) {

}
