
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.user.UserImage;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.UserImageDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateUserImageDaoImplTest.sql"})
public class UserImageDaoImplTest {

    @Autowired
    private UserImageDao userImageDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testUploadUserPhoto() {
        userImageDao.uploadUserPhoto(USER_ID_2,  IMG_BYTEA);
        assertNotNull(em.find(UserImage.class, USER_ID_2 ));

    }

    @Test
    public void testUploadUserPhotoWhenUserAlreadyHasAPhoto() {
        userImageDao.uploadUserPhoto(USER_ID,  IMG_BYTEA);
        assertNotNull(em.find(UserImage.class, USER_ID ));
    }



    @Test
    public void testFindUserPhotoByUserId(){
        Optional<byte[]> photoData = userImageDao.findUserPhotoByUserId(USER_ID);
        byte[] expectedPhotoData = new byte[]{0x00};

        assertTrue(photoData.isPresent());
        assertArrayEquals(expectedPhotoData, photoData.get());
    }


    @Test
    public void testFindUserPhotoByUserIdWhenUserIdDoesNotExist(){
        Optional<byte[]> photoData = userImageDao.findUserPhotoByUserId(0);
        assertFalse(photoData.isPresent());
    }


    @Test
    public void testDeleteUserPhoto(){
        userImageDao.deleteUserPhoto(USER_ID);
        assertNull(em.find(UserImage.class, USER_ID ));
    }

    @Test
    public void testDeleteUserPhotoWhenUserIdDoesNotExist(){
        userImageDao.deleteUserPhoto(0);
        assertNull(em.find(UserImage.class, 0L));

    }


}



