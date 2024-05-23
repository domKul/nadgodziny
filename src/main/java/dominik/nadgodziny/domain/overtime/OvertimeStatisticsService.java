package dominik.nadgodziny.domain.overtime;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class OvertimeStatisticsService {

    private final OvertimeReader overtimeReaderService;

    public void generateStatistics(){
        Map<Integer, Integer> overtimeHoursByYear = calculateOvertimeHoursByYear();
        for(Map.Entry<Integer,Integer> map :overtimeHoursByYear.entrySet()){
            int year = map.getKey();
            int hours = map.getValue();
            ConsoleWriter.printText("W roku " + year + " zrobiono " + hours + " dodatkowych godzin");
        }
    }

    private Map<Integer, Integer> calculateOvertimeHoursByYear() {
        Map<Integer,Integer> map =new HashMap<>();
        for (Overtime allOvertime : overtimeReaderService.findAllOvertimes()) {
            int year = allOvertime.getOvertimeDate().getYear();
            int actualHours = map.getOrDefault(year, 0);
            int updatingHours = actualHours + allOvertime.getDuration();
            map.put(year, updatingHours);
        }
        return map;
    }
}
