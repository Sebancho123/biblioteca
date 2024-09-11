package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.Libro;
import com.detodo.biblioteca.model.Prestamo;
import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.repository.IPrestamoRepository;
import com.detodo.biblioteca.repository.IUserSecRepository;
import com.detodo.biblioteca.service.iservice.ILibroService;
import com.detodo.biblioteca.service.iservice.IPrestamoService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService implements IPrestamoService {

    @Autowired
    private IPrestamoRepository iPresRepo;

    @Autowired
    private ILibroService iLiSer;

    @Autowired
    private IUserSecRepository iUserRepo;

    @Override
    public List<Prestamo> findAll() {
        return iPresRepo.findAll();
    }

    @Override
    public Object save(Prestamo prestamo) {

        Libro libro = iLiSer.findById(prestamo.getUnLibro().getId()).orElse(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserSec userSec = iUserRepo.findUserEntityByUsername(username).orElse(null);
        assert userSec != null;
        if(prestamo.getUnUserSec().getId().equals(userSec.getId())) {
            if (libro.getCant_disponibles() > 0) {

                libro.setCant_disponibles(libro.getCant_disponibles() - 1);
                iLiSer.update(libro);
                prestamo.setEstado("prestado");
                return iPresRepo.save(prestamo);

            } else {
                return "el libro no esta disponible";
            }
        }else {
            return "mira cual es tu id este id es de otro usuario! de lo contrario no podras reservar";
        }
    }

    @Override
    public String deleteById(Long id) {
        iPresRepo.deleteById(id);
        return "delete success";
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return iPresRepo.findById(id);
    }

    @Override
    public Prestamo update(Prestamo prestamo) {
        return iPresRepo.save(prestamo);
    }

    @Override
    public List<Prestamo> seeHistoLPrest() {

        List<Prestamo> prestamoList = this.findAll();
        List<Prestamo> newPrestamos = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        for (Prestamo prestamo : prestamoList) {

            Optional<UserSec> userSec = iUserRepo.findUserEntityByUsername(username);
            if(userSec.isPresent()) {

                UserSec newUserSec = userSec.get();
                Long idUsu = newUserSec.getId();

                if(prestamo.getUnUserSec().getId().equals(idUsu)) {
                    newPrestamos.add(prestamo);
                }

            }

        }

        return newPrestamos;
    }

    @Override
    public String libroPresAcabado(Long id_prestamo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Prestamo prestamo = this.findById(id_prestamo).orElseThrow(() -> new EntityNotFoundException("no se encontro el prestamo"));

        if(prestamo.getUnUserSec().getUsername().equalsIgnoreCase(username)) {

            //para devolver el libro
            Libro libro = iLiSer.findById(prestamo.getUnLibro().getId()).orElse(null);
            assert libro != null;
            libro.setCant_disponibles(libro.getCant_disponibles() + 1);
            iLiSer.update(libro);

            //editamos nuestro prestamo q qda en el historial
            prestamo.setEstado("devuelto");
            prestamo.setFecha_devolucion(LocalDateTime.now());
            this.update(prestamo);
            return "se devolvio correctamenet";

        }else {
            return "no pediste este prestamo verifica q si sea tuyo";
        }
    }

    @Override
    public String cleanCache() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String mensaje = "";

        List<Prestamo> prestamoList = this.findAll();

        for (Prestamo prestamo : prestamoList) {

            if(prestamo.getUnUserSec().getUsername().equalsIgnoreCase(username)) {
                if(prestamo.getEstado().equalsIgnoreCase("devuelto")) {
                    this.deleteById(prestamo.getId());
                    mensaje = "se limpio correctamente";
                }

            }
        }

        return mensaje;
    }

    @Override
    public List<Prestamo> getOnlyPrestados() {

        List<Prestamo> prestamoList = this.findAll();
        List<Prestamo> getOnlyPrest = new ArrayList<>();

        for(Prestamo prestamo : prestamoList) {

            if(prestamo.getEstado().equalsIgnoreCase("prestado")) {
                getOnlyPrest.add(prestamo);
            }

        }

        return getOnlyPrest;
    }

    @Override
    public List<Prestamo> getOnlyDevueltos() {

        List<Prestamo> prestamoList = this.findAll();
        List<Prestamo> getOnlyDevueltos = new ArrayList<>();

        for (Prestamo prestamo : prestamoList) {
            if(prestamo.getEstado().equalsIgnoreCase("devuelto")) {
                getOnlyDevueltos.add(prestamo);
            }
        }

        return getOnlyDevueltos;
    }
}
