package org.example.DTO;

public class dia {
    private int id;
    private int dia;
    private int mes;
    private int anio;
    public dia (int id, int dia, int mes, int anio){
        this.setId(id);
        this.setDia(dia);
        this.setMes(mes);
        this.setAnio(anio);
    }

    public dia(String string, boolean b) {
    }

    public dia(int dayOfMonth, int monthValue, int year, boolean b) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
