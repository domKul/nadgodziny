package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;
@AllArgsConstructor
public class OvertimeConsoleFacade implements OvertimeFunctionDescription{

    private final OvertimeReaderService overtimeReaderService;
    private final OvertimeReportingService overtimeReportingService;

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
        initialMenu();
    }
    public void initialFind(){
        initialMenuFind();
    }

    public int sumOfAllOvertimeHoursByMonth(Scanner scanner) {
        return overtimeReaderService.getSumOfAllOvertimeHoursByMonthAndYear(scanner);
    }

    public int sumByGivenStatusOfGivenMonth(Scanner scanner) {
       return overtimeReaderService.getSumByGivenStatusOfGivenMonthWithYear(scanner);
    }
}
