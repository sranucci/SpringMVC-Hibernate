package ar.edu.itba.paw.persistence.user;

import ar.edu.itba.paw.models.user.ResetPasswordToken;
import ar.edu.itba.paw.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ResetPasswordTokenDaoImpl implements ar.edu.itba.persistenceInterface.ResetPasswordTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void removeTokenByUserID(long userId) {
        String query = "DELETE FROM ResetPasswordToken t WHERE t.user.id = :userId";
        Query deleteQuery = em.createQuery(query);
        deleteQuery.setParameter("userId", userId);
        deleteQuery.executeUpdate();
    }

    @Override
    public ResetPasswordToken createToken(long userId, String token, LocalDateTime expirationDate) {
        final ResetPasswordToken resetPasswordToken =
                new ResetPasswordToken(token, em.find(User.class, userId), expirationDate);
        em.persist(resetPasswordToken);
        return resetPasswordToken;
    }

    @Override
    public Optional<ResetPasswordToken> getToken(String token) {
        TypedQuery<ResetPasswordToken> query = em.createQuery("from ResetPasswordToken  WHERE token = :token", ResetPasswordToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void removeToken(String token) {
        String query = "DELETE FROM ResetPasswordToken t WHERE t.token = :token";
        Query deleteQuery = em.createQuery(query);
        deleteQuery.setParameter("token", token);
        deleteQuery.executeUpdate();
    }


}
