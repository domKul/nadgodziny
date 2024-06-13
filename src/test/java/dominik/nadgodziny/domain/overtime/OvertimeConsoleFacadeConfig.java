package dominik.nadgodziny.domain.overtime;

import java.time.LocalDate;
import java.util.List;

class OvertimeConsoleFacadeConfig {


    private final InMemoryDBForTests inMemoryDBForTests;
    private final OvertimeFacadeFetchImpl ovetimeFacadeFetch;

     OvertimeConsoleFacadeConfig() {
        this.ovetimeFacadeFetch = new OvertimeFacadeFetchImpl(List.of(
                new Overtime(LocalDate.parse("2023-09-12"),"nadgodziny",5),
                new Overtime(LocalDate.parse("2024-01-13"),"nadgodziny",5),
                new Overtime(LocalDate.parse("2024-01-14"),"zlecenie",8)
        ));
        this.inMemoryDBForTests = new InMemoryDBForTests();
        inMemoryDBForTests.saveAll(List.of(
                new Overtime(LocalDate.parse("2023-09-12"),"nadgodziny",5),
                new Overtime(LocalDate.parse("2024-01-13"),"nadgodziny",5),
                new Overtime(LocalDate.parse("2024-01-14"),"zlecenie",8)
        ));
    }

    public OvertimeConsoleFacadeConfig(List<Overtime> overtimes) {
        this.inMemoryDBForTests = new InMemoryDBForTests();
        this.ovetimeFacadeFetch = new OvertimeFacadeFetchImpl(overtimes);
    }
    OvertimeFacade overtimeConsoleFacadeTest(){
        return new OvertimeFacade(
                new OvertimeStatisticsService(ovetimeFacadeFetch),
                new OvertimeReportingService(inMemoryDBForTests),
                new OvertimeReaderService(inMemoryDBForTests)
        );
    }
}
