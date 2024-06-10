package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.OvertimeConsoleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

@Component
@RequiredArgsConstructor
public class OvertimeMainControlLoop {

    private final OvertimeConsoleFacade overtimeConsoleFacade;
    private final ConfigurableApplicationContext applicationContext;
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
            overtimeConsoleFacade.initialInfo();
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
            overtimeConsoleFacade.initialInfo();
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
                overtimeConsoleFacade.initialFind();
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
                case 1 -> overtimeConsoleFacade.findAll();
                case 2 -> overtimeConsoleFacade.findByMonth(sc);
                case 3 -> overtimeConsoleFacade.findByStatusAndYear(sc);
                case 4 -> overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(sc);
                case 5 -> overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(sc);
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
                overtimeConsoleFacade.initialAddOrDelete();
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
                case 1 -> overtimeConsoleFacade.createOvertimeAndSaveToDb(sc);
                case 2 -> overtimeConsoleFacade.deleteOvertimeById(sc);
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
