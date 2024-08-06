package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeStatisticsDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OvertimeFacade {

    private final OvertimeStatisticsService overtimeStatisticsService;
    private final OvertimeReportingService overtimeReportingService;
    private final OvertimeReaderService overtimeReaderService;

    public List<OvertimeResponseDto> findByMonth(int year, int month){
        List<OvertimeEntity> overtimeByMonthAndYear = overtimeReaderService.findOvertimeByMonthAndYear(year, month);
        return OvertimeMapper.mapToOvertimeResponseDtoList(overtimeByMonthAndYear);
    }

    public OvertimeResponseDto createOvertimeAndSaveToDb(OvertimeCreateDto overtimeCreateDto){
        OvertimeEntity overtime = overtimeReportingService.createOvertimeObject(overtimeCreateDto);
        return OvertimeMapper.mapToOvertimeResponseDto(overtime);
    }

    public void deleteOvertimeById(long id){
        overtimeReportingService.deleteOvertimeById(id);
    }

    public List<OvertimeResponseDto> findAll() {
        List<OvertimeEntity> allOvertimes = overtimeReaderService.sortAllOvertimesById();
        return OvertimeMapper.mapToOvertimeResponseDtoList(allOvertimes);
    }

    public List<OvertimeResponseDto> findByStatusAndYear(int year, String status) {
        List<OvertimeEntity> overtimeByStatus = overtimeReaderService.findAllOvertimesByStatus(year,status);
        return OvertimeMapper.mapToOvertimeResponseDtoList(overtimeByStatus);
    }

    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        return overtimeReaderService.sumOfAllOvertimeHoursByMonth(year, month);
    }

    public int sumByGivenStatusOfGivenMonth(int year,int month,String status) {
       return overtimeReaderService.sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(year,month,status);
    }

    public OvertimeStatisticsDto showStatisticsByYear(){
       return overtimeStatisticsService.generateStatistics();
    }
}
