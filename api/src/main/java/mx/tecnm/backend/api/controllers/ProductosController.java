package mx.tecnm.backend.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Productos;
import mx.tecnm.backend.api.repository.ProductosDAO;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    ProductosDAO repo;


        // Obtener todas las categorías 
    @GetMapping()
    public ResponseEntity<List<Productos>> obtenerProductos() {
        return ResponseEntity.ok(repo.obtenerProductos());
    }

    // Obtener productos por ID

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable int id) {
        Productos p = repo.obtenerProductoPorId(id);

        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
             return ResponseEntity
                .status(404)
                .body(Map.of(
                    "mensaje", "El producto con ID " + id + " no existe o fue desactivado.",
                    "status", 404

                ));
        }
    }

    // Crear nuevo producto

    @PostMapping()
    public ResponseEntity<Productos> crearProducto(@RequestParam String nuevoProducto) {

        Productos productoCreado = repo.crearProducto(nuevoProducto);
        return ResponseEntity.ok(productoCreado);

    }




}
