package dominik.nadgodziny.domain.overtime.exception;

public enum ErrorMessages {

    INTERNAL_SERVER_ERROR("Wystąpił błąd podczas zapisu nadgodzin do bazy danych."),
    WRONG_MONTH("Błąd: Nieprawidłowy miesiąc.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ErrorMessages(String message) {
        this.message = message;
    }
}