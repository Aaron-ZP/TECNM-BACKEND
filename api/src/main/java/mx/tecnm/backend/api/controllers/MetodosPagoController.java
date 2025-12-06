package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.MetodosPago;
import mx.tecnm.backend.api.repository.MetodosPagoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/metodos-pago")
public class MetodosPagoController {

    @Autowired
    MetodosPagoDAO repo;

    @GetMapping
    public ResponseEntity<List<MetodosPago>> obtenerMetodosPagos() {
        List<MetodosPago> resultado = repo.obtenerMetodosPago();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodosPago> obtenerMetodosPagoPorId(@PathVariable int id){
        Optional<MetodosPago> metodoPago = repo.obtenerMetodoPagoPorId(id);

        return metodoPago.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodosPago> crearMetodoPago(@RequestBody MetodosPago nuevoMetodo){
        Optional<MetodosPago> creado = repo.crearMetodoPago(nuevoMetodo);

        return creado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodosPago> actualizarMetodoPago(@PathVariable int id, @RequestBody MetodosPago metodoPago) {
        Optional<MetodosPago> actualizado = repo.actualizarMetodoPago(id, metodoPago);

        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMetodoPago(@PathVariable int id){
        Optional<MetodosPago> eliminado = repo.eliminarMetodoPago(id);

        if (eliminado.isPresent()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}