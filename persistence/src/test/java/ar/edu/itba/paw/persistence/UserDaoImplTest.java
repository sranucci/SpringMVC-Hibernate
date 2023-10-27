package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateUserDaoImplTest.sql"})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreate() {
        User user = userDao.create(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, true, LOCALE);

        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(FIRST_NAME, user.getName());
        assertEquals(LAST_NAME, user.getLastname());
        assertEquals(IS_ADMIN, user.isAdmin());

    }



    @Test
    public void testFindByIdWhenIdIsFound() { // Inserting a user with ID 1 into the database

        Optional<User> user = userDao.findById(USER_ID);

        assertTrue(user.isPresent());
        assertEquals(USER_ID, user.get().getId());
        assertEquals(EMAIL, user.get().getEmail());
        assertEquals(PASSWORD, user.get().getPassword());
        assertEquals(FIRST_NAME, user.get().getName());
        assertEquals(LAST_NAME, user.get().getLastname());
        assertEquals(IS_ADMIN, user.get().isAdmin());
    }

    @Test
    public void testFindByIdWhenIdIsNotFound() {
        Optional<User> user = userDao.findById(NON_EXISTENT_USER_ID);

        assertFalse(user.isPresent());
    }



    @Test
    public void testUpdateNameById() {
        userDao.updateNameById(USER_ID, FIRST_NAME2, LAST_NAME2);

        Optional<User> user = Optional.ofNullable(em.find(User.class, USER_ID));
        assertTrue(user.isPresent());
        assertEquals(FIRST_NAME2, user.get().getName());
        assertEquals(LAST_NAME2, user.get().getLastname());

    }

    @Test
    public void testUpdatePasswordById() {
        userDao.updatePasswordById(USER_ID, PASSWORD2);
        Optional<User> user = Optional.ofNullable(em.find(User.class, USER_ID));
        assertTrue(user.isPresent());
        assertEquals(PASSWORD2, user.get().getPassword());

    }


    @Test
    public void testFindByEmail(){

        Optional<User> user = userDao.findByEmail(EMAIL);

        assertTrue(user.isPresent());
        assertEquals(USER_ID, user.get().getId());
        assertEquals(EMAIL, user.get().getEmail());
        assertEquals(PASSWORD, user.get().getPassword());
        assertEquals(FIRST_NAME, user.get().getName());
        assertEquals(LAST_NAME, user.get().getLastname());
        assertEquals(IS_ADMIN, user.get().isAdmin());
    }

    @Test
    public void testFindByEmailWhenUserDoesNotExist(){

        Optional<User> user = userDao.findByEmail("nonExistingEmail");
        assertFalse(user.isPresent());
    }

    @Test
    public void testGetAllFollowers(){
        List<User> followers = userDao.getAllFollowers(USER_ID);
        assertEquals(2, followers.size());
        assertEquals(USER_ID_3, followers.get(0).getId());
        assertEquals(USER_ID_2, followers.get(1).getId());
    }

    @Test
    public void testGetUserSuggestionList(){
        List<User> users = userDao.getUserSuggestionList();
        assertEquals(1, users.size());
    }


    @Test
    public void testGetFollowing(){
        List<User> following = userDao.getFollowing(USER_ID, Optional.of(4));
        assertEquals(1, following.size());
    }

    @Test
    public void testGetFollowingWhenIsEmpty(){
        List<User> following = userDao.getFollowing(USER_ID_4, Optional.of(4));
        assertEquals(0, following.size());
    }

   @Test
    public void testGetFollowers(){
        List<User> following = userDao.getFollowers(USER_ID, Optional.of(4));
        assertEquals(2, following.size());
    }

    @Test
    public void testGetFollowersWhenIsEmpty(){
        List<User> following = userDao.getFollowers(USER_ID_3, Optional.of(4));
        assertEquals(0, following.size());
    }

    @Test
    public void testGetIsFollowedBy(){
        assertTrue(userDao.getIsUserFollowedBy(USER_ID, USER_ID_2));
    }

    @Test
    public void testGetIsFollowedByWhenNot(){
        assertFalse(userDao.getIsUserFollowedBy(USER_ID_2, USER_ID_3));
    }

    @Test
    public void testDeleteNonVerifiedUsers(){
        assertEquals(1, userDao.deleteNonVerifiedUsers());
    }


    @Test
    public void testUpdatePasswordByEmail(){
        User user = em.find(User.class, USER_ID);
        userDao.updatePasswordByEmail(user, "newpass");
        assertEquals("newpass", user.getPassword());
    }

    @Test
    public void testVerifyUser(){
        userDao.verifyUser(USER_ID_4);
        assertTrue(em.find(User.class, USER_ID_4).isVerified());
    }



}





