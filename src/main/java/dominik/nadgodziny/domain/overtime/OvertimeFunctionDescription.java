package dominik.nadgodziny.domain.overtime;

 interface OvertimeFunctionDescription {

    default void initialMenu() {
        ConsoleWriter.printText("\n\n\nWybierz opcje" +
                "\n 1-dodajnadgodziny " +
                "\n 2-wyszukaj nadgodziny" +
                "\n 3-zakoncz ");
    }

     default void initialMenuFind() {
         ConsoleWriter.printText("\n\n\nWybierz opcje" +
                 "\n 1-lista wszystkich nadgodzin " +
                 "\n 2-znajdz nadgodziny w danym miesiacu" +
                 "\n 3-suma godzin w danym miesiacu" +
                 "\n 4-suma nadgodzin z danego rodzaju w danym miesiacu" +
                 "\n 5-cofnij");
     }

     default void statusSelectionMenu() {
         ConsoleWriter.printText("\nWybierz opcje" +
                 "\n 1-nadgodziny " +
                 "\n 2-zlecenie");
     }
}
