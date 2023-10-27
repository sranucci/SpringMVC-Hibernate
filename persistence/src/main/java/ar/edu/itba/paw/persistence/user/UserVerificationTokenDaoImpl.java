package ar.edu.itba.paw.persistence.user;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserVerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserVerificationTokenDaoImpl implements ar.edu.itba.persistenceInterface.UserVerificationTokenDao {

    @PersistenceContext
    private EntityManager em; //manejado x entityManager de webconfig

    @Override
    public UserVerificationToken createToken(long userId, String token) {
        final UserVerificationToken userVerificationToken =
                new UserVerificationToken(token, em.find(User.class, userId));
        em.persist(userVerificationToken);
        return userVerificationToken;
    }

    @Override
    public Optional<UserVerificationToken> getToken(String token) {

        TypedQuery<UserVerificationToken> query = em.createQuery("from UserVerificationToken  WHERE token = :token", UserVerificationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void removeToken(String token) {
        String query = "DELETE FROM UserVerificationToken t WHERE t.token = :token";
        Query deleteQuery = em.createQuery(query);
        deleteQuery.setParameter("token", token);
        deleteQuery.executeUpdate();
    }

    @Override
    public long deleteTokens() {
        String query = "DELETE FROM UserVerificationToken";
        Query deleteQuery = em.createQuery(query);
        return deleteQuery.executeUpdate();
    }
}
