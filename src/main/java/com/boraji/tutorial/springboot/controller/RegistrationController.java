package com.boraji.tutorial.springboot.controller;



import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.service.AccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {

  private final AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public RegistrationController(AccountService accountService,
                                BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
      pass = bCryptPasswordEncoder.encode(pass);
      User userProfile = new User(email, pass, role);
      accountService.addUser(userProfile);
      return "redirect:/user/userProfile";
    } else {
      model.addAttribute("info", "Your password not equals!");
      model.addAttribute("email", email);
      return "register.jsp";
    }
  }
}
