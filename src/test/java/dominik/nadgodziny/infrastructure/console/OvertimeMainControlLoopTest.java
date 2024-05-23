package dominik.nadgodziny.infrastructure.console;

import dominik.nadgodziny.domain.overtime.OvertimeConsoleFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    @Mock
    OvertimeConsoleFacade overtimeConsoleFacade;


    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
        overtimeMainControlLoop = Mockito.mock(OvertimeMainControlLoop.class);
        overtimeMainControlLoop = new OvertimeMainControlLoop(overtimeConsoleFacade,applicationContext);
    }

    @Test
    void shouldExitFromProgramByGivenNumber() {
        //Given
        Scanner scanner = new Scanner("4");

        // When
        overtimeMainControlLoop.initialChoice(scanner.nextInt());

        // Then
        verify(applicationContext, times(1)).close();
    }
}
