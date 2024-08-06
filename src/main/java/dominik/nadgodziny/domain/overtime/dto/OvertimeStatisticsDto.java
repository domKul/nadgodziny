package dominik.nadgodziny.domain.overtime.dto;

import java.util.Map;

public record OvertimeStatisticsDto(
        Map<Integer,Integer> stats
) {
}
