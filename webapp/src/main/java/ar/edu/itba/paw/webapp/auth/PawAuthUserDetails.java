package ar.edu.itba.paw.webapp.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class PawAuthUserDetails extends User {

    private String name;
    private String lastName;
    private boolean isAdmin;
    private Long id;
    private boolean isVerified;


    public PawAuthUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String name, String lastName, Long id, boolean isAdmin, boolean isVerified) {
        super(username, password, isVerified, true, true, true, authorities);
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.isAdmin = isAdmin;
        this.isVerified = isVerified;
    }

    public PawAuthUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


}
