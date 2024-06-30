package integration.feature;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import dominik.nadgodziny.infrastructure.overtime.console.OvertimeMainControlLoop;
import integration.InitIntegrationTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ScenarioUserAddOvertimes extends InitIntegrationTestData {

    private final ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaught));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void userWantToAddNewOvertimeToDB() {
        // 1.0 There are no overtimes in the db
        // Given
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                () -> overtimeFacade.findAll());
        // When
        String expectedMessage = ErrorMessages.NOT_FOUND.getMessage();
        // Then
        Assertions.assertEquals(expectedMessage, notFoundException.getMessage());
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
        Assertions.assertTrue(actualOutput.contains(expectedOutput.trim()));

        // 1.2 User selects 1 to enter add/delete menu
        // Given
        outputStreamCaught.reset();
        Mockito.doReturn(1).when(mockControlLoop).inputNumber();

        // When
        mockControlLoop.runAppMain();

        // Then
        String expectedMenuOutput = "Wybierz opcje" +
                "\n 1-Dodaj " +
                "\n 2-Usun" +
                "\n 3-Cofnij";
        String actualMenuOutput = outputStreamCaught.toString().trim();
        Assertions.assertTrue(actualMenuOutput.contains(expectedMenuOutput.trim()));

        // 1.3 User selects 2 to enter find overtimes menu
        // Given
        outputStreamCaught.reset();
        Mockito.doReturn(2).when(mockControlLoop).inputNumber();

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
        Assertions.assertTrue(actualMenuOutput2.contains(expectedMenuOutput2.trim()));
    }

}
