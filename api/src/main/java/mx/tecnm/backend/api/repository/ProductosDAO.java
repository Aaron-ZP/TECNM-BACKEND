package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Productos;

@Repository
public class ProductosDAO {

    @Autowired
    private JdbcClient jdbcClient;

  
    //  OBTENER TODOS LOS PRODUCTOS
    
         public List<Productos> obtenerProductos() {
        String sql = "SELECT id, nombre, precio,sku, color, marca, descripcion,  peso, alto, ancho, profundidad, categorias_id, estado FROM productos WHERE estado = true";
        return jdbcClient.sql(sql)
                .query(new ProductosRM())
                .list();
    }


  


    
    //  OBTENER PRODUCTO POR ID
    
    public Productos obtenerProductoPorId(int id) {
        String sql = """
            SELECT 
                id,
                nombre,
                sku,
                color,
                marca,
                descripcion,
                precio,
                peso,
                alto,
                ancho,
                profundidad,
                categorias_id AS categoriasId,
                estado
            FROM productos
            WHERE id = :id
        """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Productos.class)
                .single();
    }

    // ==========================================================
    //  CREAR PRODUCTO
    // ==========================================================
    public int crearProducto(Productos p) {
        String sql = """
            INSERT INTO productos
                (nombre, sku, color, marca, descripcion, precio,
                 peso, alto, ancho, profundidad, categorias_id, estado)
            VALUES
                (:nombre, :sku, :color, :marca, :descripcion, :precio,
                 :peso, :alto, :ancho, :profundidad, :categoriasId, :estado)
            RETURNING id
        """;

        return jdbcClient.sql(sql)
                .param("nombre", p.nombre())
                .param("sku", p.sku())
                .param("color", p.color())
                .param("marca", p.marca())
                .param("descripcion", p.descripcion())
                .param("precio", p.precio())
                .param("peso", p.peso())
                .param("alto", p.alto())
                .param("ancho", p.ancho())
                .param("profundidad", p.profundidad())
                .param("categoriasId", p.categoriasId())
                .param("estado", p.estado())
                .query(Integer.class)
                .single();
    }

    // ==========================================================
    //  ACTUALIZAR PRODUCTO
    // ==========================================================
    public int actualizarProducto(int id, Productos p) {
        String sql = """
            UPDATE productos SET
                nombre = :nombre,
                sku = :sku,
                color = :color,
                marca = :marca,
                descripcion = :descripcion,
                precio = :precio,
                peso = :peso,
                alto = :alto,
                ancho = :ancho,
                profundidad = :profundidad,
                categorias_id = :categoriasId,
                estado = :estado
            WHERE id = :id
        """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", p.nombre())
                .param("sku", p.sku())
                .param("color", p.color())
                .param("marca", p.marca())
                .param("descripcion", p.descripcion())
                .param("precio", p.precio())
                .param("peso", p.peso())
                .param("alto", p.alto())
                .param("ancho", p.ancho())
                .param("profundidad", p.profundidad())
                .param("categoriasId", p.categoriasId())
                .param("estado", p.estado())
                .update();
    }

    // ==========================================================
    //  ELIMINAR PRODUCTO
    // ==========================================================
    public int eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = :id";

        return jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }
}


