package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.model.Reserva;
import com.detodo.biblioteca.service.iservice.IReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private IReservaService iResSer;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Reserva>> findAll() {

        List<Reserva> reservaList = iResSer.findAll();
        return ResponseEntity.ok(reservaList);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> save(@RequestBody Reserva reserva) {

        //Object newReserva = iResSer.save(reserva);
        return ResponseEntity.ok(iResSer.save(reserva));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reserva> deleteById(@PathVariable Long id) {

        try {
            iResSer.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Reserva> findById(@PathVariable Long id) {

        Optional<Reserva> reserva = iResSer.findById(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reserva> update(@RequestBody Reserva reserva) {

        Reserva newReserva = iResSer.update(reserva);
        return ResponseEntity.ok(newReserva);
    }

    @GetMapping("/terminado/{id_reser}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> libroRTerminado(@PathVariable Long id_reser) {

        String acabado = iResSer.libroReTerminado(id_reser);
        return ResponseEntity.ok(acabado);
    }

    @GetMapping("/cleanCache")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> limpiarCache() {

        return ResponseEntity.ok(iResSer.limpiarCache());

    }

    @GetMapping("/getOnlyReservados")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Reserva>> getOnlyReservados() {
        return ResponseEntity.ok(iResSer.getOnlyReservado());
    }

    @GetMapping("/getOnlyTerminados")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Reserva>> getOnlyTerminados() {
        return ResponseEntity.ok(iResSer.getOnlyTerminado());
    }

}
