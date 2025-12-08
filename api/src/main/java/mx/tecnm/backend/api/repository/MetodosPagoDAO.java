package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.MetodosPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class MetodosPagoDAO {

    @Autowired
    private JdbcClient jdbcClient;

    private BigDecimal defaultComision(BigDecimal c) {
        return (c != null) ? c : new BigDecimal("1.50");
    }

    public List<MetodosPago> obtenerMetodosPago() {
        String sql = "SELECT id, nombre, comision, estado FROM metodos_pago WHERE estado = TRUE";
        return jdbcClient.sql(sql).query(new MetodosPagoRM()).list();
    }

    public Optional<MetodosPago> obtenerMetodoPagoPorId(int id) {
        String sql = "SELECT id, nombre, comision, estado FROM metodos_pago WHERE id = ? AND estado = TRUE";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new MetodosPagoRM())
                .optional();
    }

    public Optional<MetodosPago> crearMetodoPago(MetodosPago mp) {
        String sql = """
            INSERT INTO metodos_pago (nombre, comision, estado)
            VALUES (?, ?, TRUE)
            RETURNING id, nombre, comision, estado
        """;

        return jdbcClient.sql(sql)
                .param(mp.nombre())
                .param(defaultComision(mp.comision()))
                .query(new MetodosPagoRM())
                .optional();
    }

    public Optional<MetodosPago> actualizarMetodoPago(int id, MetodosPago mp) {
        String sql = """
            UPDATE metodos_pago 
            SET nombre = ?, comision = ?
            WHERE id = ?
            RETURNING id, nombre, comision, estado
        """;

        return jdbcClient.sql(sql)
                .param(mp.nombre())
                .param(defaultComision(mp.comision()))
                .param(id)
                .query(new MetodosPagoRM())
                .optional();
    }

    public boolean eliminarMetodoPago(int id) {
        String sql = """
            UPDATE metodos_pago 
            SET estado = FALSE 
            WHERE id = ? AND estado = TRUE
        """;

        int filas = jdbcClient.sql(sql)
                .param(id)
                .update();

        return filas > 0;
    }
}
