package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.model.Role;
import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.service.iservice.IRoleService;
import com.detodo.biblioteca.service.iservice.IUserSecService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserSecController {

    @Autowired
    private IUserSecService iUserSer;

    @Autowired
    private IRoleService iRolSer;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserSec>> getAll() {

        List<UserSec> userSecList = iUserSer.findAll();
        return ResponseEntity.ok(userSecList);

    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> save(@RequestBody UserSec userSec) {

        Set<Role> roles = new HashSet<>();
        userSec.setPassword(iUserSer.encryptPassword(userSec.getPassword()));

        for(Role role : userSec.getRoleList()) {

            Role readRole = iRolSer.findById(role.getId()).orElse(null);
            if(readRole != null) {
                roles.add(role);
            }
        }

        if(!roles.isEmpty()) {

            UserSec newUserSec = iUserSer.save(userSec);
            return ResponseEntity.ok(newUserSec);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> deleteById(@PathVariable Long id) {

        try {
            iUserSer.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserSec> findById(@PathVariable Long id) {

        Optional<UserSec> userSec = iUserSer.findById(id);
        return userSec.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> update(@RequestBody UserSec userSec) {

        userSec.setPassword(iUserSer.encryptPassword(userSec.getPassword()));

        UserSec newUserSec = iUserSer.update(userSec);
        return ResponseEntity.ok(newUserSec);

    }

    @GetMapping("/block/{id_usu}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> blockUsu(@PathVariable Long id_usu) {

        return ResponseEntity.ok(iUserSer.blockUsu(id_usu));

    }

    @GetMapping("/desbloq/{id_usu}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> desblockUsu(@PathVariable Long id_usu) {

        return ResponseEntity.ok(iUserSer.desblockUsu(id_usu));

    }

}
