package mx.tecnm.backend.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


}
