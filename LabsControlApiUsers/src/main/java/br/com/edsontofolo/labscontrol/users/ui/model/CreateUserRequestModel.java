package br.com.edsontofolo.labscontrol.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
    @NotNull(message = "First Name cannot be null")
    @Size(min = 3, max = 256, message = "First Name must be less than {0} and more than {0}")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 3, max = 256, message = "Last Name must be less than {0} and more than {0}")
    private String lastName;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 256, message = "Last Name must be less than {0} and more than {0}")
    private String password;
    @NotNull(message = "Password cannot be null")
    @Email(message = "Email needs to be valid")
    private String email;

    public CreateUserRequestModel() {
    }

    public CreateUserRequestModel(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
