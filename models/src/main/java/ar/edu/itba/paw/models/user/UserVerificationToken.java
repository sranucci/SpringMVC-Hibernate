package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user_verification_token")
@Getter
@Setter
public class UserVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_verification_token_id_seq")
    @SequenceGenerator(sequenceName = "tbl_user_verification_token_id_seq", name = "tbl_user_verification_token_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column( nullable = false )
    private String token;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    public UserVerificationToken(final String token, final User user){
        this.token=token;
        this.user=user;
    }

}
