package dominik.nadgodziny.domain.dto;

import java.time.LocalDate;

public record OvertimeResponseDto( long id,
         LocalDate creationDate,
         LocalDate overtimeDate,
         String status,
         int duration) {
}
