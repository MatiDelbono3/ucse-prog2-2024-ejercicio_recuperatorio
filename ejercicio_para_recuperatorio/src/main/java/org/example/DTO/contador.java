package org.example.DTO;

public class contador {
    private int mes;
    private int cantidad;
    public contador(int mes, int cantidad){
        this.setMes(mes);
        this.setCantidad(cantidad);
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
