package dominik.nadgodziny.domain.overtime;

import java.time.LocalDate;

interface OvertimeReporter {

    OvertimeEntity createOvertimeObject(LocalDate date, String status, int hours);
    void addNewOvertime(OvertimeEntity overtime);
    void deleteOvertimeById(long id);
}
