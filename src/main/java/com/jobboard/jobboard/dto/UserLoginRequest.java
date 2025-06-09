package com.jobboard.jobboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message="email should not be blank")
    private String email;

    @NotBlank(message = "password should not be blank")
    private String password;


}
