package com.detodo.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha_prestamo;
    private LocalDateTime fecha_devolucion;
    private String estado;

    @ManyToOne
    private UserSec unUserSec;

    @ManyToOne
    private Libro unLibro;

}
