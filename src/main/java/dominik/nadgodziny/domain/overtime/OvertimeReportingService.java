package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;
@RequiredArgsConstructor
class OvertimeReportingService implements OvertimeReporter {

    private final OvertimeRepository overtimeRepository;

      public void addNewOvertime(OvertimeEntity overtime) {
        Optional<OvertimeEntity> optionalOvertime = Optional.ofNullable(overtime);
        try {
            if (optionalOvertime.isPresent()) {
                 overtimeRepository.save(overtime);
            }
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
     }

    @Override
    public OvertimeEntity createOvertimeObject(OvertimeCreateDto overtimeCreateDto) {
        OvertimeEntity overtime = new OvertimeEntity(overtimeCreateDto.overtimeDate(),
                overtimeCreateDto.status(),
                overtimeCreateDto.duration());
        addNewOvertime(overtime);
        return overtime;
    }

    @Override
    public void deleteOvertimeById(long id){
         if (overtimeRepository.existsById(id)){
             overtimeRepository.deleteById(id);
             printText("Usnunieto nadgodzini o id: " + id);
         }else {
             printText("Nie znaleziono nadgodzin o podanym id: " + id);
         }
    }
}
