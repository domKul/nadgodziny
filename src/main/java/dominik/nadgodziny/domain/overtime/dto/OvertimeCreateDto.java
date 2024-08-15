package dominik.nadgodziny.domain.overtime.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record OvertimeCreateDto(
        @NotNull(message = "{date.not.null}")
        LocalDate overtimeDate,
        @NotNull(message = "{status.not.null}")
        String status,
        @Positive(message = "{duration.not.negative}")
        int duration) {
}
