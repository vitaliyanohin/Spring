package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.service.UserOrderService;
import model.User;
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
      return "redirect:/login";
    } else {
      if (userOrderService.getBasket(user).isPresent()) {
        user.setBasketId(userOrderService.getBasket(user).get());
      }
      return "redirect:/user/userProfile";
    }
  }
}
