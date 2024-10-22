package code.climb.cccars.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignupDTO {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String firstName;

    private String lastName;
    private String phoneNumber;

    @NotBlank
    private String password;

    public @NotNull String getUsername() {
        return username;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public @NotNull String getPassword() {
        return password;
    }
}
