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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.tecnm.backend.api.models.Categoria;
import mx.tecnm.backend.api.repository.CategoriaDAO;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaDAO repo;

    // Obtener todas las categorías 
    @GetMapping()
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        return ResponseEntity.ok(repo.obtenerCategorias());
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable int id) {
        Categoria categoria = repo.obtenerCategoriaPorId(id);

        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
             return ResponseEntity
                .status(404)
                .body(Map.of(
                    "mensaje", "La categoría con ID " + id + " no existe o fue desactivada.",
                    "status", 404
                ));
        }
    }

    // Crear nueva categoría
    @PostMapping()
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nuevaCategoria) {

        Categoria categoriaCreada = repo.crearCategoria(nuevaCategoria);
        return ResponseEntity.ok(categoriaCreada);

    }

    // Actualizar categoría 
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable int id, @RequestParam String nombre) {
        Categoria categoriaExistente = repo.obtenerCategoriaPorId(id);
        if (categoriaExistente != null) {
            Categoria categoriaActualizada = repo.actualizarCategoria(id, nombre);
            return ResponseEntity.ok(categoriaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una categoria (Sera un cambio de estado de true a false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable int id) {
        Categoria categoriaExistente = repo.obtenerCategoriaPorId(id);
        if (categoriaExistente != null) {

            boolean actualizado = repo.cambiarEstadoCategoria(id, false);
            if (actualizado) {
                return ResponseEntity.ok("Categoría desactivada (soft delete)");
            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
