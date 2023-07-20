package com.example.TestSecurity.registration.token;

import com.example.TestSecurity.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(name = "confirmation_sequence",sequenceName = "confirmation_sequence",allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;
    public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt,AppUser appUser) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
    }

}
