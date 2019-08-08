package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

  private final UserService userService;

  public IndexController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(path = {"/"}, method = RequestMethod.GET)
  public String indexForm() {
    return "index";
  }
}
