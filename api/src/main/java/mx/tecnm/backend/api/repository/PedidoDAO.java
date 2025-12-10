package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.Pedido;
import mx.tecnm.backend.api.models.PedidoRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class PedidoDAO {

    private final JdbcTemplate jdbcTemplate;

    public PedidoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pedido crearPedido(PedidoRequest request) {
        // Valores recibidos
        BigDecimal importeProductos = request.importeProductos();
        BigDecimal importeEnvio = request.importeEnvio();

        // Calcular IVA 16%
        BigDecimal iva = importeProductos.multiply(new BigDecimal("0.16"));

        // Calcular total
        BigDecimal total = importeProductos.add(importeEnvio).add(iva);

        // Generar número de pedido como UUID
        UUID numero = UUID.randomUUID();

        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("""
                INSERT INTO pedidos 
                (fecha, numero, importe_productos, importe_envio, usuarios_id, metodos_pago_id, fecha_hora_pago, importe_iva, total)
                VALUES (NOW(), ?, ?, ?, ?, ?, NOW(), ?, ?)
            """, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, numero.toString());
            ps.setBigDecimal(2, importeProductos);
            ps.setBigDecimal(3, importeEnvio);
            ps.setInt(4, request.usuariosId());
            ps.setInt(5, request.metodosPagoId());
            ps.setBigDecimal(6, iva);
            ps.setBigDecimal(7, total);

            return ps;
        }, kh);

        Integer id = kh.getKey().intValue();

        return new Pedido(
                id,
                LocalDateTime.now(),
                numero,
                importeProductos,
                importeEnvio,
                request.usuariosId(),
                request.metodosPagoId(),
                LocalDateTime.now(),
                iva,
                total
        );
    }
}
