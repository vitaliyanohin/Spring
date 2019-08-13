package com.boraji.tutorial.springboot.controller;

import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.service.UserOrderService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  private final UserOrderService userOrderService;

  public IndexController(UserOrderService userOrderService) {
    this.userOrderService = userOrderService;
  }

  @GetMapping({ "/" })
  public String index(@AuthenticationPrincipal User user, Model model) {
    if (user == null) {
      return "redirect:/index";
    } else {
      if (userOrderService.getBasket(user).isPresent()) {
        user.setBasket(userOrderService.getBasket(user).get());
      }
      return "redirect:/user/userProfile";
    }
  }
}
