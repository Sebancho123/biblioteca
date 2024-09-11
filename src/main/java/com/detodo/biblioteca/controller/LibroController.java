package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.model.Libro;
import com.detodo.biblioteca.model.Reserva;
import com.detodo.biblioteca.service.iservice.ILibroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private ILibroService iLibSer;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Libro>> findAll() {

        List<Libro> libroList = iLibSer.findAll();
        return ResponseEntity.ok(libroList);

    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Libro> save(@RequestBody Libro libro) {

        Libro newLibro = iLibSer.save(libro);
        return ResponseEntity.ok(newLibro);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Libro> deleteById(@PathVariable Long id) {

        try {
            iLibSer.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Libro> findById(@PathVariable Long id){

        Optional<Libro> libro = iLibSer.findById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Libro> update(@RequestBody Libro libro) {

        Libro newLibro = iLibSer.update(libro);
        return ResponseEntity.ok(newLibro);

    }

    @GetMapping("search/{anyThing}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Libro>> search(@PathVariable String anyThing) {

        List<Libro> libroList = iLibSer.search(anyThing);

        return ResponseEntity.ok(libroList);

    }

    @GetMapping("/lReservados")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Reserva>> seeLHistoReservados() {

        List<Reserva> reservaList = iLibSer.seeLHistoReservados();
        return ResponseEntity.ok(reservaList);

    }

}
