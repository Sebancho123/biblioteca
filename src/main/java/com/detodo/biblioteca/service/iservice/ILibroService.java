package com.detodo.biblioteca.service.iservice;

import com.detodo.biblioteca.model.Libro;
import com.detodo.biblioteca.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ILibroService {

    public List<Libro> findAll();

    public Libro save(Libro libro);

    public String deleteById(Long id);

    public Optional<Libro> findById(Long id);

    public Libro update(Libro libro);

    public List<Libro> search(String anyThing);

    public List<Reserva> seeLHistoReservados();
}
