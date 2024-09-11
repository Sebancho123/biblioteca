package com.detodo.biblioteca.service;

import com.detodo.biblioteca.dto.AuthLoginRequestDTO;
import com.detodo.biblioteca.dto.AuthResponseDTO;
import com.detodo.biblioteca.model.UserSec;
import com.detodo.biblioteca.repository.IUserSecRepository;
import com.detodo.biblioteca.service.iservice.IUserSecService;
import com.detodo.biblioteca.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserSecRepository iUserRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserSecService iUserSer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //tenemos el user y necesitamos devolverlo en format userDatails
        //traer our user de la db
        UserSec userSec = iUserRepo.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("el usuario" + username + " no fue encontrado"));


        //creamos una lista para los permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //traer roles y convertirlos en SimpleGrantedAuthority
        userSec.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        //traer permisos y convertirlos en SimpleGrantedAuthority
        userSec.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isAccountNotLocked(),
                userSec.isCredentialNotExpired(),
                authorityList
        );
    }

    public AuthResponseDTO login(AuthLoginRequestDTO userRequest) {


        //recuperar username y contra
        String email = userRequest.email();
        String password = userRequest.password();

        UserSec userSec = iUserSer.findUserEntityByEmail(email);
        String username = userSec.getUsername();

        Authentication authentication = this.authenticate(username, email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);


        return new AuthResponseDTO(email, "Login successful", accessToken, true);

        }

    private Authentication authenticate(String username, String email, String password) {

        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }
}
