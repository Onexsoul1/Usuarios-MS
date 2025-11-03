package cl.tuapp.usuariosms.dto;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;

public record UsuarioCreateDto(
  @NotBlank @Size(max=50) String username,
  @NotBlank @Size(max=120) String password,
  @NotBlank @Size(max=100) String nombre,
  @NotBlank String rolNombre,
  Integer activo
) {}
