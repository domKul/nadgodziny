package dominik.nadgodziny.infrastructure.overtime.security;


import dominik.nadgodziny.domain.user.UserEntity;
import dominik.nadgodziny.domain.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userByUsername = userFacade.findUser(username);
        return User.withUsername(userByUsername.getUsername())
                .password(userByUsername.getPassword())
                .build();
    }
}
