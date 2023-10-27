package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.user.ResetPasswordToken;
import ar.edu.itba.paw.models.user.UserVerificationToken;
import ar.edu.itba.paw.servicesInterface.TokenService;
import ar.edu.itba.persistenceInterface.ResetPasswordTokenDao;
import ar.edu.itba.persistenceInterface.UserVerificationTokenDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final ResetPasswordTokenDao resetPasswordTokenDao;

    private final UserVerificationTokenDao userVerificationTokenDao;



    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    public TokenServiceImpl(final ResetPasswordTokenDao resetPasswordTokenDao,
                            final UserVerificationTokenDao userVerificationTokenDao
                            ){
        this.resetPasswordTokenDao = resetPasswordTokenDao;
        this.userVerificationTokenDao = userVerificationTokenDao;
    }

    @Transactional
    @Override
    public ResetPasswordToken generateResetPasswordToken(long id) {
        String token = UUID.randomUUID().toString();
        return resetPasswordTokenDao.createToken(id, token, ResetPasswordToken.generateTokenExpirationDate());
    }

    @Transactional
    @Override
    public UserVerificationToken generateUserVerificationToken(long userId) {
        String token = UUID.randomUUID().toString();
        return userVerificationTokenDao.createToken(userId, token);
    }


    @Scheduled(cron = "0 0 */5 * * *")//every 5 hours
    @Transactional
    public void deleteNonVerifiedTokens(){
        long deletedTokens = userVerificationTokenDao.deleteTokens();
        LOGGER.info("Scheduled task deleted {} tokens that were not verified",deletedTokens);
    }

    @Override
    public Optional<UserVerificationToken> getGeneratedToken(String token) {
        return userVerificationTokenDao.getToken(token);
    }

}
