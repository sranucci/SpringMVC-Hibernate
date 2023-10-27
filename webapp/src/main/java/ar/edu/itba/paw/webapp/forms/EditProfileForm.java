package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.annotations.ValidUserPhoto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Setter
@Getter
public class EditProfileForm {
    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @ValidUserPhoto
    private MultipartFile userPhoto;

    private boolean deleteProfilePhoto;

}