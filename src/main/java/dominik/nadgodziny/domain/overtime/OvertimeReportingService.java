package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.exception.ErrorMessages;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

class OvertimeReportingService {

    private final OvertimeRepository overtimeRepository;

    public OvertimeReportingService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }

    //@Transactional
    void addNewOvertime(Overtime overtime) {
        Optional<Overtime> ifNotNull = Optional.ofNullable(overtime);
        try {
            if (ifNotNull.isPresent()) {
                overtimeRepository.save(overtime);
            }
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
    }

    void createOvertimeObject(Scanner scanner) {
        try {
            printText("Data nadgodzin w formacie RRRR-MM-DD:");
            String dateString = scanner.nextLine();
            LocalDate date = localDateFromString(dateString);
            printText("Rodzaj nadgodzin");
            String status = scanner.nextLine();
            printText("Czas pracy (w godzinach)");
            int hours = scanner.nextInt();
            Overtime overtime = new Overtime(date, status, hours);
            addNewOvertime(overtime);
        } catch (DateTimeParseException e) {
            printText(" Zły format daty.");
       }
    }

    LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }
}
