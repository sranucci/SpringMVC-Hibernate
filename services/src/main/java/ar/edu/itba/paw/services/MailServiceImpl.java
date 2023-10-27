package ar.edu.itba.paw.services;

import ar.edu.itba.paw.servicesInterface.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private MessageSource mailMessageSource;


    @Autowired
    public MailServiceImpl(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    private void sendEmail(String subject, String destination, String templateName, Context context) {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom("Taste Tales <tastetales.mailing@gmail.com>");
            message.setTo(destination);
            message.setSubject(subject);
            final String htmlContent = templateEngine.process(templateName, context);
            message.setText(htmlContent, true);

        } catch (MessagingException e) {
            LOGGER.error("Error proccesing  mail to {} stacktrace:\n{}",destination,e.getMessage());
            return;
        }

        try {
            this.mailSender.send(mimeMessage);
        } catch (MailException e){
            LOGGER.error("Error sending mail to {} stacktrace:\n{} ",destination,e.getMessage());
        }

    }


    @Async
    @Override
    public void sendNewCommentEmail(String destination, String username, String commenter, String comment, Long recipeId, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username", username);
        context.setVariable("commenter", commenter);
        context.setVariable("comment", comment);
        String url = "http://pawserver.it.itba.edu.ar/paw-2023a-05/" + "recipeDetail/" + recipeId.toString();
        context.setVariable("url", url);
        String subject = mailMessageSource.getMessage("commentEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "commentEmailTemplate", context);
    }


    @Async
    @Override
    public void sendRecipeDeletionEmail(String destination, String recipeName, String body, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("recipeName", recipeName);
        context.setVariable("body", body);
        context.setVariable("url", "http://pawserver.it.itba.edu.ar/paw-2023a-05/");
        String subject = mailMessageSource.getMessage("recipeDeletionEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "deletionEmailTemplate", context);
    }

    @Async
    @Override
    public void sendNLikesEmail(String destination, String recipeName, String username, long likes, Long recipeId, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("recipeName", recipeName);
        context.setVariable("username", username);
        context.setVariable("likes", likes);
        String url = "http://pawserver.it.itba.edu.ar/paw-2023a-05/" + "recipeDetail/" + recipeId.toString();
        context.setVariable("url", url);
        String subject = mailMessageSource.getMessage("likesEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "likesEmailTemplate", context);
    }

    @Async
    @Override
    public void sendResetPassword(String destination, String token, Locale locale) {
            String urlToken = "http://pawserver.it.itba.edu.ar/paw-2023a-05/resetPassword/" + token;
//            String urlToken = "http://localhost:8080/resetPassword/" + token;
            Context context = new Context();
            context.setLocale(locale);
            context.setVariable("email", destination);
            context.setVariable("url", urlToken);
            String subject = mailMessageSource.getMessage("resetPassword.subject", new Object[]{}, locale);
            sendEmail(subject, destination, "resetPasswordConfirmation", context);
    }

    @Async
    @Override
    public void sendCommentDeletionEmail(String destination, String recipeName, String commentData, String deletionMotive, Locale locale) {
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("recipeName", recipeName);
        context.setVariable("deletionMotive", deletionMotive);
        context.setVariable("commentData", commentData);
        context.setVariable("url", "http://pawserver.it.itba.edu.ar/paw-2023a-05/");
        String subject = mailMessageSource.getMessage("commentDeletionEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "commentDeletionEmailTemplate", context);
    }

    @Async
    @Override
    public void sendVerificationToken(String destination, String token, String username, Locale locale) {
        String urlToken = "http://pawserver.it.itba.edu.ar/paw-2023a-05/verifyAccount/" + token;
//        String urlToken = "http://localhost:8080/verifyAccount/" + token;
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("url", urlToken);
        context.setVariable("username", username);
        String subject = mailMessageSource.getMessage("verificationTokenEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "verificationAccountEmailTemplate", context);
    }

    @Async
    @Override
    public void sendSuccessfulPasswordChange(String destination, String username, Locale locale) {
        String url = "http://pawserver.it.itba.edu.ar/paw-2023a-05";
//        String url = "http://localhost:8080";
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username", username);
        context.setVariable("url", url);
        String subject = mailMessageSource.getMessage("successfulPasswordChangeEmail.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "successfulChangePasswordEmailTemplate", context);
    }

    @Async
    @Override
    public void sendNewRecipeToFollowers(String destination, String userOwner, String username, long recipeId, String title, Locale locale) {
        String url = "http://pawserver.it.itba.edu.ar/paw-2023a-05/recipeDetail/" + recipeId;
//        String url = "http://localhost:8080/recipeDetail/" + recipeId;
        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username", username);
        context.setVariable("title", title);
        context.setVariable("recipeOwner", userOwner);
        context.setVariable("url", url);
        String subject = mailMessageSource.getMessage("newRecipeFromFollowing.subject", new Object[]{}, locale);
        sendEmail(subject, destination, "newRecipeFromFollowingEmailTemplate", context);
    }
}
