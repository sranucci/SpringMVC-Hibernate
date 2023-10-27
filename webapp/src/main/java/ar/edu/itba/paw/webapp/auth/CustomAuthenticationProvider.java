package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();

        Optional<User> maybeUser = userService.verifyAccount(token);

        if ( !maybeUser.isPresent()){
            LOGGER.info("User not verified");
            throw new UsernameNotFoundException("Token not valid for this user");
        }

        User user = maybeUser.get();

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }


}
