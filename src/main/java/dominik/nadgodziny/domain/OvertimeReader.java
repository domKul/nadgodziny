package dominik.nadgodziny.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

interface OvertimeReader {

    List<Overtime> findAllOvertimes();

    List<Overtime> findOvertimeByMonth(int month);


    int getSumOfAllOvertimeHoursByMonth(int month);


    void getSumByGivenStatusOfGivenMonth(Scanner scanner);

}
