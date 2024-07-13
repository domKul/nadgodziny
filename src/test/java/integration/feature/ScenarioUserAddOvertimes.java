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

import java.io.ByteArrayInputStream;
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

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaught));
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void userswapBetweenOptionsInMenu() {
        // 1.0 There are no overtimes in the db
        // Given
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                () -> overtimeFacade.findAll());
        // When
        String expectedMessage = ErrorMessages.NOT_FOUND.getMessage();
        // Then
        assertEquals(expectedMessage, notFoundException.getMessage());
        // 1.1 User runs the app and wants to see initial menu options
        // Given
        outputStreamCaught.reset();
        OvertimeMainControlLoop mockControlLoop = Mockito.spy(overtimeMainControlLoop);
        // When
        mockControlLoop.runAppMain();

        // Then
        String expectedOutput = "Wybierz opcje\n" +
                " 1-Dodaj / Usun \n" +
                " 2-Wyszukaj nadgodziny\n" +
                " 3-Statystyki\n" +
                " 4-Zakoncz \n" +
                " 5-Exportuj do CSV";

        String actualOutput = outputStreamCaught.toString().trim();
        if (actualOutput.endsWith("null")) {
            actualOutput = actualOutput.substring(0, actualOutput.length() - 4).trim();
        }
        assertTrue(actualOutput.contains(expectedOutput.trim()));

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

        // 1.4 User select add/delete menu and save overtime to empty db
        // Given
        String inputData = "\n1\n1\n2023-12-12\n1\n8\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(inputData.getBytes());
        scanner = new Scanner(testIn);
        overtimeMainControlLoop.setScanner(scanner);

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

        // 1.5 user want to find all overtimes and find 1 overtime after adding it
        // Given
        outputStreamCaught.reset();
        String inputData2 = "\n2\n1\n";
        ByteArrayInputStream testIn2 = new ByteArrayInputStream(inputData2.getBytes());
        ByteArrayOutputStream testOut2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut2));
        scanner = new Scanner(testIn2);
        overtimeMainControlLoop.setScanner(scanner);

        // When
        overtimeMainControlLoop.runAppMain();

        // Then
        System.setOut(originalOut);
        String output2 = testOut2.toString();
        LocalDate todaysDate = LocalDate.now();
        assertAll(
                ()-> assertThat(output2).contains("ID 1 ||  wpisano " + todaysDate + " || " +
                        " data nadgodzin 2023-12-12 ||  rodzaj nadgodziny || " +
                        " czas pracy 8 godzin")
        );

        //1.5 they are two more overtimes in db and want to find all (3 overtimes output)
        // Given
        outputStreamCaught.reset();
        additionalTwoOvertimesAdder();
        String inputData3 = "\n2\n1\n";
        ByteArrayInputStream testIn3 = new ByteArrayInputStream(inputData3.getBytes());
        ByteArrayOutputStream testOut3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut3));
        scanner = new Scanner(testIn3);
        overtimeMainControlLoop.setScanner(scanner);

        // When
        overtimeMainControlLoop.runAppMain();

        // Then
        System.setOut(originalOut);
        String output3 = testOut3.toString();
        String expectedListOfOvertimes = "ID 1 ||  wpisano " + todaysDate +" ||  data nadgodzin 2023-12-12 ||  rodzaj nadgodziny ||  czas pracy 8 godzin \n" +
                "ID 2 ||  wpisano " + todaysDate + " ||  data nadgodzin 2023-12-01 ||  rodzaj nadgodziny ||  czas pracy 8 godzin \n" +
                "ID 3 ||  wpisano " + todaysDate + " ||  data nadgodzin 2023-12-02 ||  rodzaj nadgodziny ||  czas pracy 8 godzin ";
        assertAll(
                ()-> assertThat(output3).contains(expectedListOfOvertimes)
        );
    }
}