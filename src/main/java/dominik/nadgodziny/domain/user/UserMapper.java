package dominik.nadgodziny.domain.user;

import dominik.nadgodziny.domain.user.dto.RegisterRequest;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;

class UserMapper {

    static UserEntity dtoToUserEntity(RegisterRequest registerRequest) {
        return new UserEntity(
                registerRequest.username(),
                registerRequest.password()
        );
    }

    static RegisterResponseDto userEntityToResponseDto(UserEntity userEntity) {
        return new RegisterResponseDto(
                userEntity.getUsername()
        );
    }
}
