package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
class OvertimeReaderService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    @Override
    public List<OvertimeEntity> findAllOvertimes() {
        return executeWithExceptionHandling(() -> {
            List<OvertimeEntity> all = overtimeRepository.findAll();
            if (all.isEmpty()) {
                throw new NotFoundException(ErrorMessages.NOT_FOUND.getMessage());
            }
            return all;
        }, Collections.emptyList());
    }

    @Override
    public List<OvertimeEntity> findAllOvertimesByStatus(int year, String status) {
        return executeWithExceptionHandling(
                () -> overtimeRepository.findAllByYearAndStatus(year, status), Collections.emptyList());

    }

    @Override
    public List<OvertimeEntity> findOvertimeByMonthAndYear(int year, int month) {
        return executeWithExceptionHandling(() -> {
            List<OvertimeEntity> allByYearAndMonth = overtimeRepository.findAllByYearAndMonth(year, month);
            isEmptyConsoleInfo(allByYearAndMonth);
            return allByYearAndMonth;
        }, Collections.emptyList());
    }

    @Override
    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        return executeWithExceptionHandling(() -> findOvertimeByMonthAndYear(year, month).stream()
                .mapToInt(OvertimeEntity::getDuration)
                .sum(), 0);
    }

    @Override
    public int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        return executeWithExceptionHandling(() -> findOvertimeByMonthAndYear(year, month).stream()
                .filter(o -> o.getStatus().equals(status))
                .mapToInt(OvertimeEntity::getDuration)
                .sum(), 0);
    }

    @Override
    public List<OvertimeEntity> sortAllOvertimesById() {
        return executeWithExceptionHandling(() -> {
            List<OvertimeEntity> allOvertimes = findAllOvertimes();
            Stream<OvertimeEntity> sorted = getSorted(allOvertimes);
            isEmptyConsoleInfo(allOvertimes);
            return sorted.toList();
        }, Collections.emptyList());
    }

    Stream<OvertimeEntity> getSorted(List<OvertimeEntity> allOvertimes) {
        return allOvertimes.stream()
                .sorted(Comparator.comparingLong(OvertimeEntity::getId));
    }

    void isEmptyConsoleInfo(List<OvertimeEntity> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            ConsoleWriter.printText("Nie znaleziono danych ");
        }
    }

    private <T> T executeWithExceptionHandling(Supplier<T> action, T defaultValue) {
        try {
            return action.get();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return defaultValue;
        }
    }
}
