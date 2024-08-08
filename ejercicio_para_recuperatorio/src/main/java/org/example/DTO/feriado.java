package org.example.DTO;

public class feriado {
    private int id;
    private String descripcion;
    private int dia_trabajo_id;
    private boolean fin_semana;

    public feriado(Integer idDia) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDia_trabajo_id() {
        return dia_trabajo_id;
    }

    public void setDia_trabajo_id(int dia_trabajo_id) {
        this.dia_trabajo_id = dia_trabajo_id;
    }

    public boolean isFin_semana() {
        return fin_semana;
    }

    public void setFin_semana(boolean fin_semana) {
        this.fin_semana = fin_semana;
    }
}
