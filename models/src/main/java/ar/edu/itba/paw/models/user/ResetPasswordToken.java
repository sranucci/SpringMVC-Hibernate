package ar.edu.itba.paw.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_reset_password_token")
@Getter
@Setter
public class ResetPasswordToken {
    private static final Integer TOKEN_DURATION_DAYS = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_reset_password_token_id_seq")
    @SequenceGenerator(sequenceName = "tbl_reset_password_token_id_seq", name = "tbl_reset_password_token_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiration_date", nullable = false)
    private Timestamp expirationDate;

    public ResetPasswordToken(final String token, final User user, final LocalDateTime expirationDate ){
        this.token = token;
        this.user = user;
        this.expirationDate = Timestamp.valueOf(expirationDate);
    }

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    public boolean isValid() {
        return expirationDate.compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0;
    }

    public Long getUserId(){
        return user.getId();
    }


}
