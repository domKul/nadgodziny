package dominik.nadgodziny.infrastructure.overtime.security;


import dominik.nadgodziny.domain.user.UserEntity;
import dominik.nadgodziny.domain.user.UserFacade;
import dominik.nadgodziny.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userByUsername = userFacade.findUser(username);
//         userRepository.findByUsername(username)
//                .map(user -> User.withUsername(user.getUsername())
//                        .password(user.getPassword())
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(userByUsername.getUsername())
                .password(userByUsername.getPassword())
                .build();
    }
}
