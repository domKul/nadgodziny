package dominik.nadgodziny.infrastructure.console;

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

    private final Scanner sc = new Scanner(System.in);
    private final OvertimeConsoleFacade overtimeConsoleFacade;
    private final ConfigurableApplicationContext applicationContext;

    public void runAppMain() {
        try {
            do {
                overtimeConsoleFacade.initialInfo();
                int nextInt = inputNumber();
                if (initialChoice(nextInt)) return;
            } while (true);
        } catch (Exception e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    public void runAppFind() {
        try {
            do {
                overtimeConsoleFacade.initialFind();
                int nextInt = inputNumber();
                if (whatNextToFind(nextInt)) return;
            } while (true);
        } catch (Exception e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    private int inputNumber() {
        int nextInt = 0;
        try {
            nextInt = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            printText("\nMusisz wybrać cyfrę.");
            sc.nextLine();
        }
        return nextInt;
    }

//    boolean whatNext(int nextInt) {
//        try {
//            switch (nextInt) {
//                case 1 -> overtimeConsoleFacade.createOvertimeAndSaveToDb(sc);
//                case 2 -> overtimeConsoleFacade.findAll();
//                case 3 -> {
//                    printText("Zamykanie aplikacji...");
//                    applicationContext.close();
//                    return true;
//                }
//                case 4 -> overtimeConsoleFacade.findByMonth(sc);
//                case 5 -> overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(sc);
//                case 6 -> overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(sc);
//            }
//        } catch (InputMismatchException e) {
//            printText("Zle dane wejsciowe");
//        }
//        return false;
//    }


        boolean whatNextToFind(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> overtimeConsoleFacade.findAll();
                case 2 -> overtimeConsoleFacade.findByMonth(sc);
                case 3 -> overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(sc);
                case 4 -> overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(sc);
                case 5 -> runAppMain();
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
        return false;
    }

    boolean initialChoice(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> overtimeConsoleFacade.createOvertimeAndSaveToDb(sc);
                case 2 -> runAppFind();
                case 3 -> {
                    sc.close();
                    printText("Zamykanie aplikacji...");
                    applicationContext.close();
                    return true;
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
        return false;
    }
}
