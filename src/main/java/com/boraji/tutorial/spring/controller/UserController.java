package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.service.AccountService;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import utils.EncryptPassword;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/User")
public class UserController {

  private final AccountService accountService;

  public UserController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/UserProfile")
  private String registation(@SessionAttribute("user") User user, Model model) {
    model.addAttribute("user", user);
    return "UserProfile";
  }

  @GetMapping("/AllUsers")
  private String allUsers(Model model) {
    if (!accountService.getAllUsers().isEmpty()) {
      List<User> allUserList = accountService.getAllUsers();
      model.addAttribute("allUserList", allUserList);
      return "allUsers";
    }
    model.addAttribute("info", "Empty user list!");
    return "index";
  }

  @PostMapping("/delete")
  private String deleteUser(@RequestParam("delete") Long userId) {
    accountService.deleteUser(userId);
    return "redirect:/User/AllUsers";
  }

  @GetMapping("/updateUserJSP")
  private String getUpdateForm(@RequestParam("edit") Long userId, Model model) {
    Optional<User> currentUser = accountService.getUserById(userId);
    if (currentUser.isPresent()) {
      User user = currentUser.get();
      model.addAttribute("user", user);
      return "editUser";
    }
    return "redirect:/User/AllUsers";
  }

  @PostMapping("/updateUser")
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
      user.setPassword(EncryptPassword.encryptPassword(pass).toString());
    }
    if (role != null && !role.equals(user.getRole())) {
      user.setRole(role);
    }
    accountService.saveOrUpdateUser(user);
    return "redirect:/User/AllUsers";
  }
}
