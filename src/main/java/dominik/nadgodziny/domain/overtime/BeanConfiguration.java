package dominik.nadgodziny.domain.overtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfiguration {

    @Bean
    OvertimeReaderService overtimeService(OvertimeRepository overtimeRepository){
        return new OvertimeReaderService(overtimeRepository);
    }

    @Bean
    OvertimeReportingService overtimeReportingService(OvertimeRepository overtimeRepository){
        return new OvertimeReportingService(overtimeRepository);
    }
}
