
package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserVerificationToken;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.paw.servicesInterface.TokenService;
import ar.edu.itba.paw.servicesInterface.exceptions.UserAlreadyExistsException;
import ar.edu.itba.persistenceInterface.UserDao;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first name";
    private static final String LAST_NAME = "last name";
    private static final boolean IS_ADMIN = false;
    private static final long ID = 1;
    private static final Long USER_ID = 1L;

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private  MailService ms;

    @Mock
    private TokenService tokenService;


    @InjectMocks
    private UserServiceImpl us;

    @BeforeClass
    public static void setDefaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }


    @Test
    public void testCreateUser(){

        User userMock = new User(USER_ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, false, Locale.ENGLISH);

        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.empty());

        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        when(userDao.create(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, false, Locale.ENGLISH))
                .thenReturn(userMock);

        when(tokenService.generateUserVerificationToken(USER_ID)).thenReturn(new UserVerificationToken(ID, "token", userMock));
       doNothing().when(ms).sendVerificationToken(EMAIL, "token", FIRST_NAME, Locale.ENGLISH);

        User newUser = us.createUser(EMAIL, PASSWORD, FIRST_NAME,LAST_NAME);
        Assert.assertNotNull(newUser);
        Assert.assertEquals(USER_ID, newUser.getId());
        Assert.assertEquals(EMAIL, newUser.getEmail());
        Assert.assertEquals(PASSWORD, newUser.getPassword());
        Assert.assertEquals(FIRST_NAME, newUser.getName());
        Assert.assertEquals(LAST_NAME, newUser.getLastname());

    }


    @Test
    public void testCreateUserAlreadyExists(){
        when(userDao.findByEmail(EMAIL)).thenThrow(UserAlreadyExistsException.class);
        Assert.assertThrows(UserAlreadyExistsException.class, () -> us.createUser(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME));
    }

    @Test
    public void testFindById(){

        when(userDao.findById(eq(USER_ID)))
                .thenReturn(Optional.of(new User(USER_ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, false, Locale.ENGLISH)));

        Optional<User> newUser = us.findById(ID);

        Assert.assertNotNull(newUser);
        Assert.assertTrue(newUser.isPresent());
        Assert.assertEquals(USER_ID, newUser.get().getId());
        Assert.assertEquals(EMAIL, newUser.get().getEmail());
        Assert.assertEquals(PASSWORD, newUser.get().getPassword());
        Assert.assertEquals(FIRST_NAME, newUser.get().getName());
        Assert.assertEquals(LAST_NAME, newUser.get().getLastname());
    }
}



