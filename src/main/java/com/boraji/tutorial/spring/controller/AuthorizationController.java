package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.service.AccountService;
import com.boraji.tutorial.spring.service.UserOrderService;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import utils.EncryptPassword;

import java.util.Optional;

@Controller
@SessionAttributes("user")
public class AuthorizationController {

  private final AccountService accountService;
  private final UserOrderService userOrderService;

  public AuthorizationController(AccountService accountService,
                                 UserOrderService userOrderService) {
    this.accountService = accountService;
    this.userOrderService = userOrderService;
  }

  @ModelAttribute("user")
  public User setUpUserForm(User userFromDb) {
    return new User();
  }

  @PostMapping("/index2")
  private String doLogin(@ModelAttribute("user") User user,
                          @RequestParam("email") String login,
                          @RequestParam("pass") String pass,
                          @RequestParam("repeatPassword") String repeatPassword,
                          Model model) {
    Optional<User> currentUser = accountService.getUserByLogin(login);
    if (!currentUser.isPresent()) {
      model.addAttribute("info", "User exists, pls Sing UP!");
      model.addAttribute("email", login);
      return "index";
    }

    byte[] salt = currentUser.get().getSalt();
    String encryptPass = EncryptPassword.encryptPassword(pass, salt);
    if (pass.equals(repeatPassword)
            & currentUser.get().getPassword().equals(encryptPass)) {
      user = accountService.getUserByLogin(user.getEmail()).get();
      if (userOrderService.getBasket(user).isPresent()) {
        user.setBasketId(userOrderService.getBasket(user).get());
      }
      model.addAttribute("user", user);
      return "redirect:/user/userProfile";
    } else {
      model.addAttribute("info", "Your password not equals!");
      model.addAttribute("email", login);
      return "index";
    }
  }
}
