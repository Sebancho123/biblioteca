package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.dto.AuthLoginRequestDTO;
import com.detodo.biblioteca.dto.AuthResponseDTO;
import com.detodo.biblioteca.service.UserDetailsServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class authenticationController {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {

        return new ResponseEntity<AuthResponseDTO>(this.userDetailsServiceImp.login(userRequest), HttpStatus.OK);

    }

}
