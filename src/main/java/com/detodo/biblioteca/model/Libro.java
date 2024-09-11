package com.detodo.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private String genero;
    private String descripcion;
    private int cant_disponibles;

    @OneToMany(mappedBy = "unLibro")
    @JsonIgnore
    private List<Reserva> reservaList;

    @OneToMany(mappedBy = "unLibro")
    @JsonIgnore
    private List<Prestamo> prestamoList;

}
