package mx.tecnm.backend.api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modelo que representa un pedido realizado
 */
public record Pedido(
        Integer id,
        LocalDateTime fecha,
        UUID numero,
        BigDecimal importeProductos,
        BigDecimal importeEnvio,
        Integer usuariosId,
        Integer metodosPagoId,
        LocalDateTime fechaHoraPago,
        BigDecimal importeIva,
        BigDecimal total
) {
    // Constructor para crear nuevo pedido
    public Pedido(BigDecimal importeProductos, BigDecimal importeEnvio, Integer usuariosId, Integer metodosPagoId) {
        this(null, null, null, importeProductos, importeEnvio, usuariosId, metodosPagoId, null, null, null);
    }
}