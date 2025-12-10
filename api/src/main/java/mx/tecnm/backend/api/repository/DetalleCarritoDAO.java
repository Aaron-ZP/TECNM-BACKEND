package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.models.DetalleCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DetalleCarritoDAO {

    @Autowired
    private JdbcClient jdbcClient;

    public List<DetalleCarrito> obtenerCarritoPorUsuario(int usuarioId) {
        String sql = """
            SELECT dc.id, dc.cantidad, dc.precio, dc.productos_id, dc.usuarios_id,
                   p.nombre as nombre_producto
            FROM detalles_carrito dc
            INNER JOIN productos p ON dc.productos_id = p.id
            WHERE dc.usuarios_id = ?
            ORDER BY dc.id
        """;

        return jdbcClient.sql(sql)
                .param(usuarioId)
                .query(new DetalleCarritoRM())
                .list()
                .stream()
                .map(DetalleCarrito::withSubtotal)
                .toList();
    }

    public void agregarOIncrementar(int usuarioId, int productoId, BigDecimal precio) {
        Optional<DetalleCarrito> existente = jdbcClient.sql("SELECT * FROM detalles_carrito WHERE usuarios_id = ? AND productos_id = ?")
                .param(usuarioId).param(productoId)
                .query(new DetalleCarritoRM()).optional();

        if (existente.isPresent()) {
            jdbcClient.sql("UPDATE detalles_carrito SET cantidad = cantidad + 1 WHERE id = ?")
                    .param(existente.get().id()).update();
        } else {
            jdbcClient.sql("INSERT INTO detalles_carrito (cantidad, precio, productos_id, usuarios_id) VALUES (1, ?, ?, ?)")
                    .param(precio).param(productoId).param(usuarioId).update();
        }
    }

    public void eliminarODecrementar(int usuarioId, int productoId) {
        Optional<DetalleCarrito> existente = jdbcClient.sql("SELECT * FROM detalles_carrito WHERE usuarios_id = ? AND productos_id = ?")
                .param(usuarioId).param(productoId)
                .query(new DetalleCarritoRM()).optional();

        if (existente.isPresent()) {
            if (existente.get().cantidad() > 1) {
                jdbcClient.sql("UPDATE detalles_carrito SET cantidad = cantidad - 1 WHERE id = ?")
                        .param(existente.get().id()).update();
            } else {
                jdbcClient.sql("DELETE FROM detalles_carrito WHERE id = ?")
                        .param(existente.get().id()).update();
            }
        }
    }

    public void limpiarCarrito(int usuarioId) {
        jdbcClient.sql("DELETE FROM detalles_carrito WHERE usuarios_id = ?")
                .param(usuarioId).update();
    }

    @Transactional
    public String generarPedido(int userId, int metodoPagoId, int domicilioId) {
        List<DetalleCarrito> items = obtenerCarritoPorUsuario(userId);
        if (items.isEmpty()) throw new RuntimeException("El carrito está vacío.");

        BigDecimal totalProductos = items.stream()
                .map(item -> item.precio().multiply(new BigDecimal(item.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costoEnvio = new BigDecimal("150.00"); // Ajusta esto según tu lógica
        UUID orderNumber = UUID.randomUUID();

        // CORRECCIÓN CRÍTICA AQUÍ:
        // 1. Quitamos 'total' del INSERT porque es GENERATED ALWAYS en tu BD.
        // 2. Quitamos 'fecha' porque tiene DEFAULT now() en tu BD.
        // 3. Postgres permite 'RETURNING id' nativamente.
        String insertPedido = """
            INSERT INTO pedidos (numero, importe_productos, importe_envio, usuarios_id, metodos_pago_id) 
            VALUES (?, ?, ?, ?, ?) 
            RETURNING id
        """;

        Integer pedidoId = jdbcClient.sql(insertPedido)
                .param(orderNumber)
                .param(totalProductos)
                .param(costoEnvio)
                .param(userId)
                .param(metodoPagoId)
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        // Insertar Detalles
        String insertDetalle = "INSERT INTO detalles_pedido (cantidad, precio, productos_id, pedidos_id) VALUES (?, ?, ?, ?)";
        for (DetalleCarrito item : items) {
            jdbcClient.sql(insertDetalle)
                    .param(item.cantidad())
                    .param(item.precio())
                    .param(item.productosId())
                    .param(pedidoId)
                    .update();
        }

        // Insertar Envío
        // Generamos un string corto para el tracking
        String tracking = "TRK-" + orderNumber.toString().substring(0, 8).toUpperCase();

        // Asumiendo que 'estado' y 'fecha' tienen defaults o no son requeridos, o agregamos 'estado'
        String insertEnvio = "INSERT INTO envios (numero_seguimiento, domicilios_id, pedidos_id, estado) VALUES (?, ?, ?, 'PENDIENTE')";
        jdbcClient.sql(insertEnvio)
                .param(tracking)
                .param(domicilioId)
                .param(pedidoId)
                .update();

        limpiarCarrito(userId);

        // El total real lo calculará la base de datos, pero podemos mostrar un estimado aquí
        return "Pedido generado exitosamente. ID: " + pedidoId + ". Tu número de orden es: " + orderNumber;
    }

    public Optional<BigDecimal> obtenerPrecioProducto(int productoId) {
        return jdbcClient.sql("SELECT precio FROM productos WHERE id = ?")
                .param(productoId).query((rs, rowNum) -> rs.getBigDecimal("precio")).optional();
    }
}