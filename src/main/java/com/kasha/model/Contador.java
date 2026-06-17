package com.kasha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contador")
public class Contador {

    @Id
    private Long id;

    @Column(nullable = false)
    private long totalVisitas;

    public Contador() {}

    public Contador(Long id, long totalVisitas) {
        this.id = id;
        this.totalVisitas = totalVisitas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getTotalVisitas() { return totalVisitas; }
    public void setTotalVisitas(long totalVisitas) { this.totalVisitas = totalVisitas; }
}
