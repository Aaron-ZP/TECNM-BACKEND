package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcClient jdbc;

    public List<Usuario> obtenerUsuarios() {
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE ORDER BY id";
        return jdbc.sql(sql).query(new UsuarioRM()).list();
    }

    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND activo = TRUE";
        return jdbc.sql(sql)
                .param(id)
                .query(new UsuarioRM())
                .optional()
                .orElse(null);
    }

    public Usuario crearUsuario(Usuario u) {
        String sql = """
                INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro, activo)
                VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, TRUE)
                RETURNING *;
                """;

        return jdbc.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fecha_nacimiento())
                .param(u.contrasena())
                .query(new UsuarioRM())
                .single();
    }

    public Usuario actualizarUsuario(Usuario u) {
        String sql = """
                UPDATE usuarios
                SET nombre = ?, email = ?, telefono = ?, sexo = ?, fecha_nacimiento = ?, contrasena = ?
                WHERE id = ? AND activo = TRUE
                RETURNING *;
                """;

        return jdbc.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fecha_nacimiento())
                .param(u.contrasena())
                .param(u.id())
                .query(new UsuarioRM())
                .optional()
                .orElse(null);
    }

    public void eliminarUsuario(int id) {
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id = ?";
        jdbc.sql(sql).param(id).update();
    }
}
