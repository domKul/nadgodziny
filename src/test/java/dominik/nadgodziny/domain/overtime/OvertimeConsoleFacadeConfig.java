package dominik.nadgodziny.domain.overtime;

import java.time.LocalDate;
import java.util.List;

class OvertimeConsoleFacadeConfig {


    private final InMemoryDBForTests inMemoryDBForTests;
    private final OvertimeFacadeFetchImpl ovetimeFacadeFetch;

     OvertimeConsoleFacadeConfig() {
        this.ovetimeFacadeFetch = new OvertimeFacadeFetchImpl(List.of(
                new Overtime(LocalDate.parse("2023-01-12"),"status",8),
                new Overtime(LocalDate.parse("2024-01-13"),"status",8),
                new Overtime(LocalDate.parse("2024-01-14"),"status",8)
        ));
        this.inMemoryDBForTests = new InMemoryDBForTests();
    }

    public OvertimeConsoleFacadeConfig(List<Overtime> overtimes) {
        this.inMemoryDBForTests = new InMemoryDBForTests();
        this.ovetimeFacadeFetch = new OvertimeFacadeFetchImpl(overtimes);
    }
    OvertimeConsoleFacade overtimeConsoleFacadeTest(){
         return new OvertimeConsoleFacade(new OvertimeReaderService(inMemoryDBForTests ),
                 new OvertimeReportingService(inMemoryDBForTests)
         ,new OvertimeStatisticsService(ovetimeFacadeFetch));
    }
}
