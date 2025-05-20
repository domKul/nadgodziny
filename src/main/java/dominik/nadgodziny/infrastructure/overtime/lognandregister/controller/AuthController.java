package dominik.nadgodziny.infrastructure.overtime.lognandregister.controller;


import dominik.nadgodziny.infrastructure.overtime.lognandregister.dto.AuthRequestDto;
import dominik.nadgodziny.infrastructure.overtime.lognandregister.dto.AuthResponseDto;
import dominik.nadgodziny.domain.user.dto.RegisterRequestDto;
import dominik.nadgodziny.domain.user.dto.RegisterResponseDto;
import dominik.nadgodziny.infrastructure.overtime.lognandregister.LoginAndRegisterService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
class AuthController{

    private final LoginAndRegisterService loginAndRegisterService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(loginAndRegisterService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(loginAndRegisterService.encodeAndSaveUser(request));
    }
}
