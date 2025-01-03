package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeStatisticsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    ResponseEntity<List<OvertimeResponseDto>> findAllOvertimes() {
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/months")
    ResponseEntity<List<OvertimeResponseDto>> findOvertimeByMonth(@RequestParam int year,
                                                                  @RequestParam int month) {
        List<OvertimeResponseDto> byMonth = overtimeConsoleFacade.findByMonth(year, month);
        return ResponseEntity.ok().body(byMonth);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OvertimeResponseDto> addOvertime(@RequestBody @Valid OvertimeCreateDto overtimeCreateDto) {
        OvertimeResponseDto overtimeAndSaveToDb = overtimeConsoleFacade.createOvertimeAndSaveToDb(overtimeCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(overtimeAndSaveToDb);
    }

    @GetMapping("/statistics")
    ResponseEntity<OvertimeStatisticsDto> findAllOvertimesStatistics() {
        OvertimeStatisticsDto statistics = overtimeConsoleFacade.showStatisticsByYear();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/status")
    ResponseEntity<List<OvertimeResponseDto>> findOvertimesByStatus(@RequestParam int year,
                                                                    @RequestParam String status) {
        List<OvertimeResponseDto> byStatusAndYear = overtimeConsoleFacade.findByStatusAndYear(year, status);
        return ResponseEntity.ok(byStatusAndYear);
    }
}
