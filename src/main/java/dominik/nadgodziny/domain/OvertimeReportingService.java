package dominik.nadgodziny.domain;

import dominik.nadgodziny.domain.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.exception.ErrorMessages;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import static dominik.nadgodziny.domain.ConsoleWriter.printText;

class OvertimeReportingService {

    private final OvertimeRepository overtimeRepository;

    public OvertimeReportingService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }

    //@Transactional
    Optional<Overtime> addNewOvertime(Overtime overtime) {
        Optional<Overtime> ifNotNull = Optional.ofNullable(overtime);
        try {
            if (ifNotNull.isPresent()) {
                 overtimeRepository.save(overtime);
            }
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
        return ifNotNull;
    }

    OvertimeResponseDto createOvertimeObject(Scanner scanner) {
        Overtime overtime = null;
        try {
            printText("Data nadgodzin w formacie RRRR-MM-DD:");
            String dateString = scanner.nextLine();
            LocalDate date = localDateFromString(dateString);
            printText("Rodzaj nadgodzin");
            String status = scanner.nextLine();
            printText("Czas pracy (w godzinach)");
            int hours = scanner.nextInt();
            scanner.nextLine();
            overtime = new Overtime(date,status,hours);
            addNewOvertime(overtime);
        } catch (DateTimeParseException e) {
            printText(" Zły format daty.");
       }
        return overtime != null ? OvertimeMapper.mapToOvertimeResponseDto(overtime) : null;
    }

    LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }
}
