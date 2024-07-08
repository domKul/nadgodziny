package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


@RequiredArgsConstructor
class OvertimeReaderService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    @Override
    public List<OvertimeEntity> findAllOvertimes() {
        try {
            List<OvertimeEntity> all = overtimeRepository.findAll();
            if (all.isEmpty()){
                throw new NotFoundException(ErrorMessages.NOT_FOUND.getMessage());
            }
            return all;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<OvertimeEntity> findAllOvertimesByStatus(int year, String status) {
        try {
            return findAllOvertimes().stream()
                    .filter(o -> o.getOvertimeDate().getYear() == year)
                    .filter(o -> o.getStatus().equals(status))
                    .toList();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<OvertimeEntity> findOvertimeByMonthAndYear(int year, int month) {
        try {
            List<OvertimeEntity> overtimeList = findAllOvertimes().stream()
                    .filter(o -> o.getOvertimeDate().getYear() == year)
                    .filter(o -> o.getOvertimeDate().getMonthValue() == month)
                    .toList();
            isEmptyConsoleInfo(overtimeList);
            return overtimeList;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        try {
            List<OvertimeEntity> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .mapToInt(OvertimeEntity::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        try {
            List<OvertimeEntity> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .filter(o -> o.getStatus().equals(status))
                    .mapToInt(OvertimeEntity::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<OvertimeEntity> sortAllOvertimesById() {
        try {
            List<OvertimeEntity> allOvertimes = findAllOvertimes();
            Stream<OvertimeEntity> sorted = getSorted(allOvertimes);
            isEmptyConsoleInfo(allOvertimes);
            return sorted.toList();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }
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
}
