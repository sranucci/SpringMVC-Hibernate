package ar.edu.itba.paw.servicesInterface;

import java.util.Optional;

public interface UserImageService {

    void deleteUserPhoto(long userId);

    void uploadUserPhoto(long userId, byte[] photoData);

    Optional<byte[]> findUserPhotoByUserId(long id);


}
