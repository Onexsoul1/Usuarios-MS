package cl.tuapp.usuariosms.controller;

import cl.tuapp.usuariosms.dto.*; import cl.tuapp.usuariosms.service.UsuarioService;
import jakarta.validation.Valid; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api")
public class UsuarioController {
  private final UsuarioService service; public UsuarioController(UsuarioService s){this.service=s;}

  @PostMapping("/usuarios") public ResponseEntity<UsuarioResponseDto> crear(@Valid @RequestBody UsuarioCreateDto dto){
    var res = service.crear(dto); return ResponseEntity.created(URI.create("/api/usuarios/"+res.id())).body(res);
  }
  @GetMapping("/usuarios") public ResponseEntity<?> listar(){ return ResponseEntity.ok(service.listar()); }
  @GetMapping("/usuarios/{id}") public ResponseEntity<?> obtener(@PathVariable Long id){ return ResponseEntity.ok(service.obtener(id)); }
  @PutMapping("/usuarios/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDto dto){
    return ResponseEntity.ok(service.actualizar(id, dto));
  }
  @DeleteMapping("/usuarios/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id){ service.eliminar(id); return ResponseEntity.noContent().build(); }
  @PostMapping("/auth/login") public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req){ return ResponseEntity.ok(service.login(req)); }
}
