package com.detodo.biblioteca.controller;

import com.detodo.biblioteca.model.Permission;
import com.detodo.biblioteca.service.iservice.IPermissionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService iPerSer;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Permission>> getAll() {
        List<Permission> permissionList = iPerSer.findAll();
        return ResponseEntity.ok(permissionList);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permission> save(@RequestBody Permission permission) {

        Permission newPermission = iPerSer.save(permission);
        return ResponseEntity.ok(newPermission);

    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permission> deleteById(@PathVariable Long id) {

        try {
            iPerSer.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Permission> findById(@PathVariable Long id) {

        Optional<Permission> permission = iPerSer.findById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permission> update(@RequestBody Permission permission) {

        Permission newPermission = iPerSer.update(permission);
        return ResponseEntity.ok(newPermission);

    }

}
