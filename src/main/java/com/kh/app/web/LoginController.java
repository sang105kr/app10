package com.kh.app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {

  //로그인화면
  @GetMapping("/login")
  public String loginForm(){

    return "login";
  }

  //로그인처리
  @PostMapping("/login")
  public String login(){

    return "redirect:/";
  }

  //로그아웃
  @GetMapping("logout")
  public String logout(){

    return "redirect:/";
  }
}
