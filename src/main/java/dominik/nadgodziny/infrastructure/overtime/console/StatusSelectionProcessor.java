package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

class StatusSelectionProcessor implements OvertimeMenuFunctionDescription {

      protected String statusSelectionLoop(Scanner scanner) {
        String status;
        do {
            overtimeStatusSelectionMenu();
            int statusSelection = scanner.nextInt();
            status = StatusSelectionProcessor.selectStatus(statusSelection);
        }while (status.isEmpty());
        return status;
    }

    static String selectStatus(int statusNumber)throws InputMismatchException {
        if(statusNumber > 2 || statusNumber < 1){
            printText(ErrorMessages.WRONG_STATUS_NUMBER.getMessage());
        }
        String status = "";
        switch (statusNumber){
            case 1 -> status = "nadgodziny";
            case 2 -> status = "zlecenie";
        }
        return status;
    }
}
