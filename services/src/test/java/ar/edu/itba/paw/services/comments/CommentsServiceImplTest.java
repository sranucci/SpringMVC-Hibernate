package ar.edu.itba.paw.services.comments;

import ar.edu.itba.paw.enums.UserInteraction;
import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.persistenceInterface.CommentsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;


@RunWith(MockitoJUnitRunner.class)

public class CommentsServiceImplTest {

    private static final long COMMENT_ID = 1;
    private static final long RECIPE_ID = 1;
    private static final long USER_ID = 1;
    private static final long USER_ID_2 = 2;
    private static final String PASSWORD = "password";
    private static final boolean LIKED = true;
    private static  final String USER_NAME = "name";
    private static final String USER_LASTNAME = "last name";
    private static final String COMMENT = "comment";
    private static final Instant CREATED_AT = Instant.now();
    private static final long DISLIKES = 1;
    private static final long LIKES = 1;
    private static final UserInteraction USER_INTERACTION = UserInteraction.LIKE;

    private static final String USER_EMAIL = "email";
    private static final String USER_EMAIL_2 = "email2";
    private static final String RECIPE_NAME = "recipe";
    private static final String DELETION_MOTIVE= "deletion";
    private static final String SUBJECT = "subject";

    @Mock
    private CommentsDao commentsDao;
    @Mock
    private MailService ms;


    @InjectMocks
    private CommentsServiceImpl cs;


    @Test
    public void testCreateCommentWhenCommenterIsRecipeOwner(){
        User user = new User(USER_ID, USER_EMAIL, PASSWORD, USER_NAME, USER_LASTNAME, true, true, Locale.ENGLISH );
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        Comment comment = new Comment("comment", user, recipe, Date.from(Instant.now()));
        when(commentsDao.createComment(RECIPE_ID,USER_ID,"comment")).thenReturn(comment);
        assertNotNull(cs.createComment(RECIPE_ID, USER_ID, "comment"));

    }

    @Test
    public void testCreateCommentWhenCommenterIsNotRecipeOwner(){
        User commenter = new User(USER_ID_2, USER_EMAIL, PASSWORD, USER_NAME, USER_LASTNAME, true, true, Locale.ENGLISH );
        User recipeOwner = new User(USER_ID, USER_EMAIL_2, PASSWORD, USER_NAME, USER_LASTNAME, true, true, Locale.ENGLISH );
        Recipe recipe = new Recipe();
        recipe.setUser(recipeOwner);
        Comment comment = new Comment("comment", commenter, recipe, Date.from(Instant.now()));
        when(commentsDao.createComment(RECIPE_ID,USER_ID_2,"comment")).thenReturn(comment);
        doNothing().when(ms).sendNewCommentEmail(USER_EMAIL_2, USER_NAME, USER_NAME, COMMENT, RECIPE_ID, Locale.ENGLISH );
        assertNotNull(cs.createComment(RECIPE_ID, USER_ID_2, "comment"));

    }








}




