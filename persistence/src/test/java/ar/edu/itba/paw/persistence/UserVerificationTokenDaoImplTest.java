package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.user.UserVerificationToken;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.UserVerificationTokenDao;

import static org.junit.Assert.*;
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
import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateUserVerificationTokenDaoImplTest.sql"})
public class UserVerificationTokenDaoImplTest {


    @Autowired
    private UserVerificationTokenDao userVerificationTokenDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testCreateToken(){
        UserVerificationToken userVerificationToken = userVerificationTokenDao.createToken(2, "7c734a3f-7e90-42f8-88e1-c031cc132961");
        assertNotNull(userVerificationToken);
        assertEquals(2, userVerificationToken.getUser().getId().longValue());
        assertEquals("7c734a3f-7e90-42f8-88e1-c031cc132961", userVerificationToken.getToken());
    }



    @Test
    public void testGetTokenWhenPresent(){
        Optional<UserVerificationToken> userVerificationTokenOptional = userVerificationTokenDao.getToken("7c734a3f-7e90-42f8-88e1-c031cc132961");
        assertTrue(userVerificationTokenOptional.isPresent());
        assertEquals("7c734a3f-7e90-42f8-88e1-c031cc132961", userVerificationTokenOptional.get().getToken());
        assertEquals(1, userVerificationTokenOptional.get().getUser().getId().longValue());
    }



    @Test
    public void testGetTokenWhenNotPresent(){
        Optional<UserVerificationToken> userVerificationTokenOptional = userVerificationTokenDao.getToken("invalid");
        assertFalse(userVerificationTokenOptional.isPresent());
    }


    @Test
    public void testRemoveTokenWhenExists(){
        userVerificationTokenDao.removeToken("7c734a3f-7e90-42f8-88e1-c031cc132961");
        assertNull(em.find(UserVerificationToken.class, 1L));
    }

}

