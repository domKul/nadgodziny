package dominik.nadgodziny.domain;


import dominik.nadgodziny.domain.exception.ErrorMessages;
import dominik.nadgodziny.domain.exception.WrongArgumentInputException;

import java.util.List;
import java.util.Scanner;

class OvertimeReaderService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    OvertimeReaderService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }

    @Override
    public List<Overtime> findAllOvertimes() {
        return overtimeRepository.findAll();
    }

    @Override
    public List<Overtime> findOvertimeByMonth(int scanner) {
        return overtimeRepository.findAll().stream()
                .filter(o -> o.getOvertimeDate().getMonthValue() == scanner)
                .toList();
    }

    @Override
    public int getSumOfAllOvertimeHoursByMonth(int month) {
        return overtimeRepository.findAll().stream()
                .filter(o -> o.getOvertimeDate().getMonthValue() == month)
                .map(Overtime::getDuration)
                .reduce(0,Integer::sum);
    }

    public List<Overtime> getAllOvertimes() {
        ConsoleWriter.printText("\n\n\n\nLista wszystkich nadgodzin:");
        List<Overtime> allOvertimes = findAllOvertimes();
        isEmptyOrNot(allOvertimes);
        allOvertimes.forEach(System.out::println);
        return allOvertimes;
    }

    public List<Overtime> getOvertimeByMonth(Scanner scanner) {
        ConsoleWriter.printText("Podaj miesiac (1-12): ");
        int month = scanner.nextInt();
        List<Overtime> overtimeByMonth = findOvertimeByMonth(month);
        scanner.nextLine();
        ConsoleWriter.printText("\n\n\n");
        isEmptyOrNot(overtimeByMonth);
        overtimeByMonth.forEach(System.out::println);
        return overtimeByMonth;
    }

    public int getSumOfAllOvertimeHoursByMonth(Scanner scanner) {
        ConsoleWriter.printText("Podaj miesiac");
        int month = scanner.nextInt();
        scanner.nextLine();
        int summing = getSumOfAllOvertimeHoursByMonth(month);
        if (summing == 0) {
            ConsoleWriter.printText("Nie znaleziono danych z miesiąca " + month);
        }
        ConsoleWriter.printText("Laczna suma godzin to " + summing);
        return summing;
    }

    public void getSumByGivenStatusOfGivenMonth(Scanner scanner) {
        try {
            ConsoleWriter.printText("podaj miesiac nadgodzin: ");
            int miesiac = scanner.nextInt();
            scanner.nextLine();
            ConsoleWriter.printText("podaj rodzja nadgodzin");
            String rodzaj = scanner.nextLine();
            if (miesiac < 1 || miesiac > 12) {
                throw new WrongArgumentInputException(ErrorMessages.WRONG_MONTH.name());
            }
            int i = overtimeRepository.countByDurationByStatus(miesiac, rodzaj);
            ConsoleWriter.printText("liczba godzin to " + i);
        } catch (WrongArgumentInputException e) {
            ConsoleWriter.printText(e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    void isEmptyOrNot(List<Overtime> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            ConsoleWriter.printText("Nie znaleziono danych ");
        }
    }
}
