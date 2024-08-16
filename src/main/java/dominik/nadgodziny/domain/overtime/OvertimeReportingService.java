package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;
@RequiredArgsConstructor
class OvertimeReportingService implements OvertimeReporter {

    private final OvertimeRepository overtimeRepository;

      public void addNewOvertime(OvertimeEntity overtime) {
        try {
                 overtimeRepository.save(overtime);
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
     }

    @Override
    public OvertimeEntity createOvertimeObject(OvertimeCreateDto overtimeCreateDto) {
        if (overtimeCreateDto == null) {
            throw new NotFoundException(ErrorMessages.NOT_FOUND.getMessage());
        }
        OvertimeEntity overtimeEntity = OvertimeMapper.mapToEntity(overtimeCreateDto);
        addNewOvertime(overtimeEntity);
        return overtimeEntity;
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
