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

class OvertimeMainLoopTest {
    @InjectMocks
    OvertimeMainLoop overtimeMainLoop;
    @Mock
    ConfigurableApplicationContext applicationContext;
    @Mock
    OvertimeConsoleFacade overtimeConsoleFacade;


    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
        overtimeMainLoop = Mockito.mock(OvertimeMainLoop.class);
        overtimeMainLoop = new OvertimeMainLoop(overtimeConsoleFacade,applicationContext);
    }

    @Test
    void shouldExitFromProgramByGivenNumber() {
        //Given
        Scanner scanner = new Scanner("3");

        // When
        overtimeMainLoop.whatNext(scanner.nextInt());

        // Then
        verify(applicationContext, times(1)).close();
    }
}
