package dominik.nadgodziny.domain.overtime;

import java.util.List;

interface OvertimeReader {

    List<Overtime> findAllOvertimes();

    List<Overtime>findAllOvertimesByStatus(int year, String status);

     List<Overtime> findOvertimeByMonthAndYear(int year,int month);

    int getSumOfAllOvertimeHoursByMonth(int year,int month);

    int getSumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year,int month,String status);
}
