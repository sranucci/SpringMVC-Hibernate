package ar.edu.itba.paw.servicesInterface;

import ar.edu.itba.paw.models.user.ResetPasswordToken;
import ar.edu.itba.paw.models.user.UserVerificationToken;

import java.util.Optional;

public interface TokenService {
    ResetPasswordToken generateResetPasswordToken(long id);
    UserVerificationToken generateUserVerificationToken(long userId);


    //no eliminar porque no buildea. es metodo scheduled
    void deleteNonVerifiedTokens();

    Optional<UserVerificationToken> getGeneratedToken(String token);
}



