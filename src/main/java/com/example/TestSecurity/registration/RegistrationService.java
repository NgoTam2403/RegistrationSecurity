package com.example.TestSecurity.registration;

import com.example.TestSecurity.appuser.AppUser;
import com.example.TestSecurity.appuser.AppUserRole;
import com.example.TestSecurity.appuser.AppUserSevice;
import com.example.TestSecurity.email.EmailSender;
import com.example.TestSecurity.registration.token.ConfirmationToken;
import com.example.TestSecurity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserSevice appUserSevice;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    public String register(RegistrationRequest request) {
        boolean isvalidEmail = emailValidator.test(request.getEmail());
        if(!isvalidEmail){
            throw new IllegalStateException("email khong hop le");
        }


        String token= appUserSevice.signUp(new
                AppUser(request.getFirtName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
        String link = "http://localhost:8080/api/v1/registration/confirm?token="+token;
        emailSender.send(request.getEmail(),"welcome to application happy birthday "+link);
        return token;
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserSevice.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
