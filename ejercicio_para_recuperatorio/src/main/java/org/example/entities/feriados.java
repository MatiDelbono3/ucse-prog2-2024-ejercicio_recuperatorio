package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="feriados")
public class feriados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "dia_trabajo_id")
    private String dia_trabajo_id;
    @Column(name = "fin_semana")
    private String fin_semana;
    @OneToMany(mappedBy = "feriado", fetch = FetchType.LAZY)
    private List<dias> dias;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDia_trabajo_id() {
        return dia_trabajo_id;
    }

    public void setDia_trabajo_id(String dia_trabajo_id) {
        this.dia_trabajo_id = dia_trabajo_id;
    }

    public String getFin_semana() {
        return fin_semana;
    }

    public void setFin_semana(String fin_semana) {
        this.fin_semana = fin_semana;
    }

    public List<org.example.entities.dias> getDias() {
        return dias;
    }

    public void setDias(List<org.example.entities.dias> dias) {
        this.dias = dias;
    }
}
