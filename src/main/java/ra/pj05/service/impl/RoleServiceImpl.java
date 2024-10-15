package ra.pj05.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.pj05.constants.RoleName;
import ra.pj05.model.entity.Roles;
import ra.pj05.repository.IRoleRepository;
import ra.pj05.service.IRoleService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Roles findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("role not found"));
    }
}

