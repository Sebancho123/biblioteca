package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.Role;
import com.detodo.biblioteca.repository.IRoleRepository;
import com.detodo.biblioteca.service.iservice.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository iRolRepo;

    @Override
    public List<Role> findAll() {
        return iRolRepo.findAll();
    }

    @Override
    public Role save(Role role) {
        return iRolRepo.save(role);
    }

    @Override
    public String deleteById(Long id) {
        iRolRepo.deleteById(id);
        return "delete successful";
    }

    @Override
    public Optional<Role> findById(Long id) {
        return iRolRepo.findById(id);
    }

    @Override
    public Role update(Role role) {
        return iRolRepo.save(role);
    }
}
