package org.example;

import org.example.DTO.contador;
import org.example.DTO.dia;
import org.example.DTO.diaFeriado;
import org.example.Services.Serviciodia;
import org.example.Services.Servicioferiado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Serviciodia diaService = new Serviciodia();
        Servicioferiado feriadoServices = new Servicioferiado();
        Scanner scanner = new Scanner(System.in);
        diaFeriado diaferiado = new diaFeriado();
        diaFeriado fechainicio = new diaFeriado();
        diaFeriado fechaFin = new diaFeriado();
        int anio = 0;

        int opcion;
        do {
            System.out.println("Opcion 1, crear nuevo feriado");
            System.out.println("Opcion 2, verificar si un dia es feriado");
            System.out.println("Opcion 3, obtener los días laborales entre 2 fechas");
            System.out.println("Opcion 4, verificar los feriados por mes");
            System.out.println("Opcion 5, obtener el promedio de dias laborales por mes");
            System.out.println("Leer opcion");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // METODO 1:
                    boolean DarDeAltaFeriados = feriadoServices.darDeAltaFeriado(diaferiado);
                case 2:
                    // METODO 2:
                    boolean EsFeriado = feriadoServices.esFeriado(diaferiado);
                case 3:
                    List<dia> ObtenerLaborales = diaService.obtenerDiasLaborales(fechainicio, fechaFin);
                    ObtenerLaborales.forEach(laboral -> System.out.println(laboral.getId() + laboral.getDia() + laboral.getMes() + laboral.getAnio()));
                case 4:
                    List<contador> ObtenerFeriadosPorMes = feriadoServices.obtenerCantidadFeriadosPorMes(anio);
                    ObtenerFeriadosPorMes.forEach(feriado -> System.out.println(feriado.getMes() + feriado.getCantidad()));
                case 5:
                    double PromedioLaboralesEnUnMes = diaService.obtenerPromedioDiasLaborales(anio);
            }


        } while (opcion != -1);
    }
}