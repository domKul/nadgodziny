package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

class SwitchProcessorService implements OvertimeFunctionDescription {

     String statusSelectionLoop(Scanner scanner) {
        String status;
        do {
            statusSelectionMenu();
            int statusSelection = scanner.nextInt();
            status = SwitchProcessorService.selectStatus(statusSelection);
        }while (status.isEmpty());
        return status;
    }

    static String selectStatus(int statusNumber)throws InputMismatchException {
        if(statusNumber > 2 || statusNumber < 1){
            printText(ErrorMessages.WRONG_STATUS_NUMBER.getMessage());
        }
        String status = "";
        switch (statusNumber){
            case 1 -> status = "Nadgodziny";
            case 2 -> status = "Zlecenie";
        }
        return status;
    }
}
