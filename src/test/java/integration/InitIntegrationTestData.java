package integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class InitIntegrationTestData {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

}
