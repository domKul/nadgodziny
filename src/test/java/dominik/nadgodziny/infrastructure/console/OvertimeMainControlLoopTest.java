package dominik.nadgodziny.infrastructure.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



class OvertimeMainControlLoopTest {

    @InjectMocks
    OvertimeMainControlLoop overtimeMainControlLoop;
    @Mock
    ConfigurableApplicationContext applicationContext;


    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExitFromProgramByGivenNumberAndCloseApplicationContext() {
        //Given
        Scanner scanner = new Scanner("4");

        // When
        overtimeMainControlLoop.initialChoice(scanner.nextInt());

        // Then
        verify(applicationContext, times(1)).close();
    }
}
