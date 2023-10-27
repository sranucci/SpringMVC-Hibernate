package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.constants.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final Pattern BCRYPT_PATTERN = Pattern
            .compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = us.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No user found for the requested email"));

        if (!BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
            us.updatePasswordById(user.getId(), user.getPassword());
            return loadUserByUsername(email);
        }


        final Collection<GrantedAuthority> authorities = new HashSet<>();
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Roles.ADMIN.getRole()));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Roles.USER.getRole()));
        }

        return new PawAuthUserDetails(user.getEmail(), user.getPassword(), authorities, user.getName(), user.getLastname(), user.getId(), user.isAdmin(), user.isVerified());
    }
}
