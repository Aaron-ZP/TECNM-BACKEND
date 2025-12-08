package mx.tecnm.backend.api.api.controllers;

import mx.tecnm.backend.api.api.modells.Usuario;
import mx.tecnm.backend.api.api.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO repo;

    // LISTAR
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(repo.obtenerUsuarios());
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable int id) {
        Optional<Usuario> usuario = repo.obtenerUsuarioPorId(id);

        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREAR
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> creado = repo.crearUsuario(usuario);

        return creado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable int id,
                                              @RequestBody Usuario usuario) {
        Optional<Usuario> actualizado = repo.actualizarUsuario(id, usuario);

        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ELIMINAR (SOFT DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        Optional<Usuario> eliminado = repo.eliminarUsuario(id);

        return eliminado.isPresent()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
