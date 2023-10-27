package ar.edu.itba.paw.servicesInterface;

import java.util.Locale;

public interface MailService {
    void sendNewCommentEmail(String destination, String username, String commenter, String comment, Long recipeId, Locale locale);

    void sendRecipeDeletionEmail(String destination, String recipeName, String body, Locale locale);

    void sendNLikesEmail(String destination, String recipeNam, String username, long likes, Long recipeId, Locale locale);

    void sendResetPassword(String destination, String token, Locale locale);

    void sendCommentDeletionEmail(String destination, String username, String commentData, String deletionMotive, Locale locale);

    void sendVerificationToken(String destination, String token, String username, Locale locale);

    void sendSuccessfulPasswordChange(String email, String username, Locale locale);

    void sendNewRecipeToFollowers(String destination, String userOwner, String username, long recipeId, String title, Locale locale);

}
