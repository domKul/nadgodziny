package dominik.nadgodziny.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
