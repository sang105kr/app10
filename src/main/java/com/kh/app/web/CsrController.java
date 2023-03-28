package com.kh.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/csr")
public class CsrController {

  @GetMapping("/products")
  public String manageProduct(){
    return "/csr/product/product";
  }
}
