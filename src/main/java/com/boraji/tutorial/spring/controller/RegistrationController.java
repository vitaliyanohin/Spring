package com.boraji.tutorial.spring.controller;


import com.boraji.tutorial.spring.service.AccountService;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utils.EncryptPassword;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {

  private final AccountService accountService;

  public RegistrationController(AccountService accountService) {
    this.accountService = accountService;
  }
  @GetMapping
  private String getregistrationForm() {
    return "register";
  }

  @PostMapping()
  private String registation(@RequestParam("email") String email,
                             @RequestParam("pass") String pass,
                             @RequestParam(value = "role", required = false, defaultValue = "") String role,
                             @RequestParam("repeatPassword") String repeatPassword,
                             Model model) {
    Optional<User> currentUser = accountService.getUserByLogin(email);
    if (email.isEmpty() | pass.isEmpty() | repeatPassword.isEmpty()) {
      model.addAttribute("info", "empty fields!!!");
      return "index";
    }
    if (currentUser.isPresent()) {
      model.addAttribute("info", "such user already exists!");
      return "index";
    }
    if (pass.equals(repeatPassword)) {
      byte [] salt = EncryptPassword.getSalt();
      pass = EncryptPassword.encryptPassword(pass, salt);
      User userProfile = new User(email, pass, role);
      userProfile.setSalt(salt);
      accountService.addUser(userProfile);
      return "redirect:/User/UserProfile";
    } else {
      model.addAttribute("info", "Your password not equals!");
      model.addAttribute("email", email);
      return "register.jsp";
    }
  }
}
