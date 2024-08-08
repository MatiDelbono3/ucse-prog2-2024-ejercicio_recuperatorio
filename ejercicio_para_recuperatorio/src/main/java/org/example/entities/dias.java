package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="dias")
public class dias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "dia")
    private int dia;
    @Column(name = "mes")
    private int mes;
    @Column(name = "anio")
    private int anio;
    @OneToMany(mappedBy = "feriado", fetch = FetchType.LAZY)
    private List<dias> dias;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<org.example.entities.dias> getDias() {
        return dias;
    }

    public void setDias(List<org.example.entities.dias> dias) {
        this.dias = dias;
    }
}
