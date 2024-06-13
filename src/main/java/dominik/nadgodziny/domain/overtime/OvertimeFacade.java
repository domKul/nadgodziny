package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class OvertimeFacade {

    private final OvertimeStatisticsService overtimeStatisticsService;
    private final OvertimeReportingService overtimeReportingService;
    private final OvertimeReaderService overtimeReaderService;

    public List<OvertimeResponseDto> findByMonth(int year, int month){
        List<Overtime> overtimeByMonthAndYear = overtimeReaderService.findOvertimeByMonthAndYear(year, month);
        return OvertimeMapper.mapToOvertimeResponseDtoList(overtimeByMonthAndYear);
    }

    public OvertimeResponseDto createOvertimeAndSaveToDb(LocalDate date, String status, int hours){
        Overtime overtime = overtimeReportingService.createOvertimeObject(date, status, hours);
        return OvertimeMapper.mapToOvertimeResponseDto(overtime);
    }

    public void deleteOvertimeById(long id){
        overtimeReportingService.deleteOvertimeById(id);
    }

    public List<OvertimeResponseDto> findAll() {
        List<Overtime> allOvertimes = overtimeReaderService.sortAllOvertimesById();
        return OvertimeMapper.mapToOvertimeResponseDtoList(allOvertimes);
    }

    public List<OvertimeResponseDto> findByStatusAndYear(int year, String status) {
        List<Overtime> overtimeByStatus = overtimeReaderService.findAllOvertimesByStatus(year,status);
        return OvertimeMapper.mapToOvertimeResponseDtoList(overtimeByStatus);
    }


//    public void initialInfo() {
//        initialMenu();
//    }
//    public void initialFind(){
//        initialMenuFind();
//    }

    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        return overtimeReaderService.sumOfAllOvertimeHoursByMonth(year, month);
    }

    public int sumByGivenStatusOfGivenMonth(int year,int month,String status) {
       return overtimeReaderService.sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(year,month,status);
    }

    public void showStatisticsByYear(){
        overtimeStatisticsService.generateStatistics();
    }
}
