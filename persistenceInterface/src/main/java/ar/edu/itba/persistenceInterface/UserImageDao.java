package ar.edu.itba.persistenceInterface;

import java.util.Optional;

public interface UserImageDao {
    void uploadUserPhoto(long userId, byte[] photoData);

    Optional<byte[]> findUserPhotoByUserId(long userId);

    void deleteUserPhoto(long userId);
}
