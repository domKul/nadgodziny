package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeCreateDto;

interface OvertimeReporter {

    OvertimeEntity createOvertimeObject(OvertimeCreateDto overtimeCreateDto);
    void addNewOvertime(OvertimeEntity overtime);
    void deleteOvertimeById(long id);
}
