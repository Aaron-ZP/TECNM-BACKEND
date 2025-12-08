package mx.tecnm.backend.api.controllers;

import mx.tecnm.backend.api.models.Usuario;
import mx.tecnm.backend.api.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO repo;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(repo.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario u = repo.obtenerUsuarioPorId(id);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(repo.crearUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {

        // No enviar fecha_registro ni activo desde el cliente
        Usuario actualizado = repo.actualizarUsuario(
                new Usuario(
                        id,
                        usuario.nombre(),
                        usuario.email(),
                        usuario.telefono(),
                        usuario.sexo(),
                        usuario.fecha_nacimiento(),
                        usuario.contrasena(),
                        null,  // fecha_registro se mantiene igual
                        true   // sigue activo
                )
        );

        return (actualizado == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        repo.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
