package dominik.nadgodziny.domain.overtime.dto;

import java.time.LocalDate;

public record OvertimeCreateDto(LocalDate overtimeDate,
                                String status,
                                int duration) {
}
