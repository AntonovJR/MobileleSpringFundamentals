package com.example.mobilele.web;

import com.example.mobilele.models.binding.UserLoginBindingModel;
import com.example.mobilele.models.binding.UserRegisterBindingModel;
import com.example.mobilele.models.serviceModels.UserLoginServiceModel;
import com.example.mobilele.models.serviceModels.UserRegisterServiceModel;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserControllers {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllers.class);
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UserControllers(UserService userService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }


    @GetMapping("/users/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("auth-register");
        return modelAndView;
    }

    @PostMapping("/users/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        LOGGER.info("User tried to register. User name {}", userRegisterBindingModel.getUsername());
        if (userService.isUsernameFree(userRegisterBindingModel.getUsername())) {
            UserRegisterServiceModel userRegisterServiceModel = modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
            userService.registerUser(userRegisterServiceModel);

            return "redirect:/";
        }

            model.addAttribute("usernameOccupied", true);
            return "redirect:/users/register";

    }

    @GetMapping("/users/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("auth-login");
        return modelAndView;
    }

    @PostMapping("/users/login")
    public String login(@Valid UserLoginBindingModel userLoginBindingModel,
                        BindingResult loginBindingResult,
                        RedirectAttributes redirectAttributes) {

        if (loginBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", loginBindingResult);
            return "redirect:/users/login";
        }

        LOGGER.info("User tried to login. User name {}", userLoginBindingModel.getUsername());

        UserLoginServiceModel userLoginServiceModel = new UserLoginServiceModel();
        userLoginServiceModel.setUsername(userLoginBindingModel.getUsername());
        userLoginServiceModel.setPassword(userLoginBindingModel.getPassword());
        boolean loginSuccessful = userService.loginUser(userLoginServiceModel);

        if (loginSuccessful) {
            return "redirect:/";
        }

        return "redirect:/users/login";
    }


    @GetMapping("/users/logout")
    public String logout() {
        userService.logOut();
        return "redirect:/";
    }
}
