package dominik.nadgodziny.domain.user;

import dominik.nadgodziny.domain.user.dto.RegisterRequestDto;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserEntity findUser(String username){
        return userService.findUserByUsername(username);
    }

    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto){
        return UserMapper.userEntityToResponseDto(userService.register(registerRequestDto));
    }


}
