package dominik.nadgodziny.domain.overtime.dto;

import java.time.LocalDate;

public record OvertimeResponseDto( long id,
         LocalDate creationDate,
         LocalDate overtimeDate,
         String status,
         int duration) {
    @Override
    public String toString() {
        return "ID " + id + " ||  wpisano " +
                creationDate +
                " ||  data nadgodzin " +
                overtimeDate +
                " ||  rodzaj " +
                status +
                " ||  czas pracy " +
                duration + " godzin ";
    }
}
