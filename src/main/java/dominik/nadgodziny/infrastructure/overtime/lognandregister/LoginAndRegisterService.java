package dominik.nadgodziny.infrastructure.overtime.lognandregister;

import dominik.nadgodziny.domain.user.UserFacade;
import dominik.nadgodziny.domain.user.dto.RegisterRequestDto;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;
import dominik.nadgodziny.infrastructure.overtime.lognandregister.dto.AuthRequestDto;
import dominik.nadgodziny.infrastructure.overtime.lognandregister.dto.AuthResponseDto;
import dominik.nadgodziny.infrastructure.overtime.security.CustomUserDetailsService;
import dominik.nadgodziny.infrastructure.overtime.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAndRegisterService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserFacade userFacade;

    public RegisterResponseDto encodeAndSaveUser(RegisterRequestDto registerRequestDto) {
        String encode = passwordEncoder.encode(registerRequestDto.password());

        return userFacade.registerUser(new RegisterRequestDto(registerRequestDto.username(), encode));
    }

    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.username(), authRequestDto.password())
        );
        UserDetails user = userDetailsService.loadUserByUsername(authRequestDto.username());
        return new AuthResponseDto(jwtService.generateToken(user));
    }
}
