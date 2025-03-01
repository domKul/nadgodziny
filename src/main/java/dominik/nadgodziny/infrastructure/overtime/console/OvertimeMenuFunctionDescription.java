package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.ConsoleWriter;

interface OvertimeMenuFunctionDescription {

    default void initialMenu() {
        ConsoleWriter.printText("\n\nWybierz opcje" +
                "\n 1-Dodaj / Usun " +
                "\n 2-Wyszukaj nadgodziny" +
                "\n 3-Statystyki" +
                "\n 4-Zakoncz " +
                "\n 5-Exportuj do CSV");
    }

     default void initialMenuFind() {
         ConsoleWriter.printText("\n\n\nWybierz opcje" +
                 "\n 1-Lista wszystkich nadgodzin " +
                 "\n 2-Znajdz nadgodziny w danym miesiacu" +
                 "\n 3-Wyszukaj nadgodziny z danym statusem" +
                 "\n 4-Suma godzin w danym miesiacu" +
                 "\n 5-Suma nadgodzin z danego rodzaju w danym miesiacu" +
                 "\n 6-Cofnij");
     }

     default void overtimeStatusSelectionMenu() {
         ConsoleWriter.printText("\nWybierz opcje" +
                 "\n 1-Nadgodziny " +
                 "\n 2-Zlecenie");
     }

     default void initialAddOrDelete() {
         ConsoleWriter.printText("\nWybierz opcje" +
                 "\n 1-Dodaj " +
                 "\n 2-Usun" +
                 "\n 3-Cofnij");
     }

    default void initialCsvExport() {
        ConsoleWriter.printText("\nWybierz opcje" +
                "\n 1-Exportuj do pliku " +
                "\n 2-Cofnij");
    }
}
