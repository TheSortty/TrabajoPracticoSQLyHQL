package org.example;

import javax.persistence.*;

@Entity
public class Componente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String nroSerie;

    @ManyToOne
    @JoinColumn(name = "computadora_id")
    private Computadora computadora;

    public Componente(String nombre, String nroSerie) {
        this.nombre = nombre;
        this.nroSerie = nroSerie;
    }

    public Componente() {

    }

    // Getters y setters
    public Computadora getComputadora() {
        return computadora;
    }

    public void setComputadora(Computadora computadora) {
        this.computadora = computadora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }
}
