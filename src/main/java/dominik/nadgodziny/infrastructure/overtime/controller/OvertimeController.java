package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/overtimes")
@RequiredArgsConstructor
@CrossOrigin("*")
class OvertimeController {

    private final OvertimeFacade overtimeConsoleFacade;

    @GetMapping
    ResponseEntity<List<OvertimeResponseDto>>findAllOvertimes(){
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/statistics")
    ResponseEntity<OvertimeStatisticsDto>findAllOvertimesStatistics(){
        OvertimeStatisticsDto all = overtimeConsoleFacade.showStatisticsByYear();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/status")
    ResponseEntity<List<OvertimeResponseDto>>findOvertimesByStatus(@RequestParam int year,
                                                                   @RequestParam String status) {
        List<OvertimeResponseDto> byStatusAndYear = overtimeConsoleFacade.findByStatusAndYear(year, status);
        return ResponseEntity.ok(byStatusAndYear);
    }
}
