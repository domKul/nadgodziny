package integration.feature;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import dominik.nadgodziny.infrastructure.overtime.console.OvertimeMainControlLoop;
import integration.InitIntegrationTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class ScenarioUserConsole extends InitIntegrationTestData {

    private final ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Scanner scanner = new Scanner(System.in);
    OvertimeMainControlLoop mockControlLoop;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaught));
        MockitoAnnotations.openMocks(this);
        mockControlLoop = Mockito.spy(overtimeMainControlLoop);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void userAddAndFindOvertimeInConsole() {
        LocalDate todaysDate = LocalDate.now();
        beforStartTheyAreZeroRecordsInDb();
        userRunAllAndSeeInitialMenu();
        userEnterToAddOrDeleteMenu();
        userSelectFindOptionFromInitialMenu();
        userSelectAddOrDeleteMenuAndAddFirstOvertimeWithNadgodzinyStatus();
        userWantToFindOvertimesAndFindOneAddedBeforSearch(todaysDate);
        userWantToFindOvertimesAndFind3OvertimesInDb(todaysDate);
        userWantToFindOvertimeByStatusZlecenieAndFindZeroResultResposnse();
        userWantToAddOvertimesWithZlecenieStatus();
        userWantToSumeAllOvertimesByGivenMonthAndYear();
    }

    private void beforStartTheyAreZeroRecordsInDb() {
        // 1.0 There are no overtimes in the db
        // Given
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                () -> overtimeFacade.findAll());
        // When
        String expectedMessage = ErrorMessages.NOT_FOUND.getMessage();
        // Then
        assertEquals(expectedMessage, notFoundException.getMessage());
    }

    private void userRunAllAndSeeInitialMenu() {
        // 1.1 User runs the app and wants to see initial menu options
        // Given
        outputStreamCaught.reset();
        // When
        mockControlLoop.runAppMain();

        // Then
        String expectedOutput = "Wybierz opcje\n" +
                " 1-Dodaj / Usun \n" +
                " 2-Wyszukaj nadgodziny\n" +
                " 3-Statystyki\n" +
                " 4-Zakoncz \n" +
                " 5-Exportuj do CSV";

        String actualOutput = outputStreamCaught.toString();
        if (actualOutput.endsWith("null")) {
            actualOutput = actualOutput.substring(0, actualOutput.length() - 4).trim();
        }
        assertTrue(actualOutput.contains(expectedOutput.trim()));
    }

    private void userEnterToAddOrDeleteMenu() {
        // 1.2 User selects 1 to enter add/delete menu
        // Given
        outputStreamCaught.reset();
        doReturn(1).doReturn(3).when(mockControlLoop).inputNumber();

        // When
        mockControlLoop.runAppMain();

        // Then
        String expectedMenuOutput = "Wybierz opcje" +
                "\n 1-Dodaj " +
                "\n 2-Usun" +
                "\n 3-Cofnij";
        String actualMenuOutput = outputStreamCaught.toString().trim();
        assertTrue(actualMenuOutput.contains(expectedMenuOutput.trim()));
    }

    private void userSelectFindOptionFromInitialMenu() {
        // 1.3 User selects 2 to enter find overtimes menu
        // Given
        outputStreamCaught.reset();
        doReturn(2).doReturn(6).when(mockControlLoop).inputNumber();

        // When
        mockControlLoop.runAppMain();

        // Then
        String expectedMenuOutput2 = "Wybierz opcje" +
                "\n 1-Lista wszystkich nadgodzin " +
                "\n 2-Znajdz nadgodziny w danym miesiacu" +
                "\n 3-Wyszukaj nadgodziny z danym statusem" +
                "\n 4-Suma godzin w danym miesiacu" +
                "\n 5-Suma nadgodzin z danego rodzaju w danym miesiacu" +
                "\n 6-Cofnij";
        String actualMenuOutput2 = outputStreamCaught.toString().trim();
        assertTrue(actualMenuOutput2.contains(expectedMenuOutput2.trim()));
    }

    private void userSelectAddOrDeleteMenuAndAddFirstOvertimeWithNadgodzinyStatus() {
        // 1.4 User select add/delete menu and save overtime to empty db
        // Given
        String inputData = "\n1\n1\n2023-12-12\n1\n8\n";
        userInput(inputData,outputStreamCaught);

        // When
        overtimeMainControlLoop.runAppMain();

        // Then
        List<OvertimeResponseDto> listWithOneOvertime = overtimeFacade.findAll();
        assertEquals(1, listWithOneOvertime.size());
        OvertimeResponseDto addedOvertime = listWithOneOvertime.get(0);
        assertAll(
                () -> assertEquals(LocalDate.parse("2023-12-12"), addedOvertime.overtimeDate()),
                () -> assertEquals(8, addedOvertime.duration())
        );
    }

    private void userWantToFindOvertimesAndFindOneAddedBeforSearch(LocalDate todaysDate) {
        // 1.5 user want to find all overtimes and find 1 overtime after adding it
        // Given
        outputStreamCaught.reset();
        String inputData2 = "\n2\n1\n";
        userInput(inputData2, outputStreamCaught);

        // When
        overtimeMainControlLoop.runAppMain();

        // Then
        String output = outputStreamCaught.toString();
        assertAll(
                () -> assertThat(output).contains("ID 1 ||  wpisano " + todaysDate + " || " +
                        " data nadgodzin 2023-12-12 ||  rodzaj nadgodziny || " +
                        " czas pracy 8 godzin")
        );
    }

    private void userWantToFindOvertimesAndFind3OvertimesInDb(LocalDate todaysDate) {
        //1.5 they are two more overtimes in db and want to find all (3 overtimes output)
        // Given
        outputStreamCaught.reset();
        additionalTwoOvertimesAdderWith2023Year();
        String inputData3 = "\n2\n1\n";
        userInput(inputData3, outputStreamCaught);

        // When
        overtimeMainControlLoop.runAppMain();

        // Then
        String output = outputStreamCaught.toString();
        String expectedListOfOvertimes = "ID 1 ||  wpisano " + todaysDate + " ||  data nadgodzin 2023-12-12 ||  rodzaj nadgodziny ||  czas pracy 8 godzin \n" +
                "ID 2 ||  wpisano " + todaysDate + " ||  data nadgodzin 2023-12-01 ||  rodzaj nadgodziny ||  czas pracy 8 godzin \n" +
                "ID 3 ||  wpisano " + todaysDate + " ||  data nadgodzin 2023-12-02 ||  rodzaj nadgodziny ||  czas pracy 8 godzin ";
        assertAll(
                () -> assertThat(output).contains(expectedListOfOvertimes)
        );
    }

    private void userWantToFindOvertimeByStatusZlecenieAndFindZeroResultResposnse() {
        //1.6 user want to find overtimes with status "zlecenie" in 2023 year and they are 0 in db with messale "Znaleziono 0"
        //Given
        outputStreamCaught.reset();
        String inputData4 = "\n2\n3\n2023\n2";
        userInput(inputData4, outputStreamCaught);

        //When
        overtimeMainControlLoop.runAppMain();

        //Then
        String output = outputStreamCaught.toString();
        String expectedResposne = "Znaleziono 0";
        assertThat(output).contains(expectedResposne);
    }

    private void userWantToAddOvertimesWithZlecenieStatus(){
        //1.7 user add new overtime with status zlecenie to db
        //Given
        outputStreamCaught.reset();
        String inputData = "\n1\n1\n2023-12-12\n2\n8\n";
        userInput(inputData,outputStreamCaught);

        //When
        overtimeMainControlLoop.runAppMain();

        //Then
        List<OvertimeResponseDto> all = overtimeFacade.findByStatusAndYear(2023,"zlecenie");
        int expectedSize = 1;
        assertThat(expectedSize).isEqualTo(all.size());
    }

    private void userWantToSumeAllOvertimesByGivenMonthAndYear(){
        //Given
        outputStreamCaught.reset();
        String inputData = "\n2\n4\n2023\n12\n";
        userInput(inputData,outputStreamCaught);

        //When
        overtimeMainControlLoop.runAppMain();

        //Then
        String output = outputStreamCaught.toString();
        String expectedOutput= "Łączna suma godzin to 32";
        assertThat(output).contains(expectedOutput);
    }
}