package com.kasha.repository;

import com.kasha.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {

    List<Visita> findAllByOrderByIdDesc();

    @Query("SELECT v.edad AS categoria, COUNT(v) AS cantidad FROM Visita v GROUP BY v.edad ORDER BY COUNT(v) DESC")
    List<Object[]> contarPorEdad();

    @Query("SELECT v.procedencia AS categoria, COUNT(v) AS cantidad FROM Visita v GROUP BY v.procedencia ORDER BY COUNT(v) DESC")
    List<Object[]> contarPorProcedencia();

    @Query("SELECT v.tipoVisitante AS categoria, COUNT(v) AS cantidad FROM Visita v GROUP BY v.tipoVisitante ORDER BY COUNT(v) DESC")
    List<Object[]> contarPorTipoVisitante();

    @Query("SELECT v.fecha AS categoria, COUNT(v) AS cantidad FROM Visita v GROUP BY v.fecha ORDER BY v.fecha")
    List<Object[]> contarPorFecha();

    @Query("SELECT v.edad, v.procedencia, COUNT(v) FROM Visita v GROUP BY v.edad, v.procedencia ORDER BY v.edad, v.procedencia")
    List<Object[]> cruceEdadProcedencia();

    @Query("SELECT v.edad, v.tipoVisitante, COUNT(v) FROM Visita v GROUP BY v.edad, v.tipoVisitante ORDER BY v.edad, v.tipoVisitante")
    List<Object[]> cruceEdadTipo();

    @Query("SELECT v.procedencia, v.tipoVisitante, COUNT(v) FROM Visita v GROUP BY v.procedencia, v.tipoVisitante ORDER BY v.procedencia, v.tipoVisitante")
    List<Object[]> cruceProcedenciaTipo();
}
