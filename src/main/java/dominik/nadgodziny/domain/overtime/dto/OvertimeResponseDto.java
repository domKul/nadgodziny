package dominik.nadgodziny.domain.overtime.dto;

import java.time.LocalDate;

public record OvertimeResponseDto( long id,
         LocalDate creationDate,
         LocalDate overtimeDate,
         String status,
         int duration) {
}
