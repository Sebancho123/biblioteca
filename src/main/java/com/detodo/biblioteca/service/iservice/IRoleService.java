package com.detodo.biblioteca.service.iservice;

import com.detodo.biblioteca.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public List<Role> findAll();

    public Role save(Role role);

    public String deleteById(Long id);

    public Optional<Role> findById(Long id);

    public Role update(Role role);

}
