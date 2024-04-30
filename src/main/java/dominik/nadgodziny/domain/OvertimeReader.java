package dominik.nadgodziny.domain;

import java.util.List;

interface OvertimeReader {

    List<Overtime> findAllOvertimes();

    List<Overtime> findOvertimeByMonth(int month);


    int getSumOfAllOvertimeHoursByMonth(int month);


    int getSumOfHoursByGivenStatusOfGivenMonth(int month,String status);

}
