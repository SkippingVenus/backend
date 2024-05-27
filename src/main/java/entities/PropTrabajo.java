package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "proptrabajos")
public class PropTrabajo {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  // Definir Aqui cardinalidad
  @ManyToOne
  @JoinColumn(
      name = "id_freelancer",
      nullable = false
  )
  private Freelancer freelancer;

  @ManyToOne
  @JoinColumn(
      name = "id_trabajo",
      nullable = false
  )
  private Trabajo trabajo;

  private String descripcion;

  private float monto;

  private String estado;

  private Date fecha;

  public Freelancer getFreelancer() {
    return freelancer;
  }

  public void setFreelancer(Freelancer freelancer) {
    this.freelancer = freelancer;
  }

  public Trabajo getTrabajo() {
    return trabajo;
  }

  public void setTrabajo(Trabajo trabajo) {
    this.trabajo = trabajo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public float getMonto() {
    return monto;
  }

  public void setMonto(float monto) {
    this.monto = monto;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
}
