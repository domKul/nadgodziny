package dominik.nadgodziny.domain.overtime.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    INTERNAL_SERVER_ERROR("Wystąpił błąd podczas zapisu nadgodzin do bazy danych."),
    NOT_FOUND("Overtimes Not Found"),
    WRONG_STATUS_NUMBER("Mozesz wybrac miedzy 1 a 2"),
    WRONG_YEAR_OR_MONTH("Błąd: Nieprawidłowy rok lub miesiąc.");

    private final String message;
}
