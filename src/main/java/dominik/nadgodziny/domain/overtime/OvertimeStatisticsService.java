package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeStatisticsDto;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class OvertimeStatisticsService {

    private final OvertimeReader overtimeReaderService;

    public OvertimeStatisticsDto generateStatistics(){
        Map<Integer, Integer> overtimeHoursByYear = calculateOvertimeHoursByYear();
        return new OvertimeStatisticsDto(overtimeHoursByYear);
    }

    private Map<Integer, Integer> calculateOvertimeHoursByYear() {
        Map<Integer,Integer> map =new HashMap<>();
        for (OvertimeEntity allOvertime : overtimeReaderService.findAllOvertimes()) {
            int year = allOvertime.getOvertimeDate().getYear();
            int actualHours = map.getOrDefault(year, 0);
            int updatingHours = actualHours + allOvertime.getDuration();
            map.put(year, updatingHours);
        }
        return map;
    }
}
