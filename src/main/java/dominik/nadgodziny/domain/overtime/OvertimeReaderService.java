package dominik.nadgodziny.domain.overtime;

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
    public List<Overtime> findAllOvertimes() {
        try {
            return overtimeRepository.findAll();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Overtime> findAllOvertimesByStatus(int year, String status) {
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
    public List<Overtime> findOvertimeByMonthAndYear(int year, int month) {
        try {
            List<Overtime> overtimeList = findAllOvertimes().stream()
                    .filter(o -> o.getOvertimeDate().getYear() == year)
                    .filter(o -> o.getOvertimeDate().getMonthValue() == month)
                    .toList();
            isEmptyOrNot(overtimeList);
            return overtimeList;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public int sumOfAllOvertimeHoursByMonth(int year, int month) {
        try {
            List<Overtime> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .mapToInt(Overtime::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        try {
            List<Overtime> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .filter(o -> o.getStatus().equals(status))
                    .mapToInt(Overtime::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Overtime> sortAllOvertimesById() {

        //todo przystosowac implementacje do kosoli i rest'a
        try {
            ConsoleWriter.printText("\n\n\n\nLista wszystkich nadgodzin:");
            List<Overtime> allOvertimes = findAllOvertimes();
            Stream<Overtime> sorted = getSorted(allOvertimes);
            isEmptyOrNot(allOvertimes);
            return sorted.toList();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return Collections.emptyList();
        }

    }

    private static Stream<Overtime> getSorted(List<Overtime> allOvertimes) {
        return allOvertimes.stream()
                .sorted(Comparator.comparingLong(Overtime::getId));
    }

     void isEmptyOrNot(List<Overtime> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            ConsoleWriter.printText("Nie znaleziono danych ");
        }
    }
}
