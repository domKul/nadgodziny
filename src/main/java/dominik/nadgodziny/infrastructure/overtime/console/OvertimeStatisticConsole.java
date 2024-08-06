package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.ConsoleWriter;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
class OvertimeStatisticConsole {

    private final OvertimeFacade overtimeFacade;

    void getOvertimeStatistics() {
        for (Map.Entry<Integer, Integer> integerIntegerEntry : overtimeFacade.showStatisticsByYear().stats().entrySet()) {
            ConsoleWriter.printText("W roku " + integerIntegerEntry.getKey()
                    + " zrobiono " + integerIntegerEntry.getValue() + " dodatkowych godzin");

        }
    }
}
