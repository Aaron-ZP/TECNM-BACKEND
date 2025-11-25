package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


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

}
