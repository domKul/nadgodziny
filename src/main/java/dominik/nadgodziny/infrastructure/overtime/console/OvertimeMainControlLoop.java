package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.infrastructure.overtime.export.csv.CsvConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

@Component
@RequiredArgsConstructor
public class OvertimeMainControlLoop implements OvertimeMenuFunctionDescription {

    private final OvertimeStatisticConsole overtimeStatisticConsole;
    private final ConfigurableApplicationContext applicationContext;
    private final CsvConverter csvConverter;
    private final OvertimeReaderConsole overtimeReaderConsole;
    private final OvertimeReportingConsole overtimeReportingConsole;
    private Scanner sc = new Scanner(System.in);

     public void setScanner(Scanner sc) {
        this.sc = sc;
    }

    public int inputNumber() {
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

    public void initialChoice(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> runAppAddOrDelete();
                case 2 -> runAppFind();
                case 3 -> runAppStatistics();
                case 4 -> {
                    printText("Zamykanie aplikacji...");
                    applicationContext.close();
                }
                case 5 -> {
                    runCsvExporter();
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
    }

    public void runAppStatistics() {
        try {
            initialMenu();
            overtimeStatisticConsole.getOvertimeStatistics();
            int nextInt = inputNumber();
            initialChoice(nextInt);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    public void runCsvExporter() {
        try {
            do {
                initialCsvExport();
                int nextInt = inputNumber();
                csvConverter.writeOvertimesToCSV();
                if (getCsvOption(nextInt)) return;
            } while (true);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    public boolean getCsvOption(final int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> runCsvExporter();
                case 2 -> {
                    runAppMain();
                    return true;
                }
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }
        return false;
    }

    public void runAppFind() {
        try {
            int nextInt;
            do {
                initialMenuFind();
                nextInt = inputNumber();
                if (getNextOptionFind(nextInt)) return;
            } while (true);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

    public boolean getNextOptionFind(int nextInt) {
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

    public void runAppAddOrDelete() {
        try {
            int nextInt;
            do {
                initialAddOrDelete();
                nextInt = inputNumber();
                if (getNextOptionAddOrDelete(nextInt)) return;
            } while (true);
        } catch (InputMismatchException e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }


    public boolean getNextOptionAddOrDelete(int nextOption) {
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
