package mx.tecnm.backend.api.api.repository;

import mx.tecnm.backend.api.api.models.Usuario;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRM implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("sexo"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("contrasena"),
                rs.getDate("fecha_registro").toLocalDate(),
                rs.getBoolean("activo") // ← importante
        );
    }
}
