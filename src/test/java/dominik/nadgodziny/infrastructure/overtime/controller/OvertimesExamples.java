package dominik.nadgodziny.infrastructure.overtime.controller;

import dominik.nadgodziny.NadgodzinyApplication;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = NadgodzinyApplication.class)
@ActiveProfiles("integration")
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class OvertimesExamples {

    protected List<OvertimeResponseDto> mockOvertimeResponseDtoList;

    @Autowired
     OvertimeFacade overtimeFacade;


    List<OvertimeResponseDto> twoOvertimesExamplesWithStatusZlecenie() {
        return mockOvertimeResponseDtoList = Arrays.asList(
                new OvertimeResponseDto(0, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), "zlecenie", 8),
                new OvertimeResponseDto(0, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), "zlecenie", 8)
        );
    }

    List<OvertimeResponseDto> twoOvertimesExamplesWithStatusNadgodziny() {
        return mockOvertimeResponseDtoList = Arrays.asList(
                new OvertimeResponseDto(0, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), "nadgodziny", 8),
                new OvertimeResponseDto(0, LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-01"), "nadgodziny", 8)
        );
    }

    void overtimesLoopAdder(List<OvertimeResponseDto>overtimeResponseDtoList) {
        for (OvertimeResponseDto overtimeResponseDto : overtimeResponseDtoList) {
            overtimeFacade.createOvertimeAndSaveToDb(overtimeResponseDto.overtimeDate(),
                    overtimeResponseDto.status(),
                    overtimeResponseDto.duration());
        }
    }

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }


}
