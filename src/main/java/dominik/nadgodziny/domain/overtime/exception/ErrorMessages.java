package dominik.nadgodziny.domain.overtime.exception;

public enum ErrorMessages {

    INTERNAL_SERVER_ERROR("Wystąpił błąd podczas zapisu nadgodzin do bazy danych."),
    NOT_FOUND("Not Found"),
    WRONG_STATUS_NUMBER("Mozesz wybrac miedzy 1 a 2"),
    WRONG_YEAR_OR_MONTH("Błąd: Nieprawidłowy rok lub miesiąc.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ErrorMessages(String message) {
        this.message = message;
    }
}
