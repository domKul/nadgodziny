package dominik.nadgodziny.infrastructure.overtime.security.controller;



import dominik.nadgodziny.domain.user.UserEntity;
import dominik.nadgodziny.domain.user.UserRepository;
import dominik.nadgodziny.domain.user.dto.AuthRequest;
import dominik.nadgodziny.domain.user.dto.AuthResponse;
import dominik.nadgodziny.infrastructure.overtime.security.CustomUserDetailsService;
import dominik.nadgodziny.infrastructure.overtime.security.JwtService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));


        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
