package dominik.nadgodziny.domain.user;

import dominik.nadgodziny.domain.user.dto.RegisterRequest;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;
import dominik.nadgodziny.domain.user.exception.UserExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 class UserService {

    private final UserRepository userRepository;

    UserEntity findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    UserEntity register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new UsernameNotFoundException(UserExceptionMessage.USER_ALREADY_EXISTS.name());
        }
        UserEntity userEntity = buildUserEntity(registerRequest);
        return userRepository.save(userEntity);
    }

    UserEntity buildUserEntity(final RegisterRequest registerRequest) {
        return UserMapper.dtoToUserEntity(registerRequest);
    }




}
