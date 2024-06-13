package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

@Component
@RequiredArgsConstructor
public class OvertimeMainControlLoop implements OvertimeMenuFunctionDescription {

    private final OvertimeFacade overtimeConsoleFacade;
    private final ConfigurableApplicationContext applicationContext;
    private final OvertimeReaderConsole overtimeReaderConsole;
    private final OvertimeReportingConsole overtimeReportingConsole;
    private final Scanner sc = new Scanner(System.in);

    int inputNumber() {
        while (true) {
            try {
                int nextInt = sc.nextInt();
                sc.nextLine();
                return nextInt;
            } catch (InputMismatchException e) {
                printText("\nMusisz wybrać cyfrę.");
                sc.nextLine();
            }
        }
    }

    public void runAppMain() {
        try {
            initialMenu();
            int nextInt = inputNumber();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            initialChoice(nextInt);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        } catch (Exception e) {
            printText(e.getMessage());
        }
    }

    void initialChoice(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> runAppAddOrDelete();
                case 2 -> runAppFind();
                case 3 -> runAppStatistics();
                case 4 -> {
                    printText("Zamykanie aplikacji...");
                    applicationContext.close();
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
    }

    void runAppStatistics() {
        try {
            initialMenu();
            overtimeConsoleFacade.showStatisticsByYear();
            int nextInt = inputNumber();
            initialChoice(nextInt);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    void runAppFind() {
        try {
            do {
                initialMenuFind();
                int nextInt = inputNumber();
                if (getNextOptionFind(nextInt)) return;
            } while (true);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    boolean getNextOptionFind(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> overtimeReaderConsole.findAllSorted();
                case 2 -> overtimeReaderConsole.findOvertimeByMonth(sc);
                case 3 -> overtimeReaderConsole.findOvertimeByStatus(sc);
                case 4 -> overtimeReaderConsole.getSumOfAllOvertimeHoursByMonthAndYear(sc);
                case 5 -> overtimeReaderConsole.getSumByGivenStatusOfGivenMonthWithYear(sc);
                case 6 -> {
                    runAppMain();
                    return true;
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
        return false;
    }

    void runAppAddOrDelete() {
        try {
            do {
                initialAddOrDelete();
                int nextInt = inputNumber();
                if (getNextOptionAddOrDelete(nextInt)) return;
            } while (true);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    boolean getNextOptionAddOrDelete(int nextOption) {
        try {
            switch (nextOption) {
                case 1 -> overtimeReportingConsole.createOvertimeObjectFromConsole(sc);
                case 2 -> overtimeReportingConsole.removeOvertimeFromConsole(sc);
                case 3 -> {
                    runAppMain();
                    return true;
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
        return false;
    }
}
