package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/overtimes")
@RequiredArgsConstructor
@Validated
class OvertimeController {

    private final OvertimeFacade overtimeConsoleFacade;

    @GetMapping
    ResponseEntity<List<OvertimeResponseDto>>findAllOvertimes(){
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/status")
    ResponseEntity<List<OvertimeResponseDto>>findOvertimesByStatus(@RequestParam int year,
                                                                   @RequestParam String status) {
        List<OvertimeResponseDto> byStatusAndYear = overtimeConsoleFacade.findByStatusAndYear(year, status);
        return ResponseEntity.ok(byStatusAndYear);
    }
}
