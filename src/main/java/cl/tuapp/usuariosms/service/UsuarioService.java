package cl.tuapp.usuariosms.service;

import cl.tuapp.usuariosms.dto.*; import cl.tuapp.usuariosms.entity.*; 
import cl.tuapp.usuariosms.exception.NotFoundException;
import cl.tuapp.usuariosms.repository.*; import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; import java.util.List;

@Service
public class UsuarioService {
  private final UsuarioRepository usuarioRepo; private final RolRepository rolRepo;
  public UsuarioService(UsuarioRepository u, RolRepository r){this.usuarioRepo=u; this.rolRepo=r;}

  private UsuarioResponseDto toDto(Usuario u){
    return new UsuarioResponseDto(u.getId(), u.getUsername(), u.getNombre(),
      u.getRol()!=null?u.getRol().getNombre():null, u.getActivo());
  }

  @Transactional
  public UsuarioResponseDto crear(UsuarioCreateDto dto){
    if (usuarioRepo.existsByUsername(dto.username())) throw new IllegalArgumentException("username ya existe");
    Rol rol = rolRepo.findByNombre(dto.rolNombre()).orElseThrow(()->new NotFoundException("Rol no encontrado: "+dto.rolNombre()));
    Usuario u = new Usuario();
    u.setUsername(dto.username()); u.setPassword(dto.password()); u.setNombre(dto.nombre());
    u.setRol(rol); u.setActivo(dto.activo()==null?1:dto.activo());
    return toDto(usuarioRepo.save(u));
  }

  @Transactional(readOnly=true) public List<UsuarioResponseDto> listar(){ return usuarioRepo.findAll().stream().map(this::toDto).toList(); }
  @Transactional(readOnly=true) public UsuarioResponseDto obtener(Long id){ return toDto(usuarioRepo.findById(id).orElseThrow(()->new NotFoundException("Usuario no encontrado"))); }

  @Transactional
  public UsuarioResponseDto actualizar(Long id, UsuarioCreateDto dto){
    Usuario u = usuarioRepo.findById(id).orElseThrow(()->new NotFoundException("Usuario no encontrado"));
    Rol rol = rolRepo.findByNombre(dto.rolNombre()).orElseThrow(()->new NotFoundException("Rol no encontrado: "+dto.rolNombre()));
    u.setUsername(dto.username()); u.setPassword(dto.password()); u.setNombre(dto.nombre());
    u.setRol(rol); if (dto.activo()!=null) u.setActivo(dto.activo());
    return toDto(usuarioRepo.save(u));
  }

  @Transactional public void eliminar(Long id){
    if (!usuarioRepo.existsById(id)) throw new NotFoundException("Usuario no encontrado");
    usuarioRepo.deleteById(id);
  }

  @Transactional(readOnly=true)
  public LoginResponse login(LoginRequest req){
    var u = usuarioRepo.findByUsername(req.username()).orElseThrow(()->new NotFoundException("Credenciales inválidas"));
    if (!u.getPassword().equals(req.password()) || u.getActivo()==0) throw new NotFoundException("Credenciales inválidas");
    return new LoginResponse(true, u.getUsername(), u.getNombre(), u.getRol()!=null?u.getRol().getNombre():null);
  }
}
