package dominik.nadgodziny.domain.overtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class OvertimeConsoleFacadeTest {

    @InjectMocks
    private OvertimeConsoleFacade overtimeConsoleFacade;
    @Mock
    private OvertimeReaderService overtimeReaderService;
    @Mock
    private OvertimeReportingService overtimeReportingService;

    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
        overtimeConsoleFacade = new OvertimeConsoleFacade( overtimeReaderService,overtimeReportingService);
    }

    @Test
    void shouldCreateOvertimeAndSaveToDb() {
        // Given
        Scanner scanner = new Scanner("2023-09-09\nnadgodziny\n5\n");

        doNothing().when(overtimeReportingService).addNewOvertime(any(Overtime.class));

        // When
        overtimeConsoleFacade.createOvertimeAndSaveToDb(scanner);

        // Then
        verify(overtimeReportingService, times(1)).createOvertimeObject(scanner);
    }
}
