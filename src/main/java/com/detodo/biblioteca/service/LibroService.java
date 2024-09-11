package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.Libro;
import com.detodo.biblioteca.model.Reserva;
import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.repository.ILibroRepository;
import com.detodo.biblioteca.repository.IReservaRepository;
import com.detodo.biblioteca.repository.IUserSecRepository;
import com.detodo.biblioteca.service.iservice.ILibroService;
import com.detodo.biblioteca.service.iservice.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService implements ILibroService {

    @Autowired
    private ILibroRepository iLibRepo;


    @Autowired
    private IReservaRepository iResRepo;

    @Autowired
    private IUserSecRepository iUserRepo;

    @Override
    public List<Libro> findAll() {
        return iLibRepo.findAll();
    }

    @Override
    public Libro save(Libro libro) {
        return iLibRepo.save(libro);
    }

    @Override
    public String deleteById(Long id) {
        iLibRepo.deleteById(id);
        return "delete success";
    }

    @Override
    public Optional<Libro> findById(Long id) {
        return iLibRepo.findById(id);
    }

    @Override
    public Libro update(Libro libro) {
        return iLibRepo.save(libro);
    }

    @Override
    public List<Libro> search(String anyThing) {

        List<Libro> libroList = this.findAll();
        List<Libro> libros = new ArrayList<>();

        for (Libro libro : libroList) {

            if (libro.getGenero().equalsIgnoreCase(anyThing) || libro.getTitulo().equalsIgnoreCase(anyThing) || libro.getTitulo().contains(anyThing) || libro.getAutor().contains(anyThing) || libro.getGenero().contains(anyThing) || libro.getDescripcion().contains(anyThing) || libro.getAutor().equalsIgnoreCase(anyThing)) {

                libros.add(libro);

            }
        }
        return libros;

    }

    @Override
    public List<Reserva> seeLHistoReservados() {

        List<Reserva> newReservaList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<UserSec> userSec = iUserRepo.findUserEntityByUsername(username);

        if (userSec.isPresent()) {
            UserSec userSec1 = userSec.get();
            Long idUser = userSec1.getId();

            List<Reserva> reservaList = iResRepo.findAll();

            for (Reserva reserva : reservaList) {

                if (reserva.getUnUserSec().getId().equals(idUser)) {
                    newReservaList.add(reserva);
                }

            }
        }

        return newReservaList;
    }
}
