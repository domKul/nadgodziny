package dominik.nadgodziny.infrastructure.overtime.export.csv;

public interface CsvConverter {

    void writeOvertimesToCSV();

    default String[] csvHeader() {
        return new String[]{"id", "overtimeDate", "duration", "status"};
    }

}

