package dominik.nadgodziny.domain.overtime;

import java.util.Scanner;

interface OvertimeReader {

    void getAllOvertimes();

    void getOvertimeByMonth(Scanner scanner);


    void getSumOfAllOvertimeHoursByMonth(Scanner scanner);


    void getSumByGivenStatusOfGivenMonth(Scanner scanner);

}
