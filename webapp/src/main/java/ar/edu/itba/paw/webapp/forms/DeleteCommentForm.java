package ar.edu.itba.paw.webapp.forms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class DeleteCommentForm {

    //message regexp, only us and spanish characters, and numbers, at least one is required

    @NotBlank(message = "Please provide a deletion motive")
    private String deletionMotive;

}
