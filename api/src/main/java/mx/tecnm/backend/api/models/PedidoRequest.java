package mx.tecnm.backend.api.models;

import java.math.BigDecimal;

public record PedidoRequest(
        BigDecimal importeProductos,
        BigDecimal importeEnvio,
        Integer usuariosId,
        Integer metodosPagoId
) {}
