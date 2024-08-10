package org.example.Services;

import org.example.DTO.feriado;
import org.example.entities.dias;
import org.example.entities.feriados;
import org.example.connections.*;
import org.example.DTO.diaFeriado;
import org.example.DTO.contador;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Servicioferiado {
    public boolean darDeAltaFeriado(diaFeriado diaFeriado) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        boolean insertado = false;

        try {
            transaction = session.beginTransaction();

            // Buscar el ID del día en la tabla `Dias`
            String hqlDia = "SELECT d.Id FROM dias d WHERE d.dia = :dia AND d.mes = :mes AND d.anio = :anio";
            Query<Integer> queryDia = session.createQuery(hqlDia, Integer.class);
            queryDia.setParameter("dia", diaFeriado.getDia());
            queryDia.setParameter("mes", diaFeriado.getMes());
            queryDia.setParameter("anio", diaFeriado.getAnio());
            Integer idDia = queryDia.getSingleResult();

            if (idDia != null) {
                // Verificar si ya existe un registro en la tabla de feriados para ese ID de día
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.dia.id = :idDia";
                Query<Long> queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("idDia", idDia);
                Long count = queryFeriado.getSingleResult();

                if (count == 0) {
                    // Insertar el nuevo feriado
                    feriado nuevoFeriado = new feriado(idDia);
                    nuevoFeriado.setId((Integer) session.get(String.valueOf(feriados.class), idDia));
                    session.save(nuevoFeriado);
                    insertado = true;
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return insertado;
    }

    // Ver si un día es feriado
    public boolean esFeriado(diaFeriado diaFeriado) {
        Session session = HibernateUtil.getSession();
        boolean esFeriado = false;

        try {
            // Buscar el ID del día en la tabla `Dias`
            String hqlDia = "SELECT d.Id FROM dias d WHERE d.dia = :dia AND d.mes = :mes AND d.anio = :anio";
            Query<Integer> queryDia = session.createQuery(hqlDia, Integer.class);
            queryDia.setParameter("dia", diaFeriado.getDia());
            queryDia.setParameter("mes", diaFeriado.getMes());
            queryDia.setParameter("anio", diaFeriado.getAnio());
            Integer idDia = queryDia.getSingleResult();

            if (idDia != null) {
                // Verificar si existe un registro en la tabla de feriados para ese ID de día
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.dia.id = :idDia";
                Query<Long> queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("idDia", idDia);
                Long count = queryFeriado.getSingleResult();

                // Si count es mayor que 0, significa que el día es feriado
                esFeriado = (count > 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return esFeriado;
    }

    // Cantidad de feriados en 1 mes
    public List<contador> obtenerCantidadFeriadosPorMes(int anio) {
        List<contador> contadores = new ArrayList<>();
        Session session = HibernateUtil.getSession();

        try {
            String hql = "SELECT d.mes AS mes, COUNT(f.id) AS cantidad " +
                    "FROM dias d LEFT JOIN feriados f ON d.Id = f.dia.id " +
                    "WHERE d.anio = :anio " +
                    "GROUP BY d.mes " +
                    "ORDER BY cantidad DESC";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("anio", anio);
            List<Object[]> results = query.getResultList();

            for (Object[] result : results) {
                int mes = ((Number) result[0]).intValue();
                int cantidad = ((Number) result[1]).intValue();
                contadores.add(new contador(mes, cantidad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return contadores;
    }
}