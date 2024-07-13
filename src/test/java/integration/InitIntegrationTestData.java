package integration;

import dominik.nadgodziny.NadgodzinyApplication;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.infrastructure.overtime.console.OvertimeMainControlLoop;
import dominik.nadgodziny.infrastructure.overtime.export.csv.CsvConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

@SpringBootTest(classes = NadgodzinyApplication.class)
@ActiveProfiles("integration")
@Testcontainers
@AutoConfigureMockMvc
public abstract class InitIntegrationTestData {

    @Autowired
    protected OvertimeFacade overtimeFacade;
    @Autowired
    protected OvertimeMainControlLoop overtimeMainControlLoop;
    @Autowired
    protected OvertimeFacade overtimeConsoleFacade;
    @Autowired
    protected ConfigurableApplicationContext applicationContext;
    @Autowired
    protected CsvConverter csvConverter;
    protected Scanner scanner;


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

    protected void additionalTwoOvertimesAdderWith2023Year(){
        int day = 1;
        for(int i = 1; i < 3; i++){
        LocalDate date = LocalDate.parse("2023-12-0" + day++);
            overtimeFacade.createOvertimeAndSaveToDb(date,"nadgodziny",8);
        }
    }

    protected void userInput(String input, ByteArrayOutputStream out) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(testIn);
        System.setOut(new PrintStream(out));
        overtimeMainControlLoop.setScanner(scanner);
    }

}
