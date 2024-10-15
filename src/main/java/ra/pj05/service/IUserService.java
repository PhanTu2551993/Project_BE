package ra.pj05.service;

import org.springframework.data.domain.Page;
import ra.pj05.exception.CustomException;
import ra.pj05.model.dto.request.UpdateUserRequest;
import ra.pj05.model.entity.Users;


import java.util.List;


public interface IUserService {
    Users getUserById(Long id);
//    Users getUserByUserName(String username);
    Users changeStatus(Long userId);
    Users getCurrentLoggedInUser();
    Page<Users> getAllUsers(int page, int size, String sortField, String sortDirection);
    Page<Users> searchAndPageUsers(String searchText, int page, int size, String sortField, String sortDirection);
    Users updateAvatarUser(UpdateUserRequest updateUserRequest);
    Users changeRole(Long userId,String newRoleName);
}
