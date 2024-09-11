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



}
