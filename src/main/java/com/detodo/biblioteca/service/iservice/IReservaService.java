package com.detodo.biblioteca.service.iservice;

import com.detodo.biblioteca.model.Prestamo;
import com.detodo.biblioteca.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface IReservaService {

    public List<Reserva> findAll();

    public Object save(Reserva reserva);

    public String deleteById(Long id);

    public Optional<Reserva> findById(Long id);

    public Reserva update(Reserva reserva);

    public String libroReTerminado(Long id_reserva);

    public String limpiarCache();

    public List<Reserva> getOnlyReservado();

    public List<Reserva> getOnlyTerminado();

}
