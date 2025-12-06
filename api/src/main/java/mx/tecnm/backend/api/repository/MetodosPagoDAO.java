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

    public List<MetodosPago> obtenerMetodosPago() {
        String sql = "SELECT id, nombre, comision FROM metodos_pago";
        return jdbcClient.sql(sql).query(new MetodosPagoRM()).list();
    }

    public Optional<MetodosPago> obtenerMetodoPagoPorId(int id) {
        String sql = "SELECT id, nombre, comision FROM metodos_pago WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new MetodosPagoRM())
                .optional();
    }

    public Optional<MetodosPago> crearMetodoPago(MetodosPago nuevoMetodoPago) {
        String sql = "INSERT INTO metodos_pago (nombre, comision) VALUES (?, ?) " +
                "RETURNING id, nombre, comision";

        BigDecimal comision = (nuevoMetodoPago.comision() != null)
                ? nuevoMetodoPago.comision()
                : new BigDecimal("1.50");

        return jdbcClient.sql(sql)
                .param(nuevoMetodoPago.nombre())
                .param(comision)
                .query(new MetodosPagoRM())
                .optional();
    }

    public Optional<MetodosPago> actualizarMetodoPago(int id, MetodosPago actualizadoMetodoPago) {
        String sql = "UPDATE metodos_pago SET nombre = ?, comision = ? WHERE id = ? " +
                "RETURNING id, nombre, comision";

        return jdbcClient.sql(sql)
                .param(actualizadoMetodoPago.nombre())
                .param(actualizadoMetodoPago.comision())
                .param(id)
                .query(new MetodosPagoRM())
                .optional();
    }

    public Optional<MetodosPago> eliminarMetodoPago(int id) {
        String sql = "DELETE FROM metodos_pago WHERE id = ? " +
                "RETURNING id, nombre, comision";

        return jdbcClient.sql(sql)
                .param(id)
                .query(new MetodosPagoRM())
                .optional();
    }
}