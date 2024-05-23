package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;
@RequiredArgsConstructor
class OvertimeReportingService extends SwitchProcessorService {

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
    
    boolean removeOvertimeFromDB(Scanner scanner){
         printText("Podaj Id nadgodzin");
        try{
            long id = scanner.nextLong();
            deleteOvertimeById(id);
        }catch (InputMismatchException e){
            printText("Id musi byc cyfra");
            return false;
        }
        return true;
    }

    void deleteOvertimeById(long id){
         if (overtimeRepository.existsById(id)){
             overtimeRepository.deleteById(id);
             printText("Usnunieto nadgodzini o id: " + id);
         }else {
             printText("Nie znaleziono nadgodzin o podanym id: " + id);
         }
    }

    OvertimeResponseDto createOvertimeObject(Scanner scanner) {
        Overtime overtime = null;
        try {
            printText("Data nadgodzin w formacie RRRR-MM-DD:");
            String dateString = scanner.nextLine();
            LocalDate date = localDateFromString(dateString);
            printText("Rodzaj nadgodzin");
            String status;
            status = statusSelectionLoop(scanner);
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

    LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }
}
