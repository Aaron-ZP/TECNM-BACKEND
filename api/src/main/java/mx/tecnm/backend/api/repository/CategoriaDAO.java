package mx.tecnm.backend.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public class CategoriaDAO {
    
    private JdbcClient jdbcClient;

    public List<Categoria> obtenerCategorias() {
        String sql = "SELECT id, nombre FROM categorias";
        return jdbcClient.sql(sql1)
                .query(new CategoriaRM())
                .list();
    }

}
