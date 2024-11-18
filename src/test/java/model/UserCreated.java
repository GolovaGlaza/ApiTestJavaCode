package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreated {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("surname")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("plain_password")
    private String plain_password;


    public UserCreated(String firstName, String lastName, String email, String username, String plain_password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.plain_password = plain_password;
    }


    public String getPlain_password() {
        return plain_password;
    }

    public void setPlain_password(String plain_password) {
        this.plain_password = plain_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
