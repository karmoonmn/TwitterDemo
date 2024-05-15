package com.example.demo3.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class UserRegisterReq {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must have at least one digit"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must have at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must have at least one uppercase letter"),
            @Pattern(regexp = "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).+", message = "Password must have at least one special character")
    })
    private String password;

    @NotBlank(message = "Username cannot be blank")
    private String fullName;
    private String birthDate;
    private List<String> tags;
}
