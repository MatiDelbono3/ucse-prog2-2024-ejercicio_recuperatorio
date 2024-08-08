package org.example.Services;
import org.example.connections.*;
import jakarta.persistence.Query;
import org.example.DTO.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.*;

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
            String hqlDia = "SELECT d.id FROM dias d WHERE d.dia = :dia AND d.mes = :mes AND d.anio = :anio";
            Query queryDia = session.createQuery(hqlDia, Integer.class);
            queryDia.setParameter("dia", diaFeriado.getDia());
            queryDia.setParameter("mes", diaFeriado.getMes());
            queryDia.setParameter("año", diaFeriado.getAnio());
            Integer idDia = (Integer) queryDia.getSingleResult();

            if (idDia != null) {
                // Verificar si ya existe un registro en la tabla de feriados para ese ID de día
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.idDia = :id";
                org.hibernate.query.Query<Long> queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("idDia", idDia);
                Long count = queryFeriado.uniqueResult();

                if (count == 0) {
                    // Insertar el nuevo feriado
                    feriado nuevoFeriado = new feriado(idDia);
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
    // Ver si un dia es feriado
    public boolean esFeriado(diaFeriado diaFeriado) {
        Session session = HibernateUtil.getSession();
        boolean esFeriado = false;

        try {
            // Buscar el ID del día en la tabla `Dias`
            String hqlDia = "SELECT d.id FROM dias d WHERE d.dia = :dia AND d.mes = :mes AND d.anio = :anio";
            Query queryDia = session.createQuery(hqlDia, Integer.class);
            queryDia.setParameter("dia", diaFeriado.getDia());
            queryDia.setParameter("mes", diaFeriado.getMes());
            queryDia.setParameter("año", diaFeriado.getAnio());
            Integer idDia = (Integer) queryDia.getSingleResult();

            if (idDia != null) {
                // Verificar si existe un registro en la tabla de feriados para ese ID de día
                String hqlFeriado = "SELECT COUNT(f) FROM feriados f WHERE f.idDia = :id";
                Query queryFeriado = session.createQuery(hqlFeriado, Long.class);
                queryFeriado.setParameter("idDia", idDia);
                Long count = (Long) queryFeriado.getSingleResult();

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
    //Cantidad feriados en 1 mes
    public List<contador> obtenerCantidadFeriadosPorMes(int anio) {
        List<contador> contadores = new ArrayList<>();
        Session session = HibernateUtil.getSession();

        try {
            String hql = "SELECT \n" +
                    "    d.mes AS mes,\n" +
                    "    COUNT(f.id) AS cantidad\n" +
                    "FROM \n" +
                    "    dias d\n" +
                    "JOIN \n" +
                    "    feriados f ON d.id = f.dia_trabajo_id\n" +
                    "WHERE \n" +
                    "    d.anio = :anio\n" +
                    "GROUP BY \n" +
                    "    d.mes\n" +
                    "ORDER BY \n" +
                    "    cantidad DESC";

            Query query = session.createQuery(hql, Object[].class);
            query.setParameter("año", anio);
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
