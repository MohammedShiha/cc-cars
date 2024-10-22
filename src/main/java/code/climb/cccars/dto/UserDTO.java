package code.climb.cccars.dto;

public class UserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private Boolean isEnabled;
    private Boolean isVerified;
    private String profilePictureUrl;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}
