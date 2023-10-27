package ar.edu.itba.paw.persistence.user;

import ar.edu.itba.paw.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements ar.edu.itba.persistenceInterface.UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void updateNameById(long userId, String name, String lastname) {
        Optional<User> maybeUser = findById(userId);
        if(maybeUser.isPresent()){
            final User user = maybeUser.get();
            user.setName(name);
            user.setLastname(lastname);
            em.persist(user);
        }
    }

    @Override
    public void updatePasswordById(long userId, String password) {
        Optional<User> maybeUser = findById(userId);
        if(maybeUser.isPresent()){
            final User user = maybeUser.get();
            user.setPassword(password);
            em.persist(user);
        }
    }

    @Override
    public User create(String email, String password, String name, String lastname, boolean isAdmin, boolean isVerified, Locale locale) {
        final User user = new User(email, password,name, lastname, isAdmin, isVerified,locale);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> verifyUser(long id) {
        Optional<User> maybeUser = findById(id);
        if(maybeUser.isPresent()){
            final User user = maybeUser.get();
            user.setVerified(true);
            em.persist(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public void updatePasswordByEmail(User user, String newPassword) {
        user.setPassword(newPassword);
        em.persist(user);
    }

    @Override
    public int deleteNonVerifiedUsers() {
        String query = "DELETE FROM User u WHERE u.isVerified = false";
        return em.createQuery(query).executeUpdate();
    }

    @Override
    public List<User> getAllFollowers(long userId) {
        String sql = "SELECT user_id " +
                "FROM tbl_follows " +
                "WHERE user_followed_id = :userId " +
                "order by user_id desc";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        final List<Long> idList = (List<Long>) nativeQuery.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        if (idList.isEmpty()) {
            return Collections.emptyList();
        }
        final TypedQuery<User> query = em.createQuery("from User WHERE id IN :ids order by user_id desc", User.class);
        query.setParameter("ids", idList);
        return query.getResultList();
    }

    @Override
    public void setFollowing(long id, long idFollowed) {

        String query = "INSERT INTO tbl_follows (user_id, user_followed_id) VALUES (:userId, :userToFollowId)";
        em.createNativeQuery(query)
                .setParameter("userId", id)
                .setParameter("userToFollowId", idFollowed)
                .executeUpdate();
    }

    @Override
    public void removeFollowing(long id, long idFollowed) {
        String query = "DELETE FROM tbl_follows WHERE user_id = :userId AND user_followed_id = :userToUnfollowId";
        em.createNativeQuery(query)
                .setParameter("userId", id)
                .setParameter("userToUnfollowId", idFollowed)
                .executeUpdate();
    }

    @Override
    public List<User> getUserSuggestionList() {
        String sql = "SELECT user_id " +
                "FROM tbl_user " +
                "LEFT JOIN ( " +
                "SELECT user_followed_id, COUNT(*) AS follower_count " +
                "FROM tbl_follows " +
                "GROUP BY user_followed_id " +
                "ORDER BY follower_count DESC " +
                ") AS top_followed_users " +
                "ON tbl_user.user_id = top_followed_users.user_followed_id " +
                "WHERE tbl_user.user_id IN ( " +
                "SELECT user_id " +
                "FROM tbl_recipe " +
                ")";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setMaxResults(10);

        @SuppressWarnings("unchecked")
        final List<Long> idList = (List<Long>) nativeQuery.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        if (idList.isEmpty()) {
            return Collections.emptyList();
        }
        final TypedQuery<User> query = em.createQuery("from User WHERE id IN :ids order by followersCount DESC ", User.class);
        query.setParameter("ids", idList);

        return query.getResultList();
    }

    @Override
    public List<User> getFollowers(long profileId, Optional<Integer> followersToBring) {
        String sql = "SELECT user_id " +
                "FROM tbl_follows " +
                "WHERE user_followed_id = :userId " +
                "order by user_id desc";

        return getUsersList(profileId, followersToBring, sql);

    }


    @Override
    public List<User> getFollowing(long profileId, Optional<Integer> followingToBring) {
        String sql = "SELECT user_followed_id " +
                "FROM tbl_follows " +
                "WHERE user_id = :userId " +
                "order by user_followed_id desc";

        return getUsersList(profileId, followingToBring, sql);

    }

    private List<User> getUsersList(long profileId, Optional<Integer> followersToBring, String sql) {
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("userId", profileId);
        nativeQuery.setMaxResults(followersToBring.orElse(4));


        @SuppressWarnings("unchecked") final List<Long> idList = (List<Long>) nativeQuery.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (idList.isEmpty()) {
            return Collections.emptyList();
        }

        final TypedQuery<User> query = em.createQuery("from User WHERE id IN :ids order by id DESC ", User.class);
        query.setParameter("ids", idList);
        return query.getResultList();
    }

    @Override
    public boolean getIsUserFollowedBy(Long userId, Long userIdToCheck) {
        String sql = "SELECT COUNT(*) > 0 " +
                "FROM  tbl_follows " +
                "WHERE user_id = :userIdToCheck AND user_followed_id = :userId ";

        Query query = em.createNativeQuery(sql);
        query.setParameter("userIdToCheck", userIdToCheck);
        query.setParameter("userId", userId);
        return (boolean) query.getSingleResult();
    }
}
