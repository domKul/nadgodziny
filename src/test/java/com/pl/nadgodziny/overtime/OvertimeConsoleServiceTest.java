package com.pl.nadgodziny.overtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OvertimeConsoleServiceTest {

    @InjectMocks
    private OvertimeConsoleService overtimeConsoleService;

    @Mock
    private OvertimeService overtimeService;
    private  ConfigurableApplicationContext applicationContext;

    private OvertimeRepository overtimeRepository;

    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
        overtimeService = Mockito.mock(OvertimeService.class);
        overtimeConsoleService = new OvertimeConsoleService(applicationContext, overtimeService);
    }

    @Test
    void shouldCreateOvertimeAndSaveToDb() {
        // Given
        Scanner scanner = new Scanner("2023-09-09\nnadgodziny\n5\n");

        doNothing().when(overtimeService).addNewOvertime(any(Overtime.class));

        // When
        overtimeConsoleService.createOvertimeAndSaveToDb(scanner);

        // Then
        verify(overtimeService, times(1)).addNewOvertime(any(Overtime.class));
    }

    @Test
    void shouldHandleDateTimeParseExceptionWhenCreatingObj() {
        // Given
        Scanner scanner = new Scanner("NieprawidłowaData\nnadgodziny\n5\n");

        // When
        Exception exception = assertThrows(DateTimeParseException.class, () -> {
            overtimeConsoleService.createOvertimeObject(scanner);
        });

        // Then
        assertEquals(" Zły format daty.", exception.getMessage());
    }
}
