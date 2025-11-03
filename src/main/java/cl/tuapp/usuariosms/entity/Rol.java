package cl.tuapp.usuariosms.entity;

import jakarta.persistence.*;

@Entity @Table(name="ROL", schema="LABAPP")
public class Rol {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_ROL") private Long id;
  @Column(name="NOMBRE", nullable=false, unique=true, length=50) private String nombre;

  public Long getId() { return id; } public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre = nombre; }
}
