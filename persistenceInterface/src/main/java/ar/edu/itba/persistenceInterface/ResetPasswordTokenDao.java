package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.user.ResetPasswordToken;

import java.time.LocalDateTime;
import java.util.Optional;


public interface ResetPasswordTokenDao {

    void removeTokenByUserID(long userId);

    ResetPasswordToken createToken(long userId, String token, LocalDateTime expirationDate);

    Optional<ResetPasswordToken> getToken(String token);

    void removeToken(String token);


}
