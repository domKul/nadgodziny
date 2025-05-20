package dominik.nadgodziny.infrastructure.overtime.lognandregister;

import dominik.nadgodziny.domain.user.UserFacade;
import dominik.nadgodziny.domain.user.dto.RegisterRequest;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserFacade userFacade;

    public RegisterResponseDto encodeAndSaveUser(RegisterRequest registerRequest) {
        String encode = passwordEncoder.encode(registerRequest.password());

        return userFacade.registerUser(new RegisterRequest(registerRequest.username(), encode));
    }
}
