package dominik.nadgodziny.domain.user;

import dominik.nadgodziny.domain.user.dto.RegisterRequestDto;
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

    UserEntity register(RegisterRequestDto registerRequestDto) {
        if (userRepository.findByUsername(registerRequestDto.username()).isPresent()) {
            throw new UsernameNotFoundException(UserExceptionMessage.USER_ALREADY_EXISTS.name());
        }
        UserEntity userEntity = buildUserEntity(registerRequestDto);
        return userRepository.save(userEntity);
    }

    UserEntity buildUserEntity(final RegisterRequestDto registerRequestDto) {
        return UserMapper.dtoToUserEntity(registerRequestDto);
    }




}
