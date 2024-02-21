package com.mju.mentoring.member.ui.auth.support;

import com.mju.mentoring.member.exception.exceptions.InvalidTokenException;
import java.util.Optional;

public class TokenExtractor {

    private static final String HEADER_DELIMITER = " ";
    private static final String PREFIX = "Bearer";
    private static final int VALID_AUTHORIZATION_LENGTH = 2;
    private static final int TOKEN_INDEX = 1;
    private static final int PREFIX_INDEX = 0;

    public static String extractToken(final Optional<String> authorization)
        throws RuntimeException {
        if (authorization.isEmpty()) {
            throw new InvalidTokenException();
        }

        String[] splitAuthorization = authorization.get().split(HEADER_DELIMITER);

        validateAuthorization(splitAuthorization);
        return splitAuthorization[TOKEN_INDEX];
    }

    private static void validateAuthorization(final String[] splitAuthorization)
        throws RuntimeException {
        if (splitAuthorization.length != VALID_AUTHORIZATION_LENGTH) {
            throw new InvalidTokenException();
        }

        if (!splitAuthorization[PREFIX_INDEX].equals(PREFIX)) {
            throw new InvalidTokenException();
        }
    }
}
