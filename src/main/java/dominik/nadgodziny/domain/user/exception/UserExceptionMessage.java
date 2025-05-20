package dominik.nadgodziny.domain.user.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserExceptionMessage {
    USER_ALREADY_EXISTS("User Already Exist");


    final String message;
}
