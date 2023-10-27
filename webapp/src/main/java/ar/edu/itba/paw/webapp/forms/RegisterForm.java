package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.annotations.RepeatPasswordMatchesPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@RepeatPasswordMatchesPassword(passwordField = "password", repeatPasswordField = "repeatPassword")
public class RegisterForm {

    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Size(min = 8, max = 100)
    private String password;

    @Size(min = 8, max = 100)
    private String repeatPassword;

}
