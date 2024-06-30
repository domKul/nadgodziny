package dominik.nadgodziny.infrastructure.overtime.controller.handler;

import org.springframework.http.HttpStatus;

record ExceptionMessage(String message,
                               HttpStatus httpStatus) {
}
