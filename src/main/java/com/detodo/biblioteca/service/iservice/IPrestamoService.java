package com.detodo.biblioteca.service.iservice;

import com.detodo.biblioteca.model.Prestamo;

import java.util.List;
import java.util.Optional;

public interface IPrestamoService {

    public List<Prestamo> findAll();

    public Object save(Prestamo prestamo);

    public String deleteById(Long id);

    public Optional<Prestamo> findById(Long id);

    public Prestamo update(Prestamo prestamo);

    public List<Prestamo> seeHistoLPrest();

    public String libroPresAcabado(Long id_prestamo);

    public String cleanCache();

    public List<Prestamo> getOnlyPrestados();

    public List<Prestamo> getOnlyDevueltos();

}
