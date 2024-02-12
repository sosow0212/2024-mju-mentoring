package com.mju.mentoring.member.ui.auth.support;

import com.mju.mentoring.member.exception.exceptions.InvalidTokenException;

public class TokenExtractor {

    private static final String HEADER_DELIMITER = " ";
    private static final String PREFIX = "Bearer";
    private static final int VALID_AUTHORIZATION_LENGTH = 2;
    private static final int TOKEN_INDEX = 1;
    private static final int PREFIX_INDEX = 0;

    public static String extractToken(final String authorization) throws RuntimeException {
        if (authorization == null) {
            throw new InvalidTokenException();
        }
        String[] splitAuthorization = authorization.split(HEADER_DELIMITER);

        validateAuthorization(splitAuthorization);
        String token = splitAuthorization[TOKEN_INDEX];
        return token;
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
