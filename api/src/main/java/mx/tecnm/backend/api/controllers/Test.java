package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/hello")
    public String helloword(){
        return "Hola API Rest";
    }

    @GetMapping("/producto")
    public Producto getProducto(){
        Producto producto = new Producto();
        producto.nombreProducto = "Yoghurt Danone fresa";
        producto.codigoBarras = "7506443108042";
        producto.precio = 12.00;
        return producto;
    }

    @GetMapping("/productos")
    public Producto[] getProductos(){
        Producto producto1 = new Producto();
        producto1.nombreProducto = "Yogurt Danone Fresa";
        producto1.codigoBarras = "7588443100043";
        producto1.precio = 12.0;

        Producto producto2 = new Producto();
        producto2.nombreProducto = "Leche Lala 1L";
        producto2.codigoBarras = "7501003116116";
        producto2.precio = 22.50;

        Producto producto3 = new Producto();
        producto3.nombreProducto = "Queso Manchego 200g";
        producto3.codigoBarras = "7501031312345";
        producto3.precio = 45.75;

        Producto productos[] = {producto1, producto2, producto3};
        return productos;

    }

   
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductosByid(@PathVariable int id){
         if(id < 0 || id > 2){
            return ResponseEntity.notFound().build();
         }

    Producto producto1 = new Producto();
    producto1.nombreProducto = "Yogurt Danone Fresa";
    producto1.codigoBarras = "7588443100043";
    producto1.precio = 12.0;

    Producto producto2 = new Producto();
    producto2.nombreProducto = "Leche Lala 1L";
    producto2.codigoBarras = "7501003116116";
    producto2.precio = 22.50;

    Producto producto3 = new Producto();
    producto3.nombreProducto = "Queso Manchego 200g";
    producto3.codigoBarras = "7501031312345";
    producto3.precio = 45.75;

    Producto[] productos = {producto1, producto2, producto3};
    return ResponseEntity.ok(productos[id]);
    }

}