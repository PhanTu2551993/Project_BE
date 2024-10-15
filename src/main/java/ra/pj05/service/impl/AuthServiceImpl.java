package ra.pj05.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.pj05.constants.RoleName;
import ra.pj05.model.dto.request.FormLogin;
import ra.pj05.model.dto.request.FormRegister;
import ra.pj05.model.dto.response.JwtResponse;
import ra.pj05.model.entity.Roles;
import ra.pj05.model.entity.Users;
import ra.pj05.repository.IUserRepository;
import ra.pj05.sercurity.jwt.JwtUtil;
import ra.pj05.sercurity.principal.UserDetailsCustom;
import ra.pj05.service.IAuthService;
import ra.pj05.service.IRoleService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IRoleService roleService;
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse handleLogin(FormLogin formLogin) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password.");
        }
        UserDetailsCustom userDetailCustom = (UserDetailsCustom) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(userDetailCustom);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .username(userDetailCustom.getUsername())
                .fullName(userDetailCustom.getFullName())
                .email(userDetailCustom.getEmail())
                .address(userDetailCustom.getAddress())
                .avatar(userDetailCustom.getAvatar())
                .createdAt(userDetailCustom.getCreatAt())
                .updatedAt(userDetailCustom.getUpdateAt())
                .phone(userDetailCustom.getPhone())
                .status(userDetailCustom.getStatus())
                .roles(userDetailCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void handleRegister(FormRegister formRegister) {
        List<Roles> roles = new ArrayList<>();
        if (formRegister.getRoles() == null || formRegister.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else {
            formRegister.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "manager":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_MANAGER));
                    case "user":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                    default:
                        throw new RuntimeException("role not found");
                }
            });
        }

        Users users = Users.builder()
                .email(formRegister.getEmail())
                .username(formRegister.getUsername())
                .fullName(formRegister.getFullName())
                .phone(formRegister.getPhone())
                .status(true)
                .creatAt(new Date())
                .updateAt(new Date())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(users);
    }
}
