package mx.tecnm.backend.api.models;

import java.math.BigDecimal;

public record Productos(
        int id,
        String nombre,
        BigDecimal precio,
        String sku,
        String color,
        String marca,
        String descripcion,
        BigDecimal peso,
        BigDecimal alto,
        BigDecimal ancho,
        BigDecimal profundidad,
        int categoriasId,
        boolean estado
) {

   
    
}
