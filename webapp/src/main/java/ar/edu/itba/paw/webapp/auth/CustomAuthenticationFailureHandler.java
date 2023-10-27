package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.servicesInterface.exceptions.FailedToLoadResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception instanceof DisabledException) {
            try {
                response.sendRedirect(request.getContextPath() + "/loginNotVerified");
            } catch (Exception e) {
                LOGGER.info("Response redirect to loginNotVerified FAILED {}", e.getMessage());
                throw new FailedToLoadResourceException("Could not redirect");
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/login?error");
            } catch (Exception e) {
                LOGGER.info("Response redirect to /login?error FAILED {}", e.getMessage());
                throw new FailedToLoadResourceException("Could not redirect");
            }
        }
    }
}
