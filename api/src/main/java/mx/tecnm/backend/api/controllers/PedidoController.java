package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.PedidoRequest;
import mx.tecnm.backend.api.repository.DetalleCarritoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private DetalleCarritoDAO carritoDAO;

    /**
     * Crea un pedido desde el carrito
     */
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) {
        try {
            String resultado = carritoDAO.generarPedido(
                    request.usuariosId(),
                    request.metodosPagoId(),
                    1 // domicilio HARDCODE mientras no tengas tabla en frontend
            );

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Pedido creado correctamente",
                    "detalle", resultado
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
