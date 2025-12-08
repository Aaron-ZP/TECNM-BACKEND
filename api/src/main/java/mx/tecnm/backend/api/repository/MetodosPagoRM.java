package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.MetodosPago;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MetodosPagoRM implements RowMapper<MetodosPago> {

    @Override
    public MetodosPago mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MetodosPago(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getBigDecimal("comision"),
                rs.getBoolean("estado")
        );
    }
}
