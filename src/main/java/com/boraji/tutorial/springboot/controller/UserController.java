package com.boraji.tutorial.springboot.controller;


import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.service.AccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

  private final AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserController(AccountService accountService,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @GetMapping("/userProfile")
  private String registation(@AuthenticationPrincipal User user, Model model) {
    model.addAttribute("user", user);
    return "UserProfile";
  }

  @GetMapping("/admin/all")
  private String allUsers(Model model) {
    if (!accountService.getAllUsers().isEmpty()) {
      List<User> allUserList = accountService.getAllUsers();
      model.addAttribute("allUserList", allUserList);
      return "allUsers";
    }
    model.addAttribute("inform", "Empty user list!");
    return "index";
  }

  @PostMapping("/admin/delete")
  private String deleteUser(@RequestParam("delete") Long userId) {
    accountService.deleteUser(userId);
    return "redirect:/user/all";
  }

  @GetMapping("/admin/update")
  private String getUpdateForm(@RequestParam("edit") Long userId, Model model) {
    Optional<User> currentUser = accountService.getUserById(userId);
    if (currentUser.isPresent()) {
      User user = currentUser.get();
      model.addAttribute("user", user);
      return "editUser";
    }
    return "redirect:/User/AllUsers";
  }

  @PostMapping("/update")
  private String updateUser(@RequestParam("edit") Long userId,
                            @RequestParam("email") String login,
                            @RequestParam("pass") String pass,
                            @RequestParam("repeatPassword") String repeatPassword,
                            @RequestParam("role") String role) {
    User user = accountService.getUserById(userId).get();
    if (!login.isEmpty()) {
      user.setEmail(login);
    }
    if (pass.equals(repeatPassword) & !pass.isEmpty()) {
      user.setPassword(bCryptPasswordEncoder.encode(pass));
    }
    if (role != null && !role.equals(user.getRole())) {
      user.setRole(role);
    }
    accountService.saveOrUpdateUser(user);
    return "redirect:/user/all";
  }
}
