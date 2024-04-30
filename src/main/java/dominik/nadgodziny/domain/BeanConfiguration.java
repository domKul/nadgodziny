package dominik.nadgodziny.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
    OvertimeConsoleFacade overtimeConsoleFacade(OvertimeReportingService overtimeRepository,
                                                OvertimeReaderService overtimeReader){
        return new OvertimeConsoleFacade(overtimeReader,overtimeRepository);
    }

}
