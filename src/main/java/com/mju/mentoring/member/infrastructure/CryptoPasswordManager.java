package com.mju.mentoring.member.infrastructure;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.mju.mentoring.member.domain.PasswordManager;
import com.mju.mentoring.member.exception.EncodedException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class CryptoPasswordManager implements PasswordManager {

    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int HASH_LENGTH = 128;
    private static final int HASH_ITERATION = 85319;
    private static final String SALT_ALGORITHM = "SHA-512";

    @Override
    public String encode(final String plainPassword) {
        try {
            KeySpec spec = new PBEKeySpec(
                    plainPassword.toCharArray(),
                    genSalt(plainPassword),
                    HASH_ITERATION,
                    HASH_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);

            byte[] hash = factory.generateSecret(spec)
                    .getEncoded();

            return Base64.getEncoder()
                    .encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new EncodedException();
        }
    }

    private byte[] genSalt(final String plainPassword) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(SALT_ALGORITHM);
        byte[] keyBytes = plainPassword.getBytes(UTF_8);

        return digest.digest(keyBytes);
    }
}
