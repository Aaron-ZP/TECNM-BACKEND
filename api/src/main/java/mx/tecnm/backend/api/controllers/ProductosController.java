package mx.tecnm.backend.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Productos>crearProducto(@RequestBody Productos nuevoP) {

        Productos productoCreado = repo.crearProducto(nuevoP);
        return ResponseEntity.ok(productoCreado);

    }

    //º Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable int id, @RequestBody Productos p) {
        Productos exiProductos = repo.obtenerProductoPorId(id);

        if (exiProductos != null) {
            Productos productoActualizado = repo.actualizarProducto(id, p);
            return ResponseEntity.ok(productoActualizado);
            
        } else {
            return ResponseEntity.notFound().build();
        }

        
    }


    // Eliminar producto (cambiar estado a false)

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable int id) {
        Productos p = repo.obtenerProductoPorId(id);

        if(p != null ){
            boolean actualizado = repo.cambiarEstadoProductos(id, false);
            if(actualizado){
                return ResponseEntity.ok("Producto con ID " + id + " desactivado correctamente.");
            } else {
                return ResponseEntity.notFound().build();
        }
        } else {
            return ResponseEntity.notFound().build();
        }


        
      


    }




}
