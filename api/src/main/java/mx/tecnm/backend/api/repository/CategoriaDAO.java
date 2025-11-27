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
        String sql = "SELECT id, nombre FROM categorias";
        return jdbcClient.sql(sql)
                .query(new CategoriaRM())
                .list();
    }
    public Categoria obtenerCategoriaPorId(int id) {
        String sql = "SELECT id, nombre FROM categorias WHERE id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new CategoriaRM())
                .single();
    }
    public Categoria crearCategoria(String nuevaCategoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?) RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("nombre", nuevaCategoria)
                .query(new CategoriaRM())
                .single();       
    }

}
