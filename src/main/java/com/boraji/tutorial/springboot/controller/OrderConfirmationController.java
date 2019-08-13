package com.boraji.tutorial.springboot.controller;


import com.boraji.tutorial.springboot.model.Order;
import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.service.ProductService;
import com.boraji.tutorial.springboot.service.UserOrderService;
import com.boraji.tutorial.springboot.utils.ConfirmCode;
import com.boraji.tutorial.springboot.utils.SendEmail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.util.List;

@Controller
@SessionAttributes("code")
@RequestMapping("/confirmation")
public class OrderConfirmationController {

  private ProductService productService;
  private UserOrderService userOrderService;

  public OrderConfirmationController(ProductService productService,
                                     UserOrderService userOrderService) {
    this.productService = productService;
    this.userOrderService = userOrderService;
  }

  @ModelAttribute("code")
  public String setUpUserConfirmCode() {
    return ConfirmCode.code();
  }

  private void filter(Model model, User user) {
    List<Product> productList = userOrderService.getProductsFromUserBasket(user);
    Double totalPrice = productService.orderTotalPrice(productList);
    model.addAttribute("productList", productList);
    model.addAttribute("totalPrice", totalPrice);
  }

  @GetMapping("/userOrder")
  private String prepareUserOrder(@AuthenticationPrincipal User user, Model model) {
    filter(model, user);
    return "confirmOrder";
  }

  @GetMapping("/getConfirmCode")
  private String getConfirmOrder(@AuthenticationPrincipal User user,
                                 @RequestParam("email") String email,
                                 @RequestParam("address") String address,
                                 @RequestParam("totalPrice") Double totalPrice,
                                 Model model) {
    filter(model, user);
    String confirmCode = ConfirmCode.code();
    new Thread(() -> SendEmail.sendCode(email, confirmCode, totalPrice)).start();
    model.addAttribute("email", email);
    model.addAttribute("address", address);
    model.addAttribute("code", confirmCode);
    return "confirmOrder";
  }

  @PostMapping("/confirmOrder")
  private String confirmOrder(@AuthenticationPrincipal User user,
                              @SessionAttribute("code") String code,
                              @RequestParam("email") String email,
                              @RequestParam("address") String address,
                              @RequestParam("code") String confirmCodeFromUser,
                              Model model) {
    if (confirmCodeFromUser.equals(code)) {
      Order order = new Order(user, user.getBasket(), address);
      userOrderService.addOrderToDb(order);
      user.dropBasket();
      model.addAttribute("user", user);
      model.addAttribute("inform", "request has been sent! TY!");
      return "UserProfile";
    }
    filter(model, user);
    model.addAttribute("email", email);
    model.addAttribute("address", address);
    model.addAttribute("info", "code is not correct!");
    return "confirmOrder";
  }
}
