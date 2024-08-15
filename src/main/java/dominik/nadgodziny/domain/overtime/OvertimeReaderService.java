package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
class OvertimeReaderService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    @Override
    public List<OvertimeEntity> findAllOvertimes() {
        return executeWithExceptionHandlingInConsole(() -> {
            List<OvertimeEntity> all = overtimeRepository.findAll();
            isEmptyOvertimesList(all);
            return all;
        });
    }

    @Override
    public List<OvertimeEntity> findAllOvertimesByStatus(int year, String status) {
        return executeWithExceptionHandlingInConsole(
                () -> overtimeRepository.findAllByYearAndStatus(year, status));

    }

    @Override
    public List<OvertimeEntity> findOvertimeByMonthAndYear(int year, int month) {
        return executeWithExceptionHandlingInConsole(() -> {
            List<OvertimeEntity> allByYearAndMonth = overtimeRepository.findAllByYearAndMonth(year, month);
            isEmptyOvertimesList(allByYearAndMonth);
            return allByYearAndMonth;
        });
    }

    @Override
    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        return executeWithExceptionHandlingInConsole(() -> findOvertimeByMonthAndYear(year, month).stream()
                .mapToInt(OvertimeEntity::getDuration)
                .sum());
    }

    @Override
    public int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        return executeWithExceptionHandlingInConsole(() -> findOvertimeByMonthAndYear(year, month).stream()
                .filter(o -> o.getStatus().equals(status))
                .mapToInt(OvertimeEntity::getDuration)
                .sum());
    }

    @Override
    public List<OvertimeEntity> sortAllOvertimesById() {
        return executeWithExceptionHandlingInConsole(() -> {
            List<OvertimeEntity> allOvertimes = findAllOvertimes();
            Stream<OvertimeEntity> sorted = getSorted(allOvertimes);
            isEmptyOvertimesList(allOvertimes);
            return sorted.toList();
        });
    }

    Stream<OvertimeEntity> getSorted(List<OvertimeEntity> allOvertimes) {
        return allOvertimes.stream()
                .sorted(Comparator.comparingLong(OvertimeEntity::getId));
    }

    void isEmptyOvertimesList(List<OvertimeEntity> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            log.warn("Nie znaleziono danych");
            throw new NotFoundException(ErrorMessages.DATA_NOT_FOUND.getMessage());
        }
    }

    private <T> T executeWithExceptionHandlingInConsole(Supplier<T> action) {
        try {
            return action.get();
        } catch (DataAccessException e) {
            log.error("Data access error: {}", e.getMessage());
            throw new DataAccessResourceFailureException(ErrorMessages.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
