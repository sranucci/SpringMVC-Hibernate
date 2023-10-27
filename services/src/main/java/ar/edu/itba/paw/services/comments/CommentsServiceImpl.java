package ar.edu.itba.paw.services.comments;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.paw.servicesInterface.comments.CommentsService;
import ar.edu.itba.paw.servicesInterface.exceptions.CommentNotFoundException;
import ar.edu.itba.persistenceInterface.CommentsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsDao commentsDao;
    private final MailService mailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);

    @Autowired
    public CommentsServiceImpl(final CommentsDao cd,final MailService mailService) {
        this.commentsDao = cd;
        this.mailService = mailService;
    }

    @Transactional
    @Override
    public Comment createComment(long recipeId, long userId, String commentContent) {
        Comment comment = commentsDao.createComment(recipeId, userId, commentContent);



        User commenter = comment.getUser();
        User recipeOwner = comment.getRecipe().getUser();

        if ( commenter.getId().equals(recipeOwner.getId()) )
            return comment;

        mailService.sendNewCommentEmail(recipeOwner.getEmail(), recipeOwner.getName(), commenter.getName(), commentContent, recipeId, recipeOwner.getLocale());

        return comment;
    }


    @Transactional
    @Override
    public void removeCommentNotifyingUser(long commentId, String deletionMotive) throws CommentNotFoundException {
        Comment commentData = commentsDao.getComment(commentId).orElseThrow(CommentNotFoundException::new);
        String commentContent = commentData.getCommentContent();
        Recipe recipe = commentData.getRecipe();
        User commentOwner = commentData.getUser();
        commentsDao.deleteComment(commentData);
        mailService.sendCommentDeletionEmail(commentOwner.getEmail(), recipe.getTitle(), commentContent, deletionMotive, commentOwner.getLocale());
    }

}
