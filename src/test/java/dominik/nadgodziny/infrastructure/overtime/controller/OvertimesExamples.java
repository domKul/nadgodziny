package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OvertimesExamples {

    protected  List<OvertimeResponseDto> mockOvertimeResponseDtoList;


    void twoOvertimesExamplesWithStatusZlecenie() {
        mockOvertimeResponseDtoList = Arrays.asList(
                new OvertimeResponseDto(1, LocalDate.parse("2023-01-01"),LocalDate.parse("2023-01-01"), "zlecenie", 8),
                new OvertimeResponseDto(2, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), "zlecenie", 8)
        );
    }




}
