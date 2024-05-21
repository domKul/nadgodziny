package dominik.nadgodziny.domain.overtime;

 interface OvertimeFunctionDescription {

    default void initialMenu() {
        ConsoleWriter.printText("\n\n\nWybierz opcje" +
                "\n 1-dodaj " +
                "\n 2-lista wszystkich nadgodzin " +
                "\n 3-zakoncz " +
                "\n 4-znajdz nadgodziny w danym miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin z danego rodzaju w danym miesiacu");
    }

     default void statusSelectionMenu() {
         ConsoleWriter.printText("\nWybierz opcje" +
                 "\n 1-nadgodziny " +
                 "\n 2-zlecenie");
     }
}
