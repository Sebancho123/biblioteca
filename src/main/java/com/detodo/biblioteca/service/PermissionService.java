package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.Permission;
import com.detodo.biblioteca.repository.IPermissionRepository;
import com.detodo.biblioteca.service.iservice.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private IPermissionRepository iPerRepo;

    @Override
    public List<Permission> findAll() {
        return iPerRepo.findAll();
    }

    @Override
    public Permission save(Permission permission) {
        return iPerRepo.save(permission);
    }

    @Override
    public String deleteById(Long id) {
        iPerRepo.deleteById(id);
        return "delete successful";
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return iPerRepo.findById(id);
    }

    @Override
    public Permission update(Permission permission) {
        return iPerRepo.save(permission);
    }
}
