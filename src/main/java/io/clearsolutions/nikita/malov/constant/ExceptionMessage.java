package io.clearsolutions.nikita.malov.constant;

public final class ExceptionMessage {
    public static final String USER_NOT_FOUND_BY_ID = "The user with id: %s is not present in database";
    public static final String USER_PERSISTENCE_EXCEPTION = "The user must have unique email and phone number";
    public static final String USER_PATCH_EXCEPTION = "The exception occurred while patch operation";
}
