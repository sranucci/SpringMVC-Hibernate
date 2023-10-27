package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.user.UserVerificationToken;

import java.util.Optional;

public interface UserVerificationTokenDao {

    UserVerificationToken createToken(long userId, String token);

    Optional<UserVerificationToken> getToken(String token);

    void removeToken(String token);

    long deleteTokens();
}
