package mx.tecnm.backend.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Usuario;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcClient jdbcClient;

    // LISTAR
    public List<Usuario> obtenerUsuarios() {
        String sql = """
                SELECT i      contrasena, fecha_registro, estado
                        FROM usuarios
                        WHERE estado = TRUE
                        ORDER BY idd, nombre, email, telefono, sexo, fecha_nacimiento,
                  
                """;

        return jdbcClient.sql(sql).query(new UsuarioRM()).list();
    }

    // OBTENER POR ID
    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        String sql = """
                SELECT id, nombre, email, telefono, sexo, fecha_nacimiento,
                       contrasena, fecha_registro, estado
                FROM usuarios
                WHERE id = ? AND estado = TRUE
                """;

        return jdbcClient.sql(sql)
                .param(id)
                .query(new UsuarioRM())
                .optional();
    }

    // OBTENER POR EMAIL
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        String sql = """
                SELECT id, nombre, email, telefono, sexo, fecha_nacimiento,
                       contrasena, fecha_registro, estado
                FROM usuarios
                WHERE email = ? AND estado = TRUE
                """;

        return jdbcClient.sql(sql)
                .param(email)
                .query(new UsuarioRM())
                .optional();
    }

    // CREAR
    public Optional<Usuario> crearUsuario(Usuario u) {
        String sql = """
    INSERT INTO usuarios 
    (nombre, email, telefono, sexo, fecha_nacimiento, contrasena, estado)
    VALUES (?, ?, ?, ?::sexo_enum, ?, ?, TRUE)
    RETURNING id, nombre, email, telefono, sexo, fecha_nacimiento,
              contrasena, fecha_registro, estado
    """;

        return jdbcClient.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fechaNacimiento())
                .param(u.contrasena())
                .query(new UsuarioRM())
                .optional();
    }

    // ACTUALIZAR (sin contraseña)
    public Optional<Usuario> actualizarUsuario(int id, Usuario u) {
       String sql = """
    UPDATE usuarios
    SET nombre = ?, email = ?, telefono = ?, sexo = ?::sexo_enum, fecha_nacimiento = ?
    WHERE id = ? AND estado = TRUE
    RETURNING id, nombre, email, telefono, sexo, fecha_nacimiento,
              contrasena, fecha_registro, estado
    """;


        return jdbcClient.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fechaNacimiento())
                .param(id)
                .query(new UsuarioRM())
                .optional();
    }

    // CAMBIAR CONTRASEÑA
    public Optional<Usuario> actualizarContrasena(int id, String nuevaContrasena) {
     String sql = """
    UPDATE usuarios
    SET estado = FALSE
    WHERE id = ?
    RETURNING id, nombre, email, telefono, sexo, fecha_nacimiento,
              contrasena, fecha_registro, estado
    """;


        return jdbcClient.sql(sql)
                .param(nuevaContrasena)
                .param(id)
                .query(new UsuarioRM())
                .optional();
    }

    // ELIMINAR (SOFT DELETE)
    public Optional<Usuario> eliminarUsuario(int id) {
        String sql = """
                UPDATE usuarios
                SET estado = FALSE
                WHERE id = ?
                RETURNING id, nombre, email, telefono, sexo, fecha_nacimiento,
                          contrasena, fecha_registro, estado
                """;

        return jdbcClient.sql(sql)
                .param(id)
                .query(new UsuarioRM())
                .optional();
    }
}
