package org.example.Services;
import org.example.connections.*;
import org.example.entities.dias;
import org.example.entities.feriados;
import org.example.DTO.dia;
import org.example.DTO.diaFeriado;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Serviciodia {
    private List<dia> Obtenerdias = new ArrayList<>();

    public List<dia> obtenerDiasLaborales(diaFeriado fechaInicio, diaFeriado fechaFin) {
        Session session = HibernateUtil.getSession();

        try {
            LocalDate startDate = LocalDate.of(fechaInicio.getAnio(), fechaInicio.getMes(), fechaInicio.getDia());
            LocalDate endDate = LocalDate.of(fechaFin.getAnio(), fechaFin.getMes(), fechaFin.getDia());

            while (!startDate.isAfter(endDate)) {
                boolean esLaboral = true;

                // Verificar si es fin de semana
                if (startDate.getDayOfWeek().getValue() == 6 || startDate.getDayOfWeek().getValue() == 7) {
                    esLaboral = false;
                }

                // Verificar si es feriado
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.dia_trabajo.id = :diaId";
                Query<Long> queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("diaId", startDate.toString());
                Long count = queryFeriado.getSingleResult();

                if (count > 0) {
                    esLaboral = false;
                }

                // Si es un día laboral, agregarlo a la lista
                if (esLaboral) {
                    Obtenerdias.add(new dia(startDate.getDayOfMonth(), startDate.getMonthValue(), startDate.getYear(), true));
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

    public double obtenerPromedioDiasLaborales(int anio) {
        double promedio = 0.0;
        Session session = HibernateUtil.getSession();

        try {
            // Consulta para obtener el total de días laborales en un año
            String hql = "SELECT\n" +
                    "    (COUNT(CASE WHEN d.dia IS NOT NULL AND (d.finSemana = false OR d.finSemana IS NULL) AND (f.id IS NULL) THEN 1 ELSE NULL END) * 100.0 /\n" +
                    "    NULLIF(COUNT(d), 0)) AS porcentaje\n" +
                    "FROM dias d\n" +
                    "LEFT JOIN feriados f ON d.id = f.dia.id\n" +
                    "WHERE d.anio = :anio";

            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("anio", anio);
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