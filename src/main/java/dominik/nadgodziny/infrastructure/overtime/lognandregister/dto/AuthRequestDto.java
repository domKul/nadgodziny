package dominik.nadgodziny.infrastructure.overtime.lognandregister.dto;

import jakarta.validation.constraints.NotBlank;


public record AuthRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
