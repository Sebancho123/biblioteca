package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.model.Prestamo;
import com.detodo.biblioteca.service.iservice.IPrestamoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamo")
public class PrestamosController {

    @Autowired
    private IPrestamoService iPresSer;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Prestamo>> getAll() {

        List<Prestamo> prestamoList = iPresSer.findAll();
        return ResponseEntity.ok(prestamoList);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> save(@RequestBody Prestamo prestamo) {

        return ResponseEntity.ok(iPresSer.save(prestamo));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Prestamo> deleteById(@PathVariable Long id) {

        try {
            iPresSer.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Prestamo> findById(@PathVariable Long id) {

        Optional<Prestamo> prestamo = iPresSer.findById(id);
        return prestamo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Prestamo> update(@RequestBody Prestamo prestamo) {

        Prestamo newPrestamo = iPresSer.update(prestamo);
        return ResponseEntity.ok(newPrestamo);
    }

    @GetMapping("/lPrestados")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Prestamo>> seeHistoLPrest() {

        List<Prestamo> prestamoList = iPresSer.seeHistoLPrest();
        return ResponseEntity.ok(prestamoList);

    }

    @GetMapping("/end/{id_prestamo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> libroPresAcabado(@PathVariable Long id_prestamo) {
        return ResponseEntity.ok(iPresSer.libroPresAcabado(id_prestamo));
    }

    @GetMapping("/cleanCache")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> cleanCache() {
        return ResponseEntity.ok(iPresSer.cleanCache());
    }

    @GetMapping("/getOnlyPrestados")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Prestamo>> getOnlyPrestados() {
        return ResponseEntity.ok(iPresSer.getOnlyPrestados());
    }

    @GetMapping("/getOnlyDevueltos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Prestamo>> getOnlyDevueltos() {
        return ResponseEntity.ok(iPresSer.getOnlyDevueltos());
    }

}
