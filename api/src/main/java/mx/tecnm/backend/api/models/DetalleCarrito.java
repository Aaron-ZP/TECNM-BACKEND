package mx.tecnm.backend.api.models;

import java.math.BigDecimal;

public record DetalleCarrito(
        Integer id,
        Integer cantidad,
        BigDecimal precio,
        Integer productosId,
        Integer usuariosId,
        String nombreProducto,
        BigDecimal subtotal
) {
    public DetalleCarrito(Integer cantidad, BigDecimal precio, Integer productosId, Integer usuariosId) {
        this(null, cantidad, precio, productosId, usuariosId, null, null);
    }

    public DetalleCarrito withSubtotal() {
        BigDecimal calculatedSubtotal = (precio != null && cantidad != null)
                ? precio.multiply(new BigDecimal(cantidad))
                : BigDecimal.ZERO;
        return new DetalleCarrito(id, cantidad, precio, productosId, usuariosId, nombreProducto, calculatedSubtotal);
    }
}