package ar.edu.itba.paw.services;

import ar.edu.itba.paw.servicesInterface.UserImageService;
import ar.edu.itba.persistenceInterface.UserImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserImageServiceImpl implements UserImageService {
    private final UserImageDao userImageDao;

    @Autowired
    public UserImageServiceImpl(UserImageDao userImageDao) {
        this.userImageDao = userImageDao;
    }


    @Transactional
    @Override
    public void uploadUserPhoto(long userId, byte[] photoData) {
        userImageDao.uploadUserPhoto(userId, photoData);
    }

    @Transactional
    @Override
    public void deleteUserPhoto(long userId) {
        userImageDao.deleteUserPhoto(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<byte[]> findUserPhotoByUserId(long id) {
        return userImageDao.findUserPhotoByUserId(id);
    }
}
