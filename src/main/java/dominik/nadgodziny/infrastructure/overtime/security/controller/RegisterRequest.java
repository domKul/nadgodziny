package dominik.nadgodziny.infrastructure.overtime.security.controller;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
