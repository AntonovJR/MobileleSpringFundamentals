package com.example.mobilele.models.binding;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
