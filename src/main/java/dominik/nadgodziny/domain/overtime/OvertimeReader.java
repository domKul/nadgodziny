package dominik.nadgodziny.domain.overtime;

import java.util.List;

interface OvertimeReader {

    List<OvertimeEntity> findAllOvertimes();
    List<OvertimeEntity> findAllOvertimesByStatus(int year, String status);

    List<OvertimeEntity> findOvertimeByMonthAndYear(int year, int month);

    int sumOfAllOvertimeHoursByMonth(int year, int month);

    int sumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status);

    List<OvertimeEntity> sortAllOvertimesById();
}
