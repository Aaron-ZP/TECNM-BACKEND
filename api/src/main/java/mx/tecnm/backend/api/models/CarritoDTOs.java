package mx.tecnm.backend.api.models;

public record CarritoDTOs() {

    // Reemplaza al antiguo "OrderRequest" para agregar al carrito
    public record AgregarProductoRequest(
            Integer userId,
            Integer productId,
            Integer cantidad // Opcional, por defecto será 1 en la lógica
    ) {}

    // Para el Checkout
    public record CheckoutRequest(
            Integer userId,
            Integer metodoPagoId,
            Integer domicilioId
    ) {}
}