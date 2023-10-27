package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserDao {
    void updateNameById(long userId, String name, String lastname);

    void updatePasswordById(long userId, String password);

    User create(String email, String password, String name, String lastname, boolean isAdmin, boolean isVerified, Locale locale);

    Optional<User> verifyUser(final long id);

    Optional<User> findById(final long id);

    Optional<User> findByEmail(final String email);

    void updatePasswordByEmail(User user, String newPassword);

    int deleteNonVerifiedUsers();

    List<User> getAllFollowers(long userId);

    void setFollowing(long userId, long idFollowed);

    void removeFollowing(long userId, long idFollowed);

    List<User> getUserSuggestionList();

    List<User> getFollowers(long profileId, Optional<Integer> followersToBring);

    List<User> getFollowing(long profileId, Optional<Integer> followingToBring);

    boolean getIsUserFollowedBy(Long id, Long currentId);
}
