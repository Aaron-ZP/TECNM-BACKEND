package mx.tecnm.backend.api.repository;



import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.tecnm.backend.api.models.Productos;

public class ProductosRM implements RowMapper<Productos> {

    @Override
    public Productos mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        return new Productos(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getBigDecimal("precio"),
                rs.getString("sku"),
                rs.getString("color"),
                rs.getString("marca"),
                rs.getString("descripcion"),
                rs.getBigDecimal("peso"),
                rs.getBigDecimal("alto"),
                rs.getBigDecimal("ancho"),
                rs.getBigDecimal("profundidad"),
                rs.getInt("categorias_id"),
                rs.getBoolean("estado")
        );
     
    }
}
