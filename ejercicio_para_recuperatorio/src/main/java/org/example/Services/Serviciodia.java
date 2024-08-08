package org.example.Services;
import org.example.entities.*;
import jakarta.persistence.Query;
import org.example.connections.*;
import org.example.DTO.dia;
import org.example.DTO.diaFeriado;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Serviciodia {
    ;public List<dia>Obtenerdias=new ArrayList<>();
    public List<dia> obtenerDiasLaborales(diaFeriado fechaInicio, diaFeriado fechaFin) {
        Session session = HibernateUtil.getSession();

        try {
            LocalDate startDate = LocalDate.of(fechaInicio.getAnio(), fechaInicio.getMes(), fechaInicio.getDia());
            LocalDate endDate = LocalDate.of(fechaFin.getAnio(), fechaFin.getMes(), fechaFin.getDia());

            // Generar todas las fechas entre las dos fechas dadas
            while (!startDate.isAfter(endDate)) {
                boolean esLaboral = true;

                // Verificar si es fin de semana
                if (startDate.getDayOfWeek().getValue() ==6|| startDate.getDayOfWeek().getValue() == 7) {
                    esLaboral = false;
                }

                // Verificar si es feriado
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.dia_trabajo_id = :dia";
                Query queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("fecha", startDate.toString());
                Long count = (Long) queryFeriado.getSingleResult();

                if (count > 0) {
                    esLaboral = false;
                }

                // Si es un día laboral, agregarlo a la lista
                if (esLaboral) {
                    Obtenerdias.add(new dia(startDate.toString(), true));
                }

                startDate = startDate.plusDays(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return Obtenerdias;
    }
    public double obtenerPromedioDiasLaborales( int anio) {
        double promedio = 0.0;
        Session session = HibernateUtil.getSession();

        try {
            // Consulta para obtener el total de días en el mes y los días laborales
            String hql = "WITH DiasDelMes AS (" +
                    "    SELECT d.dia, d.mes, d.anio, d.fin_semana, " +
                    "           CASE WHEN f.id IS NOT NULL THEN 1 ELSE 0 END AS esFeriado " +
                    "    FROM dias d " +  // Ajustar el nombre de la entidad según tu configuración
                    "    LEFT JOIN feriados f ON d.id = f.dia_Trabajo_Id " +  // Ajustar el nombre de la entidad y propiedad
                    "    WHERE d.mes = :mes AND d.anio = :año " +
                    "), " +
                    "Resumen AS (" +
                    "    SELECT COUNT(*) AS totalDias, " +
                    "           SUM(CASE WHEN fin_semana = 1 OR esFeriado = 1 THEN 0 ELSE 1 END) AS diasLaborales " +
                    "    FROM DiasDelMes " +
                    ") " +
                    "SELECT CAST(diasLaborales AS DECIMAL) / totalDias AS promedio " +
                    "FROM Resumen";

            org.hibernate.query.Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("año", anio);
            List<Double> results = query.list();

            if (!results.isEmpty()) {
                promedio = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return promedio;
    }
}
