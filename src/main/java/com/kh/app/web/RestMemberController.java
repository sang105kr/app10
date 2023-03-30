package com.kh.app.web;

import com.kh.app.domain.member.svc.MemberSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class RestMemberController {

  private final MemberSVC memberSVC;

  //회원아이디 체크
  @GetMapping("/email")
  public RestResponse<Object> isExistEmail(@RequestParam("email") String email){
    log.info("email={}",email);
    RestResponse<Object> res = null;

    //이메일 검증
    String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    if(!matcher.matches()){
      res = RestResponse.createRestResponse("01","이메일 형식이 맞지 않습니다.", null);
      return res;
    }
    boolean exist = memberSVC.isExist(email);
    res = RestResponse.createRestResponse("00","성공", exist);

    return res;
  }



}
