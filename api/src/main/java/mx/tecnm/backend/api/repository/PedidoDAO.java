package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para manejar operaciones de pedidos
 */
@Repository
public class PedidoDAO {

    @Autowired
    private JdbcClient jdbcClient;

    /**
     * Crea un nuevo pedido
     */
    public Optional<Pedido> crearPedido(Pedido pedido) {
// 1. Generamos el UUID del pedido aquí
        UUID numeroPedido = UUID.randomUUID();



        String sql = """
INSERT INTO pedidos (importe_productos, importe_envio, usuarios_id, metodos_pago_id)
            VALUES (?, ?, ?, ?)
            RETURNING id, fecha, numero, importe_productos, importe_envio, 
                      usuarios_id, metodos_pago_id, fecha_hora_pago, importe_iva, total
        """;

        return jdbcClient.sql(sql)
                .param(pedido.importeProductos())
                .param(pedido.importeEnvio())
                .param(pedido.usuariosId())
                .param(pedido.metodosPagoId())
                .query((rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        (java.util.UUID) rs.getObject("numero"),
                        rs.getBigDecimal("importe_productos"),
                        rs.getBigDecimal("importe_envio"),
                        rs.getInt("usuarios_id"),
                        rs.getInt("metodos_pago_id"),
                        rs.getTimestamp("fecha_hora_pago") != null ?
                                rs.getTimestamp("fecha_hora_pago").toLocalDateTime() : null,
                        rs.getBigDecimal("importe_iva"),
                        rs.getBigDecimal("total")
                ))
                .optional();
    }

    /**
     * Crea los detalles de un pedido desde el carrito
     */
    public int crearDetallesPedido(int pedidoId, int usuarioId) {
        String sql = """
            INSERT INTO detalles_pedido (cantidad, precio, productos_id, pedidos_id)
            SELECT cantidad, precio, productos_id, ?
            FROM detalles_carrito
            WHERE usuarios_id = ?
        """;

        return jdbcClient.sql(sql)
                .param(pedidoId)
                .param(usuarioId)
                .update();
    }
}