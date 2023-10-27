package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.comments.CommentLikeData;
import ar.edu.itba.paw.models.recipe.SavedRecipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_user_id_seq")
    @SequenceGenerator(sequenceName = "tbl_user_user_id_seq", name = "tbl_user_user_id_seq", allocationSize = 1)
    @Column(name="user_id")
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name="last_name", length = 50, nullable = false)
    private String lastname;

    @Column(name="is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @Column(name="is_verified", nullable = false)
    private boolean isVerified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserImage userImage;

    @Column(nullable = false)
    private Locale locale;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommentLikeData> commentLikeData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedRecipe> savedRecipes;

    @Formula("(SELECT COUNT(*) FROM tbl_follows f WHERE f.user_followed_id = user_id)")
    private long followersCount;

    @Formula("(SELECT COUNT(*) FROM tbl_follows f WHERE f.user_id = user_id)")
    private long followingCount;

    @Transient
    private boolean isFollowedByCurrentUser;


    public User(final String email, final String password,final String name, final String lastname,
               final boolean isAdmin,final boolean isVerified, Locale locale) {
        this.email = email;
        this.password=password;
        this.name=name;
        this.lastname=lastname;
        this.isAdmin= isAdmin;
        this.isVerified=isVerified;
        this.locale=locale;
        this.createdAt = Date.from(Instant.now());

    }

    public User(long user_id, String email, String password, String name, String last_name, boolean is_admin, boolean is_verified, Locale locale) {
        this.id = user_id;
        this.email = email;
        this.password=password;
        this.name=name;
        this.lastname=last_name;
        this.isAdmin= is_admin;
        this.isVerified=is_verified;
        this.locale = locale;
        this.createdAt = Date.from(Instant.now());

    }

    public boolean getIsFollowedByCurrentUser() {
        return isFollowedByCurrentUser;
    }

}
