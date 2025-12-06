package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import mx.tecnm.backend.api.models.Categoria;

@Repository
public class CategoriaDAO {

    @Autowired
    private JdbcClient jdbcClient;

    public List<Categoria> obtenerCategorias() {
        String sql = "SELECT id, nombre FROM categorias WHERE estado = true";
        return jdbcClient.sql(sql)
                .query(new CategoriaRM())
                .list();
    }

    public Categoria obtenerCategoriaPorId(int id) {
        String sql = "SELECT id, nombre, estado FROM categorias WHERE id = ? AND estado = true";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new CategoriaRM())
                .optional()
                .orElse(null);
    }

    public Categoria crearCategoria(String nuevaCategoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (:nombre) RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("nombre", nuevaCategoria)
                .query(new CategoriaRM())
                .single();
    }

    public Categoria actualizarCategoria(int id, String nombre) {
        String sql = "UPDATE categorias SET nombre = :nombre WHERE id = :id RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", nombre)
                .query(new CategoriaRM())
                .single();
    }

    public boolean cambiarEstadoCategoria(int id, boolean estado) {
        String sql = "UPDATE categorias SET estado = ? WHERE id = ?";
        int filas = jdbcClient.sql(sql)
                .param(estado) // primer ?
                .param(id) // segundo ?
                .update();

        return filas > 0;
    }

}
