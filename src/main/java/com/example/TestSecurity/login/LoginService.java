package com.example.TestSecurity.login;

import com.example.TestSecurity.appuser.AppUserSevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final AppUserSevice appUserSevice;


    public Enum login(LoginRequest request) {
        Enum testlogin= appUserSevice.Login(
                request.getEmail(),
                request.getPassword());
        return testlogin;
    }
}
