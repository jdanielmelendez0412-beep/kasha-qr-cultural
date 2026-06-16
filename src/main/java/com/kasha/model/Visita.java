package com.kasha.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private String monumento;

    @Column(nullable = false)
    private String edad;

    @Column(nullable = false)
    private String procedencia;

    @Column(nullable = false, name = "tipo_visitante")
    private String tipoVisitante;

    public Visita() {}

    public Visita(String monumento, String edad, String procedencia, String tipoVisitante) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.monumento = monumento;
        this.edad = edad;
        this.procedencia = procedencia;
        this.tipoVisitante = tipoVisitante;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMonumento() { return monumento; }
    public void setMonumento(String monumento) { this.monumento = monumento; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public String getProcedencia() { return procedencia; }
    public void setProcedencia(String procedencia) { this.procedencia = procedencia; }

    public String getTipoVisitante() { return tipoVisitante; }
    public void setTipoVisitante(String tipoVisitante) { this.tipoVisitante = tipoVisitante; }
}
