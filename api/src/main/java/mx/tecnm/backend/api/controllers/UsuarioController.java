package mx.tecnm.backend.api.api.controllers;

import mx.tecnm.backend.api.api.models.Usuario;
import mx.tecnm.backend.api.api.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO repo;

    // ✔ Obtener solo usuarios activos
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(repo.obtenerUsuariosActivos());
    }

    // ✔ Obtener usuario por ID (solo si está activo)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario u = repo.obtenerUsuarioPorId(id);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    // ✔ Crear usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(repo.crearUsuario(usuario));
    }

    // ✔ Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario actualizado = repo.actualizarUsuario(new Usuario(
                id,
                usuario.nombre(),
                usuario.email(),
                usuario.telefono(),
                usuario.sexo(),
                usuario.fecha_nacimiento(),
                usuario.contrasena(),
                usuario.fecha_registro(),
                true //Siempre debe mantenerse activo al actualizar
        ));
        return ResponseEntity.ok(actualizado);
    }

    // 🔥 BORRADO LÓGICO (NO elimina de la base, solo inhabilita)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        boolean eliminado = repo.eliminarUsuarioLogico(id);
        return eliminado ?
                ResponseEntity.ok("Usuario desactivado correctamente") :
                ResponseEntity.notFound().build();
    }

    // 🔍 adicional: ver TODOS (activos + eliminados)
    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(repo.obtenerTodos());
    }
}
