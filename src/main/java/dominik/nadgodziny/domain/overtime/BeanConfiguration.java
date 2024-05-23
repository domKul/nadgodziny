package dominik.nadgodziny.domain.overtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfiguration {

    @Bean
    OvertimeReaderService overtimeReaderService(OvertimeRepository overtimeRepository){
        return new OvertimeReaderService(overtimeRepository);
    }

    @Bean
    OvertimeReportingService overtimeReportingService(OvertimeRepository overtimeRepository){
        return new OvertimeReportingService(overtimeRepository);
    }

    @Bean
    OvertimeStatisticsService overtimeStatisticsService(OvertimeReader overtimeReaderService){
        return new OvertimeStatisticsService(overtimeReaderService);
    }


    @Bean
    OvertimeConsoleFacade overtimeConsoleFacade(OvertimeReportingService overtimeRepository,
                                                OvertimeReaderService overtimeReader,
                                                OvertimeStatisticsService overtimeStatisticsService){
        return new OvertimeConsoleFacade(overtimeReader,overtimeRepository,overtimeStatisticsService);
    }

}
