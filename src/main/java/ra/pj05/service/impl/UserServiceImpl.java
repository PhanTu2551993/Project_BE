package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.pj05.constants.RoleName;
import ra.pj05.exception.ChangeException;
import ra.pj05.model.dto.request.UpdateUserRequest;
import ra.pj05.model.entity.Roles;
import ra.pj05.model.entity.Users;
import ra.pj05.repository.IUserRepository;
import ra.pj05.service.IRoleService;
import ra.pj05.service.IUserService;
import ra.pj05.service.UploadService;


import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private IRoleService roleService;

    @Override
    public Page<Users> getAllUsers(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<Users> searchAndPageUsers(String searchText, int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findByUsernameContainingOrPhoneContainingOrEmailContainingOrFullNameContaining(searchText, searchText, searchText, searchText, pageable);
    }

    @Override
    public Users updateAvatarUser(UpdateUserRequest updateUserRequest) {
        String imageUrl = null;
        if (updateUserRequest.getAvatar() != null && !updateUserRequest.getAvatar().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(updateUserRequest.getAvatar());
        }
        Users updateUser = getCurrentLoggedInUser();
        if (updateUserRequest.getAvatar() != null) {
            updateUser.setAvatar(imageUrl);
        }
        updateUser.setUpdateAt(new Date());
        return userRepository.save(updateUser);
    }

    @Override
    public Users changeRole(Long userId,String newRoleName) {
        Users currenUser = getCurrentLoggedInUser();
        Users user = getUserById(userId);
        if (user == null){
            throw new ChangeException("User không tồn tại");
        }
        if (userId.equals(currenUser.getUserId())) {
            throw new ChangeException("Không thể thay đổi trạng thái tài khoản cu mình");
        }

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_ADMIN));

        if (isAdmin) {
            throw new ChangeException("Không thể thay đổi trạng thái ADMIN");
        }
        user.getRoles().clear();
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(newRoleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + newRoleName);
        }
        Roles newRole = roleService.findByRoleName(roleName);
        user.getRoles().add(newRole);
        return userRepository.save(user);
    };

    @Override
    public Users changeStatus(Long userId) {
        Users currenUser = getCurrentLoggedInUser();
        Users user = getUserById(userId);
        if (userId.equals(currenUser.getUserId())) {
            throw new ChangeException("Không thể thay đổi trạng thái tài khoản này");
        }
        if (user == null) {
            throw new RuntimeException("Người dùng không tồn tại");
        }

        if(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals(RoleName.ROLE_ADMIN))){
            throw new ChangeException("Không thể thay đổi trạng thái ADMIN");
        }
        user.setUpdateAt(new Date());
        user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

        @Override
        public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
    }
//
//    @Override
//    public Users getUserByUserName(String username) {
//        return userRepository.findUsersByUsername(username).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
//    }
//
    @Override
    public Users getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUsersByUsername(username).orElseThrow(() -> new NoSuchElementException("Không có người dùng"));
    }
//
//    private boolean isValidPassword(String password) {
//        return password.length() >= 8;
//    }

}
