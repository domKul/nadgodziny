package dominik.nadgodziny.domain.exception;

public enum ErrorMessages {

    INTERNAL_SERVER_ERROR("Wystąpił błąd podczas zapisu nadgodzin do bazy danych."),
    WRONG_MONTH("Błąd: Nieprawidłowy miesiąc.");


    ErrorMessages(String message) {
    }
}
