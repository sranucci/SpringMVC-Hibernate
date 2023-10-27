package ar.edu.itba.paw.servicesInterface;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.exceptions.InvalidTokenException;
import ar.edu.itba.paw.servicesInterface.exceptions.UserAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(String email, String password, String name, String lastname) throws UserAlreadyExistsException;
    Optional<User> findById(long id);

    void updatePasswordById(long userId, String password);
    Optional<User> findByEmail(final String email);

    void updateProfile(long userId, String name, String lastName, byte[] userPhoto, boolean deleteProfilePhoto);

    User resetPassword(String token, String newPassword) throws InvalidTokenException;
    void generateResetPasswordToken(User user);
    boolean validateToken(String token);
    void deleteGeneratedToken(String token);

    Optional<User> verifyAccount(String token);

    boolean resendVerificationToken(String email);

    List<User> getFollowers(long userId);

    void setFollowing(long id, long idFollowed);

    void removeFollowing(long id, long idFollowed);

    boolean getFollows(long currentId, long profileId);

    //NO ELIMINAR PORQUE NO BUILDEA, ES METODO SCHEDULED
    void deleteNonVerifiedUsers();

    List<User> getUserSuggestionList();

    List<User> getFollowers(Long currentId, long profileId, Optional<Integer> followersToBring);

    List<User> getFollowing(Long currentId, long profileId, Optional<Integer> followingToBring);
}

