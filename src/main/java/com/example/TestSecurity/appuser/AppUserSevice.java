package com.example.TestSecurity.appuser;

import com.example.TestSecurity.registration.token.ConfirmationToken;
import com.example.TestSecurity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.TestSecurity.appuser.Login.*;

@Service
@AllArgsConstructor
public class AppUserSevice implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG="user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }
    public String signUp(AppUser appUser){
        boolean userexists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userexists){
            throw new IllegalStateException("email alreade");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        //send cofirmation  token
        String token = UUID.randomUUID().toString();
        appUserRepository.save(appUser);
        ConfirmationToken confirmationToken = new ConfirmationToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),
                appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //send email

        return token;
    }
    public Enum Login(String email,String password) {
        AppUser user = appUserRepository.findUserByEmail(email);
        if (user != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            if (encodedPassword == user.getPassword()) {
                if (user.isEnabled()){
                    return SUCESSFULLY;
                }else
                    return EMAIL_NOT_VALIDATE;
            } else {
                return PASSWORD_ERROR;
            }

        } else {
            return EMAIL_NOT_FOUND;
        }


    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
