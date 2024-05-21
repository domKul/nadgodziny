package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;
@RequiredArgsConstructor
class OvertimeReportingService implements OvertimeFunctionDescription{

    private final OvertimeRepository overtimeRepository;

    Optional<Overtime> addNewOvertime(Overtime overtime) {
        Optional<Overtime> ifNotNull = Optional.ofNullable(overtime);
        try {
            if (ifNotNull.isPresent()) {
                 overtimeRepository.save(overtime);
            }
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
        return ifNotNull;
    }

    OvertimeResponseDto createOvertimeObject(Scanner scanner) {
        Overtime overtime = null;
        try {
            printText("Data nadgodzin w formacie RRRR-MM-DD:");
            String dateString = scanner.nextLine();
            LocalDate date = localDateFromString(dateString);
            printText("Rodzaj nadgodzin");
            String status;
            do {
                statusSelectionMenu();
                int statusSelection = scanner.nextInt();
                 status = selectStatus(statusSelection);
            }while (status.isEmpty());
            printText("Czas pracy (w godzinach)");
            int hours = scanner.nextInt();
            scanner.nextLine();
            overtime = new Overtime(date,status,hours);
            addNewOvertime(overtime);
        } catch (DateTimeParseException e) {
            printText(" Zły format daty.");
       }
        return overtime != null ? OvertimeMapper.mapToOvertimeResponseDto(overtime) : null;
    }

     String selectStatus(int statusNumber)throws InputMismatchException {
        if(statusNumber > 2 || statusNumber < 1){
            printText(ErrorMessages.WRONG_STATUS_NUMBER.getMessage());
        }
        String status = "";
            switch (statusNumber){
                case 1 ->
                    status = "nadgodziny";
                case 2 ->
                    status = "zlecenie";
            }
        return status;
    }

    LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }
}
