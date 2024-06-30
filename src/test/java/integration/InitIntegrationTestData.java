package integration;

import dominik.nadgodziny.NadgodzinyApplication;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.infrastructure.overtime.console.OvertimeMainControlLoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = NadgodzinyApplication.class)
@ActiveProfiles("integration")
@Testcontainers
@AutoConfigureMockMvc
public abstract class InitIntegrationTestData {

    @Autowired
    protected OvertimeFacade overtimeFacade;
    @Autowired
    protected OvertimeMainControlLoop overtimeMainControlLoop;


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
