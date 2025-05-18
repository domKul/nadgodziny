package dominik.nadgodziny.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserEntity findUser(String username){
        return userService.findUserByUsername(username);
    }


}
