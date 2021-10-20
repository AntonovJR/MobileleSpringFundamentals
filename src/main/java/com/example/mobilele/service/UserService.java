package com.example.mobilele.service;

import com.example.mobilele.models.serviceModels.UserLoginServiceModel;
import com.example.mobilele.models.serviceModels.UserRegisterServiceModel;

public interface UserService {
    boolean loginUser(UserLoginServiceModel loginServiceModel);
    void logOut();
    boolean registerUser(UserRegisterServiceModel userRegisterServiceModel);
    boolean isUsernameFree(String username);
}
