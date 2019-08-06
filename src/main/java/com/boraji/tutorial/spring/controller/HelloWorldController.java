package com.boraji.tutorial.spring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import com.boraji.tutorial.spring.service.UserService;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {

   private final UserService userService;

   public HelloWorldController(UserService userService) {
      this.userService = userService;
   }

   @RequestMapping(path={"/"},method=RequestMethod.GET)
   public String sayHello(Model model) {
      return "index";
   }
}
