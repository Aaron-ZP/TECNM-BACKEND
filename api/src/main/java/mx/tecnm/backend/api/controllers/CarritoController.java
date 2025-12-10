package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.CarritoDTOs;
import mx.tecnm.backend.api.models.DetalleCarrito; // Importante
import mx.tecnm.backend.api.repository.DetalleCarritoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List; // Importante
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private DetalleCarritoDAO carritoDAO;

    // 0. NUEVO: Ver el carrito (Faltaba este endpoint)
    @GetMapping("/{userId}")
    public ResponseEntity<List<DetalleCarrito>> getCart(@PathVariable int userId) {
        List<DetalleCarrito> items = carritoDAO.obtenerCarritoPorUsuario(userId);
        return ResponseEntity.ok(items);
    }

    // 1. Agregar producto (Incrementa si existe)
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CarritoDTOs.AgregarProductoRequest request) {
        try {
            // Validar existencia y precio del producto
            Optional<BigDecimal> precioOpt = carritoDAO.obtenerPrecioProducto(request.productId());

            if (precioOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("el Producto no fue encontrado o es inactivo");
            }

            carritoDAO.agregarOIncrementar(request.userId(), request.productId(), precioOpt.get());

            return ResponseEntity.ok("El Producto fue agregado y actualizado en el carrito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar: " + e.getMessage());
        }
    }

    // 2. Eliminar producto (Decrementa si > 1, borra si == 1)
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable int userId, @PathVariable int productId) {
        try {
            carritoDAO.eliminarODecrementar(userId, productId);
            return ResponseEntity.ok("El Producto fue actualizado o eliminado del carrito");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // 3. Limpiar carrito completo
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable int userId) {
        carritoDAO.limpiarCarrito(userId);
        return ResponseEntity.ok("Carrito vaciado completamente");
    }

    // 4. Generar Pedido (Checkout)
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CarritoDTOs.CheckoutRequest request) {
        try {
            String result = carritoDAO.generarPedido(request.userId(), request.metodoPagoId(), request.domicilioId());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error en consola para depurar
            return ResponseEntity.internalServerError().body("Error al procesar el pedido: " + e.getMessage());
        }
    }
}