package com.example.TestSecurity.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService; // Chỉnh sửa tên ở đây

    @PostMapping
    public Enum login(@RequestBody LoginRequest request){

        return loginService.login(request);
    }
}
