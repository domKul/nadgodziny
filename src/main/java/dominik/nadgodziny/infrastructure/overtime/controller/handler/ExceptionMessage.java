package dominik.nadgodziny.infrastructure.overtime.controller.handler;

import org.springframework.http.HttpStatus;

public record ExceptionMessage(String message,
                               HttpStatus httpStatus) {
}
