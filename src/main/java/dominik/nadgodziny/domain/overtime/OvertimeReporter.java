package dominik.nadgodziny.domain.overtime;

import java.time.LocalDate;

interface OvertimeReporter {

    Overtime createOvertimeObject(LocalDate date, String status, int hours);
    void addNewOvertime(Overtime overtime);
    void deleteOvertimeById(long id);
}
