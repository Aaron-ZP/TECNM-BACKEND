package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.MetodosPago;
import mx.tecnm.backend.api.repository.MetodosPagoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/metodos-pago")
public class MetodosPagoController {

    @Autowired
    private MetodosPagoDAO repo;

    // 📌 CONSULTAR TODOS
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return ResponseEntity.ok(repo.obtenerMetodosPago());
    }

    // 📌 CONSULTAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        return repo.obtenerMetodoPagoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📌 CREAR CON PARAMS
    @PostMapping
    public ResponseEntity<?> crear(
            @RequestParam String nombre,
            @RequestParam(required = false) BigDecimal comision) {

        MetodosPago nuevo = new MetodosPago(0, nombre, comision, true);

        return repo.crearMetodoPago(nuevo)
                .map(m -> ResponseEntity.created(URI.create("/metodos-pago/" + m.id())).body(m))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    // 📌 ACTUALIZAR CON PARAMS
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable int id,
            @RequestParam String nombre,
            @RequestParam(required = false) BigDecimal comision) {

        MetodosPago mp = new MetodosPago(id, nombre, comision, true);

        return repo.actualizarMetodoPago(id, mp)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📌 ELIMINAR (BAJA LÓGICA)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        boolean eliminado = repo.eliminarMetodoPago(id);

        return eliminado
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
