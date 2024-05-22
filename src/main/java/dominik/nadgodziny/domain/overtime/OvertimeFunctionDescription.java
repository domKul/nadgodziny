package dominik.nadgodziny.domain.overtime;

 interface OvertimeFunctionDescription {

    default void initialMenu() {
        ConsoleWriter.printText("\n\n\nWybierz opcje" +
                "\n 1-Dodaj nadgodziny " +
                "\n 2-Wyszukaj nadgodziny" +
                "\n 3-Zakoncz ");
    }

     default void initialMenuFind() {
         ConsoleWriter.printText("\n\n\nWybierz opcje" +
                 "\n 1-Lista wszystkich nadgodzin " +
                 "\n 2-Znajdz nadgodziny w danym miesiacu" +
                 "\n 3-Suma godzin w danym miesiacu" +
                 "\n 4-Suma nadgodzin z danego rodzaju w danym miesiacu" +
                 "\n 5-Cofnij");
     }

     default void statusSelectionMenu() {
         ConsoleWriter.printText("\nWybierz opcje" +
                 "\n 1-Nadgodziny " +
                 "\n 2-Zlecenie");
     }
}
