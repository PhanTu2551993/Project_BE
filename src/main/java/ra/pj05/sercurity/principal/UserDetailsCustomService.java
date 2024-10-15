package ra.pj05.sercurity.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.pj05.model.entity.Users;
import ra.pj05.repository.IUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsCustomService implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepository.findUsersByUsername(username);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            return UserDetailsCustom.builder()
                    .userId(users.getUserId())
                    .username(users.getUsername())
                    .fullName(users.getFullName())
                    .phone(users.getPhone())
                    .email(users.getEmail())
                    .avatar(users.getAvatar())
                    .updateAt(users.getUpdateAt())
                    .creatAt(users.getCreatAt())
                    .password(users.getPassword())
                    .status(users.getStatus())
                    .address(users.getAddress())
                    .authorities(users.getRoles().stream()
                            .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                            .toList())
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
