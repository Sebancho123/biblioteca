package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.Libro;
import com.detodo.biblioteca.model.Reserva;
import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.repository.IReservaRepository;
import com.detodo.biblioteca.repository.IUserSecRepository;
import com.detodo.biblioteca.service.iservice.ILibroService;
import com.detodo.biblioteca.service.iservice.IReservaService;
import com.detodo.biblioteca.service.iservice.IUserSecService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IUserSecRepository iUserRepo;

    @Autowired
    private IReservaRepository iResRepo;

    @Autowired
    private ILibroService iLiSer;

    @Override
    public List<Reserva> findAll() {
        return iResRepo.findAll();
    }

    @Override
    public Object save(Reserva reserva) {


        Libro libro = iLiSer.findById(reserva.getUnLibro().getId()).orElse(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserSec userSec = iUserRepo.findUserEntityByUsername(username).orElse(null);
        assert userSec != null;
        if(reserva.getUnUserSec().getId().equals(userSec.getId())) {
            if (libro.getCant_disponibles() > 0) {

                libro.setCant_disponibles(libro.getCant_disponibles() - 1);
                iLiSer.update(libro);
                reserva.setEstado("reservado");
                return iResRepo.save(reserva);

            } else {
                return "el libro no esta disponible";
            }
        }else {
            return "mira cual es tu id este id es de otro usuario! de lo contrario no podras reservar";
        }

    }

    @Override
    public String deleteById(Long id) {
        iResRepo.deleteById(id);
        return "delete success";
    }

    @Override
    public Optional<Reserva> findById(Long id) {
        return iResRepo.findById(id);
    }

    @Override
    public Reserva update(Reserva reserva) {
        return iResRepo.save(reserva);
    }

    @Override
    public String libroReTerminado(Long id_reserva) {

        Optional<Reserva> reserva = iResRepo.findById(id_reserva);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (reserva.isPresent()) {
            Reserva reserva1 = reserva.get();

            if(reserva1.getUnUserSec().getUsername().equalsIgnoreCase(username)) {

                Libro libro = iLiSer.findById(reserva1.getUnLibro().getId()).orElse(null);

                libro.setCant_disponibles(libro.getCant_disponibles() + 1);
                iLiSer.update(libro);

                reserva1.setEstado("terminado");
                this.update(reserva1);
                return "libro terminado";

            }else{
                return "esta reserva no es tuya!";
            }

        }else {
            return "esta reserva no existe!";
        }
    }

    @Override
    public String limpiarCache() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Reserva> reservaList = this.findAll();
        String mensaje= "";

        for (Reserva reserva : reservaList) {

            if(reserva.getUnUserSec().getUsername().equals(username)) {
                if(reserva.getEstado().equalsIgnoreCase("terminado")) {
                    this.deleteById(reserva.getId());
                    mensaje = "se limpio correctamnete";
                }
            }else{
                mensaje = "esta limpio";
            }
        }
        return mensaje;
    }

    @Override
    public List<Reserva> getOnlyReservado() {

        List<Reserva> reservaList = this.findAll();
        List<Reserva> getOnlyReservados = new ArrayList<>();

        for(Reserva reserva : reservaList) {
            if(reserva.getEstado().equalsIgnoreCase("reservado")){
                getOnlyReservados.add(reserva);
            }
        }

        return getOnlyReservados;
    }

    @Override
    public List<Reserva> getOnlyTerminado() {

        List<Reserva> reservaList = this.findAll();
        List<Reserva> getOnlyTerminado = new ArrayList<>();

        for(Reserva reserva : reservaList) {
            if(reserva.getEstado().equalsIgnoreCase("terminado")) {
                getOnlyTerminado.add(reserva);
            }
        }

        return getOnlyTerminado;
    }

}
