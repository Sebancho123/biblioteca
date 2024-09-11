package com.detodo.biblioteca.service;

import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.repository.IUserSecRepository;
import com.detodo.biblioteca.service.iservice.IUserSecService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecService implements IUserSecService {

    @Autowired
    private IUserSecRepository iUserRepo;

    @Override
    public List<UserSec> findAll() {
        return iUserRepo.findAll();
    }

    @Override
    public UserSec save(UserSec userSec) {

        return iUserRepo.save(userSec);
    }

    @Override
    public String deleteById(Long id) {
        iUserRepo.deleteById(id);
        return "delete success";
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return iUserRepo.findById(id);
    }

    @Override
    public UserSec update(UserSec userSec) {
        return iUserRepo.save(userSec);
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserSec findUserEntityByEmail(String email) {

        List<UserSec> userSecList = iUserRepo.findAll();
        Long idUser = null;

        for(UserSec userSec : userSecList) {

            if(userSec.getEmail().equals(email)){
                idUser = userSec.getId();
                break;
            }

        }

        UserSec userSec1 = this.findById(idUser).orElse(null);
        return userSec1;

    }

    @Override
    public String blockUsu(Long id_usu) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserSec userSec = iUserRepo.findUserEntityByUsername(username).orElse(null);

        UserSec userSecBlock = this.findById(id_usu).orElseThrow(() -> new UsernameNotFoundException("este usuario no existe!!!"));
        List<UserSec> listBlocks = new ArrayList<>();

        listBlocks.add(userSecBlock);

        assert userSec != null;
        userSec.setListBloqueados(listBlocks);
        this.update(userSec);

        return "usuario bloqueado correctamente para desbloquear use /desbloq/{id_usu}";
    }

    @Override
    public String desblockUsu(Long id_usu) {

        String mensaje = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserSec userSec = iUserRepo.findUserEntityByUsername(username).orElse(null);
        assert userSec != null;
        List<UserSec> listBlocks = userSec.getListBloqueados();

        UserSec userSecDesblock = this.findById(id_usu).orElseThrow(() -> new UsernameNotFoundException("este usuario no existe"));

        for (UserSec userSec1 : listBlocks) {

            if(userSec1.getUsername().equalsIgnoreCase(userSecDesblock.getUsername())) {
                listBlocks.remove(userSecDesblock);
                this.update(userSec);
                mensaje = "se desbloqueo correctamente";
                return mensaje;
            }else {
                mensaje = "este usuario no esta bloqueado";
                return mensaje;
            }
        }
        return mensaje;
    }
}
