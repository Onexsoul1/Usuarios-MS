package cl.tuapp.usuariosms.entity;

import jakarta.persistence.*;

@Entity @Table(name="USUARIO", schema="LABAPP")
public class Usuario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_USUARIO") private Long id;

  @Column(name="USERNAME", nullable=false, unique=true, length=50) private String username;
  @Column(name="PASSWORD", nullable=false, length=120) private String password;
  @Column(name="NOMBRE", nullable=false, length=100) private String nombre;
  @Column(name="ACTIVO", nullable=false) private Integer activo = 1;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="ID_ROL", nullable=false)
  private Rol rol;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String v){this.username=v;}
  public String getPassword(){return password;} public void setPassword(String v){this.password=v;}
  public String getNombre(){return nombre;} public void setNombre(String v){this.nombre=v;}
  public Integer getActivo(){return activo;} public void setActivo(Integer v){this.activo=v;}
  public Rol getRol(){return rol;} public void setRol(Rol r){this.rol=r;}
}
