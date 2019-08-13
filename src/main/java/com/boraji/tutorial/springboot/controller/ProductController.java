package com.boraji.tutorial.springboot.controller;


import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.service.ProductService;
import com.boraji.tutorial.springboot.service.UserOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

  private ProductService productService;
  private UserOrderService userOrderService;

  public ProductController(ProductService productService, UserOrderService userOrderService) {
    this.productService = productService;
    this.userOrderService = userOrderService;
  }

  @GetMapping("/admin/create")
  private String productForm() {
    return "newProduct";
  }

  @PostMapping("/admin/create")
  private String create(@RequestParam("product") String product,
                            @RequestParam("description") String description,
                            @RequestParam("price") Double price,
                            Model model) {
    Product newProduct = new Product(product, description, price);
    productService.saveOrUpdateProduct(newProduct);
    model.addAttribute("info", "Product add!");
    return "UserProfile";
  }

  @GetMapping("/all")
  private String allProduct(Model model) {
    if (!productService.getAllProducts().isEmpty()) {
      List<Product> allProductList = productService.getAllProducts();
      model.addAttribute("allProductList", allProductList);
      return "allProducts";
    }
    model.addAttribute("info", "Empty products!");
    return "index";
  }

  @GetMapping("/basket/add")
  private String addProductInBasket(@AuthenticationPrincipal User user,
                                    @RequestParam("addInBox") Long productId,
                                    Model model) {
    if (user.getBasket() == null) {
      userOrderService.addUserBasket(user);
      model.addAttribute("user", user);
    }
    userOrderService.addProductToBasket(user, productId);
    return "redirect:/product/all";
  }

  @PostMapping("/admin/delete")
  private String deleteUser(@RequestParam("delete") Long productId) {
    productService.deleteProduct(productId);
    return "redirect:/product/all";
  }

  @GetMapping("/admin/edit")
  private String deleteProductForm(@RequestParam("edit") Long productId, Model model) {
    Optional<Product> currentProduct = productService.getProductById(productId);
    if (currentProduct.isPresent()) {
      Product product = currentProduct.get();
      model.addAttribute("product", product);
      return "editProduct";
    }
    return "redirect:/product/all";
  }

  @PostMapping("/admin/edit")
  private String editProduct(@RequestParam("edit") Long productId,
                             @RequestParam("product") String productName,
                             @RequestParam("description") String description,
                             @RequestParam("price") Double price) {
    Product product = productService.getProductById(productId).get();
    product.setName(productName);
    product.setDescription(description);
    product.setPrice(price);
    productService.saveOrUpdateProduct(product);
    return "redirect:/product/all";
  }
}
