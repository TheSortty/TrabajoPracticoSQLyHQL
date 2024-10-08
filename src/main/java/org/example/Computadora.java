package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Computadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String marca;
    private String modelo;

    @OneToMany(mappedBy = "computadora", cascade = CascadeType.ALL)
    private List<Componente> componentes;

    public Computadora() {

    }

    public Computadora(String codigo, String marca, String modelo) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.componentes = new ArrayList<>();
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void agregarComponente(Componente componente) {
        this.componentes.add(componente);
    }
}