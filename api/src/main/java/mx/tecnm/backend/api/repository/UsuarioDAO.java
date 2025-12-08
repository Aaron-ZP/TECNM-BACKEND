package mx.tecnm.backend.api.api.repository;

import mx.tecnm.backend.api.api.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcClient jdbc;

    // ✔ Obtener solo activos
    public List<Usuario> obtenerUsuariosActivos() {
        String sql = "SELECT * FROM usuarios WHERE activo = true ORDER BY id";
        return jdbc.sql(sql).query(new UsuarioRM()).list();
    }

    // ✔ Obtener todos (incluye eliminados)
    public List<Usuario> obtenerTodos() {
        return jdbc.sql("SELECT * FROM usuarios ORDER BY id").query(new UsuarioRM()).list();
    }

    // ✔ Obtener por ID (solo activos)
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND activo = true";
        return jdbc.sql(sql).param(id).query(new UsuarioRM()).singleOrNull();
    }

    // ✔ Crear usuario
    public Usuario crearUsuario(Usuario u) {
        String sql = """
                INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro, activo)
                VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE, true)
                RETURNING *;
                """;

        return jdbc.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fecha_nacimiento())
                .param(u.contrasena())
                .query(new UsuarioRM()).single();
    }

    // ✔ Actualizar usuario
    public Usuario actualizarUsuario(Usuario u) {
        String sql = """
                UPDATE usuarios
                SET nombre=?, email=?, telefono=?, sexo=?, fecha_nacimiento=?, contrasena=?, activo=?
                WHERE id=?
                RETURNING *;
                """;

        return jdbc.sql(sql)
                .param(u.nombre())
                .param(u.email())
                .param(u.telefono())
                .param(u.sexo())
                .param(u.fecha_nacimiento())
                .param(u.contrasena())
                .param(true)
                .param(u.id())
                .query(new UsuarioRM()).single();
    }

    // 🔥 Borrado lógico – NO DELETE
    public boolean eliminarUsuarioLogico(int id) {
        String sql = "UPDATE usuarios SET activo=false WHERE id=? AND activo=true";
        return jdbc.sql(sql).param(id).update() > 0;
    }
}
