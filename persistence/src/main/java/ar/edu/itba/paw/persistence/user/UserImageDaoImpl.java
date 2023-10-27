package ar.edu.itba.paw.persistence.user;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.persistenceInterface.UserImageDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class UserImageDaoImpl implements UserImageDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void uploadUserPhoto(long userId, byte[] photoData) {
        Optional<ar.edu.itba.paw.models.user.UserImage> maybeUserImage = Optional.ofNullable(em.find(ar.edu.itba.paw.models.user.UserImage.class, userId));
        if(maybeUserImage.isPresent()){
            final ar.edu.itba.paw.models.user.UserImage userImage = maybeUserImage.get();
            userImage.setPhotoData(photoData);
            return;
        }
        final ar.edu.itba.paw.models.user.UserImage userImage = new ar.edu.itba.paw.models.user.UserImage(userId, em.getReference(User.class, userId), photoData);
        em.persist(userImage);
    }

    @Override
    public Optional<byte[]> findUserPhotoByUserId(long userId) {
        Optional<ar.edu.itba.paw.models.user.UserImage> maybeUserImage = Optional.ofNullable(em.find(ar.edu.itba.paw.models.user.UserImage.class, userId));
        return maybeUserImage.map(ar.edu.itba.paw.models.user.UserImage::getPhotoData);
    }

    @Override
    public void deleteUserPhoto(long userId) {
        String query = "DELETE FROM UserImage ui WHERE ui.user.id = :userId";
        Query deleteQuery = em.createQuery(query);
        deleteQuery.setParameter("userId", userId);
        deleteQuery.executeUpdate();
    }
}
