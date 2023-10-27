package ar.edu.itba.paw.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
    USER("USER"),
    ADMIN("ADMIN"),
    ANONYMOUS("ANONYMOUS");
    private final String role;

}