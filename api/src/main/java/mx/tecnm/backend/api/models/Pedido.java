package mx.tecnm.backend.api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modelo que representa un pedido leído desde la BD
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
) {}
