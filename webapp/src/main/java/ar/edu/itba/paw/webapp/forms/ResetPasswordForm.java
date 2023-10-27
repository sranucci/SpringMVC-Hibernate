package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.annotations.RepeatPasswordMatchesPassword;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@RepeatPasswordMatchesPassword(passwordField = "password", repeatPasswordField = "repeatPassword")
public class ResetPasswordForm {


    @Size(min = 8, max = 100)
    private String password;

    @Size(min = 8, max = 100)
    private String repeatPassword;

    @NotEmpty
    private String token;

}