package dominik.nadgodziny.domain.overtime;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

@Service
public class OvertimeConsoleFacade {
    private final OvertimeReaderService overtimeReaderService;
    private final OvertimeReportingService overtimeReportingService;

    OvertimeConsoleFacade(OvertimeReaderService overtimeReaderService, OvertimeReportingService overtimeReportingService) {
        this.overtimeReaderService = overtimeReaderService;
        this.overtimeReportingService = overtimeReportingService;
    }

    public void createOvertimeAndSaveToDb(Scanner scanner) throws DateTimeParseException{
        overtimeReportingService.createOvertimeObject(scanner);
    }

    public void findAll() {
        overtimeReaderService.getAllOvertimes();
    }

    public void findByMonth(Scanner scanner) {
        overtimeReaderService.getOvertimeByMonth(scanner);
    }


    public void initialInfo() {
        printText("\n\n\nWybierz opcje" +
                " \n 1-dodaj " +
                "\n 2-wszystkie " +
                "\n 3-zakoncz " +
                "\n 4-znajdz po miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin o z danego rodzaju w danym miesiacu");
    }

    public void sumOfAllOvertimeHoursByMonth(Scanner scanner) {
        overtimeReaderService.getSumOfAllOvertimeHoursByMonth(scanner);
    }

    public void sumByGivenStatusOfGivenMonth(Scanner scanner) {
        overtimeReaderService.getSumByGivenStatusOfGivenMonth(scanner);
    }
}
