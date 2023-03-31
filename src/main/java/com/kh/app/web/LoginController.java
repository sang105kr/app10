package com.kh.app.web;

import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.svc.MemberSVC;
import com.kh.app.web.form.login.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  //로그인화면
  @GetMapping("/login")
  public String loginForm(Model model){
    model.addAttribute("loginForm", new LoginForm());
    return "login";
  }

  //로그인처리
  @PostMapping("/login")
  public String login(
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult,
      HttpServletRequest httpServletRequest,
      @RequestParam(required = false, defaultValue = "/") String redirectUrl
      ){

    log.info("redirectUrl={}",redirectUrl);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "login";
    }

    //1)아이디 존재유무
    if(!memberSVC.isExist(loginForm.getEmail())){
      bindingResult.rejectValue("email","login","아이디가 존재하지 않습니다.");
      return "login";
    }

    //2)로그인
    Optional<Member> member = memberSVC.login(loginForm.getEmail(), loginForm.getPasswd());
    if(member.isEmpty()){
      bindingResult.rejectValue("passwd","login","비밀번호가 일치하지 않습니다.");
      return "login";
    }

    //3)세션 생성
    //세션이 있으면 해당 정보를 가져오고 없으면 세션생성
    HttpSession session = httpServletRequest.getSession(true);
    LoginMember loginMember = new LoginMember(
          member.get().getMemberId(),
          member.get().getEmail(),
          member.get().getNickname(),
          member.get().getGubun()  );
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:"+redirectUrl;
  }

  //로그아웃
  @GetMapping("logout")
  public String logout(HttpServletRequest httpServletRequest){
    //세션이 있으면 해당 정보를 가져오고 없으면 세션생성 하지 않음
    HttpSession session = httpServletRequest.getSession(false);
    if(session != null){
      session.invalidate();   //세션 제거
    }
    return "redirect:/";
  }
}
