package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

@Component
@RequiredArgsConstructor
class OvertimeReportingConsole extends StatusSelectionProcessor {

    private final OvertimeFacade overtimeFacade;

    void createOvertimeObjectFromConsole(Scanner scanner) {
        try {
            OvertimeCreateDto overtimeCreateDto = getOvertimeCreateDto(scanner);
            overtimeFacade.createOvertimeAndSaveToDb(overtimeCreateDto);
        } catch (DateTimeParseException e) {
            printText("Zły format daty.");
        }
    }

    private OvertimeCreateDto getOvertimeCreateDto(Scanner scanner) {
        LocalDate date = promptForDate(scanner);
        String status = promptForStatus(scanner);
        int hours = promptForHours(scanner);
        return new OvertimeCreateDto(date, status, hours);
    }

    private LocalDate promptForDate(Scanner scanner) {
        printText("Data nadgodzin w formacie RRRR-MM-DD:");
        String dateString = scanner.nextLine();
        return LocalDate.parse(dateString);
    }

    private String promptForStatus(Scanner scanner) {
        printText("Rodzaj nadgodzin");
        return statusSelectionLoop(scanner);
    }

    private int promptForHours(Scanner scanner) {
        printText("Czas pracy (w godzinach)");
        int hours = scanner.nextInt();
        scanner.nextLine();
        return hours;
    }

    void removeOvertimeFromConsole(Scanner scanner) {
        printText("Podaj Id nadgodzin");
        try {
            long id = scanner.nextLong();
            scanner.nextLine();
            overtimeFacade.deleteOvertimeById(id);
        } catch (InputMismatchException e) {
            printText("Id musi być cyfrą");
        }
    }
}
