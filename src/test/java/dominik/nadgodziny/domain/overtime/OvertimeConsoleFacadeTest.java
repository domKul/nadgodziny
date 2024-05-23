package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
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
        Scanner scanner = new Scanner("2023-09-09\n1\n5\n");
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
        Scanner scanner1 = new Scanner("2023-09-09\n1\n5\n");
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
        Scanner overtime1 = new Scanner("2023-10-09\n1\n5\n");
        Scanner overtime2= new Scanner("2023-09-12\n1\n5\n");
        Scanner monthToFind= new Scanner("2023\n9\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        List<OvertimeResponseDto> result = overtimeConsoleFacade.findByMonth(monthToFind);
        //Then
        assertAll(
                ()->assertThat(result.size()).isEqualTo(1),
                ()->assertThat(result.get(0).duration()).isEqualTo(5),
                ()->assertThat(result.get(0).overtimeDate().getDayOfMonth()).isEqualTo(12)
        );
    }


    @Test
    void shouldFindAllOvertimeWIthSuccessWithGivenStatus() {
        //Given
        Scanner overtime1 = new Scanner("2023-10-09\n1\n5\n");
        Scanner overtime2= new Scanner("2023-09-12\n1\n5\n");
        Scanner overtime3= new Scanner("2023-09-12\n2\n5\n");
        Scanner monthToFind= new Scanner("2023\n1\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();

        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime3);
        //When
        List<OvertimeResponseDto> result = overtimeConsoleFacade.findByStatusAndYear(monthToFind);
        //Then
        assertAll(
                ()->assertThat(result.size()).isEqualTo(2),
                ()->assertThat(result.get(0).status().equals("nadgodziny")),
                ()->assertThat(result.get(0).overtimeDate().getYear() == 2023),
                ()->assertThat(result.get(1).status().equals("nadgodziny")),
                ()->assertThat(result.get(1).overtimeDate().getYear() == 2023)

        );
    }

    @Test
    void shouldSumAllHoursByGivenMonth() {
        //Given
        Scanner overtime1 = new Scanner("2023-10-09\n1\n5\n");
        Scanner overtime2= new Scanner("2023-10-12\n1\n5\n");
        Scanner monthToFind= new Scanner("2023\n10\n");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        int result = overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(monthToFind);
        //Then
        assertAll(
                ()->assertThat(result).isNotNull(),
                ()->assertThat(result).isEqualTo(10)
        );
    }

    @Test
    void shouldSumAllHoursByGivenStatus() {
        //Given
        Scanner overtime1 = new Scanner("2023-10-09\n1\n5\n");
        Scanner overtime2= new Scanner("2023-10-12\n1\n5\n");
        Scanner monthToFind= new Scanner("2023\n10\n1");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        int result = overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(monthToFind);
        System.out.println(result);
        //Then
        assertAll(
                ()->assertThat(result).isNotNull(),
                ()->assertThat(result).isEqualTo(10)
        );
    }

    @Test
    void shouldSumAllHoursByGivenStatusInGivenMonth() {
        //Given
        Scanner overtime1 = new Scanner("2023-01-09\n1\n5\n");
        Scanner overtime2= new Scanner("2023-10-12\n1\n5\n");
        Scanner monthToFind= new Scanner("2023\n10\n1");
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime1);
        overtimeConsoleFacade.createOvertimeAndSaveToDb(overtime2);
        //When
        int result = overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(monthToFind);
        //Then
        assertAll(
                ()->assertThat(result).isNotNull(),
                ()->assertThat(result).isEqualTo(5)
        );
    }



    @Test
    public void ShouldPrintInitialInfoOfMainMenu() {
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        OvertimeConsoleFacade overtimeConsoleFacade = new OvertimeConsoleFacadeConfig().overtimeConsoleFacadeTest();
        System.setOut(new PrintStream(outputStreamCaught));
        //When
        overtimeConsoleFacade.initialInfo();
        //Then
        String expectedOutput = "\n\n\nWybierz opcje" +
                "\n 1-Dodaj / Usun " +
                "\n 2-Wyszukaj nadgodziny" +
                "\n 3-Statystyki" +
                "\n 4-Zakoncz ";

        String expectedTrimmed = expectedOutput.trim();
        String actualTrimmed = outputStreamCaught.toString().trim();
        assertEquals(expectedTrimmed, actualTrimmed);
        System.setOut(System.out);
    }
}
