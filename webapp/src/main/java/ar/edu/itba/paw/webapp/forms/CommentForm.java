package ar.edu.itba.paw.webapp.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentForm {

    @Size(min = 1, max = 256)
    private String comment;

}
