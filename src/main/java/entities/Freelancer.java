package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "freelancers")
@PrimaryKeyJoinColumn(name = "id")
public class Freelancer extends Usuario{
  @OneToOne(mappedBy = "freelancer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private Portafolio portafolio;
  private float calificacion;
  private char tipoPriv;
  private int numTrabajos;

  public Portafolio getPortafolio() {
    return portafolio;
  }

  public void setPortafolio(Portafolio portafolio) {
    this.portafolio = portafolio;
  }

  public float getCalificacion() {
    return calificacion;
  }

  public void setCalificacion(float calificacion) {
    this.calificacion = calificacion;
  }

  public char getTipoPriv() {
    return tipoPriv;
  }

  public void setTipoPriv(char tipoPriv) {
    this.tipoPriv = tipoPriv;
  }

  public int getNumTrabajos() {
    return numTrabajos;
  }

  public void setNumTrabajos(int numTrabajos) {
    this.numTrabajos = numTrabajos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Freelancer that = (Freelancer) o;
    return Float.compare(calificacion, that.calificacion) == 0 && tipoPriv == that.tipoPriv && numTrabajos == that.numTrabajos && Objects.equals(portafolio, that.portafolio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), portafolio, calificacion, tipoPriv, numTrabajos);
  }
}
