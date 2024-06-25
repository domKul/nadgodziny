package dominik.nadgodziny.infrastructure.overtime.console;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PromptMenuTest {

    @Autowired
    OvertimeMainControlLoop overtimeMainControlLoop;

    @Test
    public void shouldPrintInitialInfoOfMainMenu() {
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaught));
        //When
        overtimeMainControlLoop.initialMenu();
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

    @Test
    public void shouldPrintInitialInfoOfMainMenuFind() {
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaught));
        //When
        overtimeMainControlLoop.initialMenuFind();
        //Then
        String expectedOutput = "\n\n\nWybierz opcje" +
                "\n 1-Lista wszystkich nadgodzin " +
                "\n 2-Znajdz nadgodziny w danym miesiacu" +
                "\n 3-Wyszukaj nadgodziny z danym statusem" +
                "\n 4-Suma godzin w danym miesiacu" +
                "\n 5-Suma nadgodzin z danego rodzaju w danym miesiacu" +
                "\n 6-Cofnij";

        String expectedTrimmed = expectedOutput.trim();
        String actualTrimmed = outputStreamCaught.toString().trim();
        assertEquals(expectedTrimmed, actualTrimmed);
        System.setOut(System.out);
    }

    @Test
    public void shouldPrintInitialInfoOfCreateAndDeleteMenu() {
        //Given
        ByteArrayOutputStream outputStreamCaught = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaught));
        //When
        overtimeMainControlLoop.initialAddOrDelete();
        //Then
        String expectedOutput = "\nWybierz opcje" +
                "\n 1-Dodaj " +
                "\n 2-Usun" +
                "\n 3-Cofnij";

        String expectedTrimmed = expectedOutput.trim();
        String actualTrimmed = outputStreamCaught.toString().trim();
        assertEquals(expectedTrimmed, actualTrimmed);
        System.setOut(System.out);
    }
}
