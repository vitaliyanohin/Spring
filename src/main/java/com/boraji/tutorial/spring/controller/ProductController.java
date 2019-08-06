package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.service.ProductService;
import com.boraji.tutorial.spring.service.UserOrderService;
import model.Product;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Product")
public class ProductController {

  private ProductService productService;
  private UserOrderService userOrderService;

  public ProductController(ProductService productService, UserOrderService userOrderService) {
    this.productService = productService;
    this.userOrderService = userOrderService;
  }

  @GetMapping("/product")
  private String productForm() {
    return "newProduct";
  }

  @PostMapping("/newProduct")
  private String newProduct(@RequestParam("product") String product,
                            @RequestParam("description") String description,
                            @RequestParam("price") Double price,
                            Model model) {
    Product newProduct = new Product(product, description, price);
    productService.saveOrUpdateProduct(newProduct);
    model.addAttribute("info", "Product add!");
    return "UserProfile";
  }

  @GetMapping("/allProducts")
  private String allUsers(Model model) {
    if (!productService.getAllProducts().isEmpty()) {
      List<Product> allProductList = productService.getAllProducts();
      model.addAttribute("allProductList", allProductList);
      return "allProducts";
    }
    model.addAttribute("info", "Empty products!");
    return "index";
  }

  @GetMapping("/addProductInBasket")
  private String addProductInBasket(@SessionAttribute("user") User user,
                                    @RequestParam("addInBox") Long productId,
                                    Model model) {
    if (user.getBasketId() == null) {
      userOrderService.createAndAddUserBasketInDb(user);
      model.addAttribute("user", user);
    }
    userOrderService.addProductToBasket(user, productId);
    return "redirect:/Product/allProducts";
  }

  @PostMapping("/delete")
  private String deleteUser(@RequestParam("delete") Long productId) {
    productService.deleteProduct(productId);
    return "redirect:/Product/allProducts";
  }

  @GetMapping("/editProductForm")
  private String deleteProductForm(@RequestParam("edit") Long productId, Model model) {
    Optional<Product> currentProduct = productService.getProductById(productId);
    if (currentProduct.isPresent()) {
      Product product = currentProduct.get();
      model.addAttribute("product", product);
      return "editProduct";
    }
    return "redirect:/Product/allProducts";
  }

  @PostMapping("/editProduct")
  private String editProduct(@RequestParam("edit") Long productId,
                             @RequestParam("product") String productName,
                             @RequestParam("description") String description,
                             @RequestParam("price") Double price) {
    Product product = productService.getProductById(productId).get();
    product.setName(productName);
    product.setDescription(description);
    product.setPrice(price);
    productService.saveOrUpdateProduct(product);
    return "redirect:/Product/allProducts";
  }
}
