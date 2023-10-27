package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.webapp.auth.PawAuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class BaseUserTemplateController {

    @Autowired
    UserService userService;

    //user data for the screen to render
    private Optional<PawAuthUserDetails> getUserCredentials() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof PawAuthUserDetails) {
            PawAuthUserDetails auth = (PawAuthUserDetails) o;
            return Optional.of(auth);
        }
        return Optional.empty();
    }


    @ModelAttribute("currentUser")
    public Optional<User> getUserDto() {
        if (getUserCredentials().isPresent()) {
            return userService.findById(getUserCredentials().get().getId());
        }
        return Optional.empty();
    }

    protected Optional<Long> getCurrentUserId() {
        Optional<PawAuthUserDetails> currentUser = getUserCredentials();
        return currentUser.map(PawAuthUserDetails::getId);
    }


}
