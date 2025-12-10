package mx.tecnm.backend.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.tecnm.backend.api.models.Usuario;

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
                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                rs.getBoolean("estado") // ← BOOLEAN REAL DE SUPABASE
        );
    }
}
