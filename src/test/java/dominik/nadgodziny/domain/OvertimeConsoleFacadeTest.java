package dominik.nadgodziny.domain;

import dominik.nadgodziny.domain.dto.OvertimeResponseDto;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OvertimeConsoleFacadeTest {

    @Test
    void shouldCreateOvertimeAndSaveToDbSuccessfully() {
        // Given
        Scanner scanner = new Scanner("2023-09-09\nnadgodziny\n5\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        // When
        OvertimeResponseDto overtimeResponseDto = overtimeConsoleFacade.createOvertimeAndSaveToDb(scanner);
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();

        // Then
        assertAll(
                ()->assertThat(all.size()).isEqualTo(1),
                () -> assertThat(overtimeResponseDto).isNotNull(),
                () -> assertThat(overtimeResponseDto.overtimeDate().toString()).isEqualTo("2023-09-09"),
                () -> assertThat(overtimeResponseDto.duration()).isEqualTo(5)
        );
    }

    @Test
    void shouldFindAllOvertimeWIthSuccessWithZeroResults() {
        //Given
        OvertimeConsoleFacade facade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        //When
        List<OvertimeResponseDto> all = facade.findAll();
        //Then
        assertEquals(0, all.size());
    }

    @Test
    void shouldFindAllOvertimeWIthSuccessWithFourResults() {
        //Given
        Scanner scanner1 = new Scanner("2023-09-09\nnadgodziny\n5\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        overtimeConsoleFacade.createOvertimeAndSaveToDb(scanner1);
        //When
        List<OvertimeResponseDto> all = overtimeConsoleFacade.findAll();
        //Then
        assertEquals(1, all.size());
    }

    @Test
    void shouldFindAllOvertimeWIthSuccessWithGivenMonth() {
        //Given
        Scanner overtime1 = new Scanner("2023-10-09\nnadgodziny\n5\n");
        Scanner overtime2= new Scanner("2023-09-12\nnadgodziny\n5\n");
        Scanner monthToFind= new Scanner("9\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        List<OvertimeResponseDto> retuslt = overtimeConsoleFacade.findByMonth(monthToFind);
        //Then
        assertAll(
                ()->assertThat(retuslt.size()).isEqualTo(1),
                ()->assertThat(retuslt.get(0).duration()).isEqualTo(5),
                ()->assertThat(retuslt.get(0).overtimeDate().getDayOfMonth()).isEqualTo(12)
        );
    }

    @Test
    void shouldSumAllHoursByGivenMonth() {
        //Given
        Scanner overtime1 = new Scanner("2023-10-09\nnadgodziny\n5\n");
        Scanner overtime2= new Scanner("2023-10-12\nnadgodziny\n5\n");
        Scanner monthToFind= new Scanner("10\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        int retuslt = overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(monthToFind);
        //Then
        assertAll(
                ()->assertThat(retuslt).isNotNull(),
                ()->assertThat(retuslt).isEqualTo(10)
        );
    }

    @Test
    public void ShouldPrintInitialInfo() {
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        System.setOut(new PrintStream(outputStreamCaught));

        //When
        overtimeConsoleFacade.initialInfo();

        //Then
        String expectedOutput = "\n\n\nWybierz opcje" +
                " \n 1-dodaj " +
                "\n 2-wszystkie " +
                "\n 3-zakoncz " +
                "\n 4-znajdz po miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin o z danego rodzaju w danym miesiacu";

        String expectedTrimmed = expectedOutput.trim();
        String actualTrimmed = outputStreamCaught.toString().trim();
        assertEquals(expectedTrimmed, actualTrimmed);
        System.setOut(System.out);
    }
}
