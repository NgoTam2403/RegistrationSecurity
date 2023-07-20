package com.example.TestSecurity.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firtName;
    private final String lastName;
    private final String email;

    private final String password;

}
