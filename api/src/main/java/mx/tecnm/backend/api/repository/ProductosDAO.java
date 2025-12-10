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
        String sql = "SELECT id, nombre, precio, sku, color, marca, descripcion,  peso, alto, ancho, profundidad, categorias_id, estado FROM productos WHERE id = ? AND estado = true";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new ProductosRM())
                .optional()
                .orElse(null);
    }

    //  CREAR PRODUCTO
    public Productos crearProducto(Productos nuevoP) {
        String sql = "INSERT INTO productos (nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad, categorias_id, estado) VALUES (:nombre, :precio, :sku, :color, :marca, :descripcion, :peso, :alto, :ancho, :profundidad, :categorias_id, true) RETURNING id, nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad, categorias_id, estado";
        return jdbcClient.sql(sql)
                .param("nombre", nuevoP.nombre())
                .param("precio", nuevoP.precio())
                .param("sku", nuevoP.sku())
                .param("color", nuevoP.color())
                .param("marca", nuevoP.marca())
                .param("descripcion", nuevoP.descripcion())
                .param("peso", nuevoP.peso())
                .param("alto", nuevoP.alto())
                .param("ancho", nuevoP.ancho())
                .param("profundidad", nuevoP.profundidad())
                .param("categorias_id", nuevoP.categoriasId())
                .query(new ProductosRM())
                .single();
    }

    //  ACTUALIZAR PRODUCTO
    public Productos actualizarProducto(int id, Productos p) {
        String sql = "UPDATE productos SET nombre = :nombre, precio = :precio, sku = :sku, color = :color, marca = :marca, descripcion = :descripcion, peso = :peso, alto = :alto, ancho = :ancho, profundidad = :profundidad, categorias_id = :categorias_id, estado = :estado  WHERE id = :id RETURNING id, nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad, categorias_id, estado";

        return jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", p.nombre())
                .param("precio", p.precio())
                .param("sku", p.sku())
                .param("color", p.color())
                .param("marca", p.marca())
                .param("descripcion", p.descripcion())
                .param("peso", p.peso())
                .param("alto", p.alto())
                .param("ancho", p.ancho())
                .param("profundidad", p.profundidad())
                .param("categorias_id", p.categoriasId())
                .param("estado", p.estado())
                .query(new ProductosRM())
                .single();
    }

    //  ELIMINAR PRODUCTO
    public boolean cambiarEstadoProductos(int id, boolean estado) {
        String sql = "UPDATE productos SET estado = ? WHERE id = ?";
        int filas = jdbcClient.sql(sql)
                .param(estado) // primer ?
                .param(id) // segundo ?
                .update();

        return filas > 0;
    }
}
