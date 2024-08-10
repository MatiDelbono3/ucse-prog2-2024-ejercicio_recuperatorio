package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name="feriados")
public class feriados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fin_semana")
    private String fin_semana;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_trabajo_id", nullable = false)
    private dias dia;


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


    public String getFin_semana() {
        return fin_semana;
    }

    public void setFin_semana(String fin_semana) {
        this.fin_semana = fin_semana;
    }

    public dias getDia() {
        return dia;
    }

    public void setDia(dias dia) {
        this.dia = dia;
    }
}
