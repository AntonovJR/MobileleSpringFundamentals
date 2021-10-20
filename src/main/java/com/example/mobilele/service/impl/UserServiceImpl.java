package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.User;
import com.example.mobilele.models.entity.UserRole;
import com.example.mobilele.models.enums.Role;
import com.example.mobilele.models.serviceModels.UserLoginServiceModel;
import com.example.mobilele.models.serviceModels.UserRegisterServiceModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.repository.UserRoleRepository;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CurrentUser currentUser, UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean loginUser(UserLoginServiceModel loginServiceModel) {
        Optional<User> userOptional = userRepository.findByUsername(loginServiceModel.getUsername());
        if (userOptional.isEmpty()) {
            logOut();
            return false;
        } else {
            boolean success = passwordEncoder.matches(loginServiceModel.getPassword(), userOptional.get().getPassword());
            if (success) {
                User loggedInUser = userOptional.get();
                currentUser.setLoggedIn(true);
                currentUser.setUsername(loggedInUser.getUsername());
                currentUser.setFirstName(loggedInUser.getFirstName());
                currentUser.setLastName(loggedInUser.getLastName());
                currentUser.setAdmin(loggedInUser.getRole().getId() == 1);

            }
            return success;
        }

    }

    @Override
    public void logOut() {
        currentUser.clean();
    }

    @Override
    public boolean registerUser(UserRegisterServiceModel userRegisterServiceModel) {
        User user = new User();
        user.setUsername(userRegisterServiceModel.getUsername());
        user.setLastName(userRegisterServiceModel.getLastName());
        user.setFirstName(userRegisterServiceModel.getFirstName());
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));
        user.setActive(true);
        UserRole userRole = userRoleRepository.getById(2L);
        user.setRole(userRole);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
        login(user);
        return true;

    }




    @Override
    public boolean isUsernameFree(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    private void login(User user) {
        currentUser.setLoggedIn(true);
        currentUser.setUsername(user.getUsername());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setAdmin(user.getRole().getId() == 1);

    }


}
