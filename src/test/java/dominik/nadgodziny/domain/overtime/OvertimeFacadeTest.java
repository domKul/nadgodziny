package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OvertimeFacadeTest {

    OvertimeFacade overtimeFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

    @Test
    void shouldCreateOvertimeAndSaveToDbSuccessfully() {
        // Given
        LocalDate date = LocalDate.parse("2023-09-09");
        String status = "nadgodziny";
        int hours = 5;
        OvertimeCreateDto overtimeCreateDto = new OvertimeCreateDto(date, status, hours);

        // When
        List<OvertimeResponseDto> allBefore = overtimeFacade.findAll();
        assertEquals(3,allBefore.size());
        OvertimeResponseDto overtimeResponseDto = overtimeFacade.createOvertimeAndSaveToDb(overtimeCreateDto);
        List<OvertimeResponseDto> allAfter = overtimeFacade.findAll();

        // Then
        assertAll(
                ()->assertThat(allAfter.size()).isEqualTo(4),
                () -> assertThat(overtimeResponseDto).isNotNull(),
                () -> assertThat(overtimeResponseDto.overtimeDate().toString()).isEqualTo("2023-09-09"),
                () -> assertThat(overtimeResponseDto.duration()).isEqualTo(5)
        );
    }

    @Test
    void shouldFindAllOvertimeWIthSuccessWithThreeResults() {
        //Given
        //When
        List<OvertimeResponseDto> all = overtimeFacade.findAll();
        //Then
        assertEquals(3, all.size());
    }

    @Test
    void shouldFindAllOvertimeWIthSuccessWithGivenMonth() {
        //Given
        LocalDate date1 = LocalDate.parse("2023-10-09");
        String status1 = "nadgodziny";
        int hours1 = 5;
        OvertimeCreateDto overtimeCreateDto1 = new OvertimeCreateDto(date1, status1, hours1);
        LocalDate date2 = LocalDate.parse("2023-09-12");
        String status2 = "nadgodziny";
        int hours2 = 5;
        OvertimeCreateDto overtimeCreateDto2 = new OvertimeCreateDto(date2, status2, hours2);
        overtimeFacade.createOvertimeAndSaveToDb(overtimeCreateDto1);
        overtimeFacade.createOvertimeAndSaveToDb(overtimeCreateDto2);

        //When
        int yearToFind = 2023;
        int monthToFind = 9;
        List<OvertimeResponseDto> result = overtimeFacade.findByMonth(yearToFind, monthToFind);

        //Then
        assertAll(
                ()->assertThat(result.size()).isEqualTo(2),
                ()->assertThat(result.get(0).duration()).isEqualTo(5),
                ()->assertThat(result.get(0).overtimeDate().getDayOfMonth()).isEqualTo(12)
        );
    }


    @Test
    void shouldFindAllOvertimeWIthSuccessWithGivenStatus() {
        //Given
        int year = 2023;
        String status = "nadgodziny";

        //When
        List<OvertimeResponseDto> result = overtimeFacade.findByStatusAndYear(year,status);

        //Then
        assertAll(
                ()->assertThat(result.size()).isEqualTo(1),
                ()->assertThat(result.get(0).status().equals("nadgodziny")),
                ()->assertThat(result.get(0).overtimeDate().getYear() == 2023)

        );
    }

    @Test
    void shouldSumAllHoursByGivenMonth() {
        //Given
        int year = 2023;
        int month = 9;

        //When
        int result = overtimeFacade.sumOfAllOvertimeHoursByMonth(year,month);
        //Then
        assertAll(
                ()->assertThat(result).isNotNull(),
                ()->assertThat(result).isEqualTo(5)
        );
    }

    @Test
    void shouldSumAllHoursByGivenStatusInGivenMonth() {
        //Given
        LocalDate date1 = LocalDate.parse("2023-09-20");
        String status1 = "nadgodziny";
        int hours1 = 5;
        OvertimeCreateDto overtimeCreateDto = new OvertimeCreateDto(date1, status1, hours1);
        overtimeFacade.createOvertimeAndSaveToDb(overtimeCreateDto);

        //When
        int year = 2023;
        int month = 9;
        String status = "nadgodziny";
        int result = overtimeFacade.sumByGivenStatusOfGivenMonth(year,month,status);

        //Then
        assertAll(
                ()->assertThat(result).isNotNull(),
                ()->assertThat(result).isEqualTo(10)
        );
    }

    @Test
    void shouldSumAllOvertimesStatisticByYear(){
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaught));
        //When
        overtimeFacade.showStatisticsByYear();

        //Then
        String expectedMessage = "W roku 2023 zrobiono 5 dodatkowych godzin\n" +
                "W roku 2024 zrobiono 13 dodatkowych godzin".trim();

        String result = outputStreamCaught.toString().trim();
        System.out.println(result);
        assertEquals(expectedMessage,result);
        System.setOut(System.out);
    }

    @Test
    void shouldDeleteOvertimeByGivenId(){
        //Given
        List<OvertimeResponseDto> listBeforeDelete = overtimeFacade.findAll();
        OvertimeResponseDto overtimeResponseDto = listBeforeDelete.get(0);

        //When
        long id = overtimeResponseDto.id();
        overtimeFacade.deleteOvertimeById(id);
        List<OvertimeResponseDto> listAfterDelete = overtimeFacade.findAll();

        //Then
        assertAll(
                ()-> assertThat(listBeforeDelete.size()).isEqualTo(3),
                ()-> assertThat(listAfterDelete.size()).isEqualTo(2)
        );
    }
}
