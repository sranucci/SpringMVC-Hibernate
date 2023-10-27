package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.user.ResetPasswordToken;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserVerificationToken;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.paw.servicesInterface.TokenService;
import ar.edu.itba.paw.servicesInterface.UserImageService;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.servicesInterface.exceptions.InvalidTokenException;
import ar.edu.itba.paw.servicesInterface.exceptions.UserAlreadyExistsException;
import ar.edu.itba.persistenceInterface.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final ResetPasswordTokenDao resetPasswordTokenDao;
    private final UserImageService userImageService;


    private final TokenService tokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final MailService ms,
                           final UserImageService userImageService,
                           final ResetPasswordTokenDao resetPasswordTokenDao,
                           final TokenService tokenService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.mailService = ms;
        this.userImageService = userImageService;
        this.resetPasswordTokenDao = resetPasswordTokenDao;
        this.tokenService = tokenService;
    }

    @Transactional
    @Override
    public User createUser(String email, String password, String name, String lastname) throws UserAlreadyExistsException {
        if (userDao.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User user = userDao.create(email, passwordEncoder.encode(password), name, lastname, false, false, LocaleContextHolder.getLocale());
        final UserVerificationToken token = tokenService.generateUserVerificationToken(user.getId());
        mailService.sendVerificationToken(user.getEmail(), token.getToken(), user.getName(), user.getLocale());
        return user;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }


    //para usuarios viejos que no tienen la clave hasheada
    @Transactional
    @Override
    public void updatePasswordById(long userId, String password) {
        userDao.updatePasswordById(userId, passwordEncoder.encode(password));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(final String email) {
        return userDao.findByEmail(email);
    }

    @Transactional
    @Override
    public void updateProfile(long userId, String name, String lastName, byte[] userPhoto, boolean deleteProfilePhoto) {
        if (userPhoto.length != 0) {
            userImageService.uploadUserPhoto(userId, userPhoto);
        }
        if (deleteProfilePhoto) {
            userImageService.deleteUserPhoto(userId);
        }
        userDao.updateNameById(userId, name, lastName);
    }

    @Transactional
    @Override
    public User resetPassword(String token, String newPassword) throws InvalidTokenException {

        final Optional<ResetPasswordToken> resetPasswordTokenOptional = resetPasswordTokenDao.getToken(token);

        if (!resetPasswordTokenOptional.isPresent()) {
            throw new InvalidTokenException();
        }


        final ResetPasswordToken resetPasswordToken = resetPasswordTokenOptional.get();


        if (!resetPasswordToken.isValid()) {
            throw new InvalidTokenException();
        }

        User user = resetPasswordToken.getUser();


        userDao.updatePasswordByEmail(user, passwordEncoder.encode(newPassword));
        deleteGeneratedToken(token);
        mailService.sendSuccessfulPasswordChange(user.getEmail(),user.getName(),user.getLocale());
        return user;
    }

    @Transactional
    @Override
    public void generateResetPasswordToken(User user) {
        resetPasswordTokenDao.removeTokenByUserID(user.getId());
        final ResetPasswordToken resetPasswordToken = tokenService.generateResetPasswordToken(user.getId());
        mailService.sendResetPassword(user.getEmail(), resetPasswordToken.getToken(), user.getLocale());
    }

    @Transactional
    @Override
    public boolean validateToken(String token) {
        final Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenDao.getToken(token);

        return resetPasswordToken.isPresent() && resetPasswordToken.get().isValid();
    }

    @Transactional
    @Override
    public void deleteGeneratedToken(String token) {
        resetPasswordTokenDao.removeToken(token);
    }

    @Transactional
    @Override
    public Optional<User> verifyAccount(String token) {
        Optional<UserVerificationToken> userVerificationToken = tokenService.getGeneratedToken(token);

        if (!userVerificationToken.isPresent()) {
            return Optional.empty();
        }


        return userDao.verifyUser(userVerificationToken.get().getUser().getId());

    }

    @Transactional
    @Override
    public boolean resendVerificationToken(String email) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            final UserVerificationToken token = tokenService.generateUserVerificationToken(user.get().getId());
            mailService.sendVerificationToken(user.get().getEmail(), token.getToken(), user.get().getName(), user.get().getLocale());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<User> getFollowers(long userId) {
        return userDao.getAllFollowers(userId);
    }


    @Scheduled(cron = "0 0 */5 * * *")//every 5 hours
    @Transactional
    public void deleteNonVerifiedUsers() {
        long usersDeleted = userDao.deleteNonVerifiedUsers();
        LOGGER.info("Scheduled task deleted {} users that were not verified", usersDeleted);
    }

    @Transactional
    @Override
    public List<User> getUserSuggestionList() {
        return userDao.getUserSuggestionList();
    }

    @Transactional
    @Override
    public List<User> getFollowers(Long currentId, long profileId, Optional<Integer> followersToBring) {
        List<User> followers = userDao.getFollowers(profileId, followersToBring);
        followers.forEach(user -> user.setFollowedByCurrentUser(userDao.getIsUserFollowedBy(user.getId(), currentId)));
        return followers;
    }

    @Transactional
    @Override
    public List<User> getFollowing(Long currentId, long profileId, Optional<Integer> followingToBring) {
        List<User> followers = userDao.getFollowing(profileId, followingToBring);
        followers.forEach(user -> user.setFollowedByCurrentUser(userDao.getIsUserFollowedBy(user.getId(), currentId)));
        return followers;
    }

    @Transactional
    @Override
    public void setFollowing(long id, long idFollowed) {
        userDao.setFollowing(id, idFollowed);
    }

    @Transactional
    @Override
    public void removeFollowing(long id, long idFollowed) {
        userDao.removeFollowing(id, idFollowed);
    }

    @Transactional
    @Override
    public boolean getFollows(long currentId, long profileId) {
        return userDao.getIsUserFollowedBy(profileId, currentId);
    }

}
