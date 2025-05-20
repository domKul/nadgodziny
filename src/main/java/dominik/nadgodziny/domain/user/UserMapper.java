package dominik.nadgodziny.domain.user;

import dominik.nadgodziny.domain.user.dto.RegisterRequestDto;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;

class UserMapper {

    static UserEntity dtoToUserEntity(RegisterRequestDto registerRequestDto) {
        return new UserEntity(
                registerRequestDto.username(),
                registerRequestDto.password()
        );
    }

    static RegisterResponseDto userEntityToResponseDto(UserEntity userEntity) {
        return new RegisterResponseDto(
                userEntity.getUsername()
        );
    }
}
