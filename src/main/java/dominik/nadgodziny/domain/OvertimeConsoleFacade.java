package dominik.nadgodziny.domain;

import dominik.nadgodziny.domain.dto.OvertimeResponseDto;

import java.util.List;
import java.util.Scanner;

public class OvertimeConsoleFacade {
    private final OvertimeReaderService overtimeReaderService;
    private final OvertimeReportingService overtimeReportingService;

    OvertimeConsoleFacade(OvertimeReaderService overtimeReaderService, OvertimeReportingService overtimeReportingService) {
        this.overtimeReaderService = overtimeReaderService;
        this.overtimeReportingService = overtimeReportingService;
    }

    public OvertimeResponseDto createOvertimeAndSaveToDb(Scanner scanner){
       return overtimeReportingService.createOvertimeObject(scanner);
    }

    public List<OvertimeResponseDto> findAll() {
        List<Overtime> allOvertimes = overtimeReaderService.getAllOvertimes();
        return OvertimeMapper.mapToOvertimeResponseDtoList(allOvertimes);
    }

    public List<OvertimeResponseDto> findByMonth(Scanner scanner) {
        List<Overtime> overtimeByMonth = overtimeReaderService.getOvertimeByMonth(scanner);
        return OvertimeMapper.mapToOvertimeResponseDtoList(overtimeByMonth);
    }


    public void initialInfo() {
        ConsoleWriter.printText("\n\n\nWybierz opcje" +
                " \n 1-dodaj " +
                "\n 2-wszystkie " +
                "\n 3-zakoncz " +
                "\n 4-znajdz po miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin o z danego rodzaju w danym miesiacu");
    }

    public int sumOfAllOvertimeHoursByMonth(Scanner scanner) {
        return overtimeReaderService.getSumOfAllOvertimeHoursByMonth(scanner);
    }

    public int sumByGivenStatusOfGivenMonth(Scanner scanner) {
       return overtimeReaderService.getSumByGivenStatusOfGivenMonth(scanner);
    }
}
