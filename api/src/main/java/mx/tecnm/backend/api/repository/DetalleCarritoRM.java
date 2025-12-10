package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.DetalleCarrito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper para convertir ResultSet en objetos DetalleCarrito
 */
public class DetalleCarritoRM implements RowMapper<DetalleCarrito> {

    @Override
    public DetalleCarrito mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Verifica si existe la columna nombreProducto (para consultas con JOIN)
        String nombreProducto = null;
        try {
            nombreProducto = rs.getString("nombre_producto");
        } catch (SQLException e) {
            // La columna no existe, se deja null
        }

        return new DetalleCarrito(
                rs.getInt("id"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio"),
                rs.getInt("productos_id"),
                rs.getInt("usuarios_id"),
                nombreProducto,
                null // subtotal se calcula después
        );
    }
}