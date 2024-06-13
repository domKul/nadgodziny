package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/overtimes")
@RequiredArgsConstructor
public class OvertimeController {

    private final OvertimeFacade overtimeConsoleFacade;

    @GetMapping
    ResponseEntity<List<OvertimeResponseDto>>findAllOvertimes(){
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();
        return ResponseEntity.ok().body(all);
    }
}
