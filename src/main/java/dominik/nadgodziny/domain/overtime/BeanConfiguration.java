package dominik.nadgodziny.domain.overtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfiguration {

    @Bean
    OvertimeReaderService overtimeReaderService(OvertimeRepository overtimeRepository) {
        return new OvertimeReaderService(overtimeRepository);
    }

    @Bean
    OvertimeReportingService overtimeReportingService(OvertimeRepository overtimeRepository) {
        return new OvertimeReportingService(overtimeRepository);
    }

    @Bean
    OvertimeStatisticsService overtimeStatisticsService(OvertimeReaderService overtimeReaderService) {
        return new OvertimeStatisticsService(overtimeReaderService);
    }


    @Bean
    OvertimeFacade overtimeConsoleFacade(OvertimeReportingService overtimeReportingService,
                                         OvertimeReaderService overtimeReaderService,
                                         OvertimeStatisticsService overtimeStatisticsService) {
        return new OvertimeFacade(overtimeStatisticsService, overtimeReportingService, overtimeReaderService);
    }

}
