package com.kh.app.web;

import com.kh.app.domain.common.mail.MailService;
import com.kh.app.domain.common.util.CommonCode;
import com.kh.app.domain.common.util.PasswordGenerator;
import com.kh.app.domain.entity.Code;
import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.svc.MemberSVC;
import com.kh.app.web.common.CodeDecode;
import com.kh.app.web.form.member.FindPWForm;
import com.kh.app.web.form.member.JoinForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;
  private final CommonCode commonCode;
  private final MailService ms;

  @ModelAttribute("hobbies")
  public List<CodeDecode> hobbies(){
    List<CodeDecode> codes = new ArrayList<>();
//    codes.add(new CodeDecode("독서","독서"));
//    codes.add(new CodeDecode("수영","수영"));
//    codes.add(new CodeDecode("등산","등산"));
//    codes.add(new CodeDecode("골프","골프"));
    List<Code> findedCodes = commonCode.findCodesByPcodeId("A01");
    //case1)
    //  findedCodes.stream()
    //    .forEach(ele->codes.add(new CodeDecode(ele.getCodeId(),ele.getDecode())));
    //case2)
    for(Code code : findedCodes){
      codes.add(new CodeDecode(code.getCodeId(),code.getDecode()));
    }
    return codes;
  }

  @ModelAttribute("regions")
  public List<CodeDecode> regions(){
    List<CodeDecode> codes = new ArrayList<>();
//    codes.add(new CodeDecode("서울","서울"));
//    codes.add(new CodeDecode("부산","부산"));
//    codes.add(new CodeDecode("대구","대구"));
//    codes.add(new CodeDecode("울산","울산"));

    List<Code> findedCodes = commonCode.findCodesByPcodeId("A02");
    findedCodes.stream()
        .forEach(ele->codes.add(new CodeDecode(ele.getCodeId(),ele.getDecode())));
    return codes;
  }


  //회원가입양식
  @GetMapping("/add")
  public String joinForm(Model model){
    model.addAttribute("joinForm", new JoinForm());
    return "member/joinForm";
  }

  //회원가입처리
  @PostMapping("/add")
  public String join(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult){
    log.info("joinForm={}",joinForm);
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "member/joinForm";
    }

    //비밀번호 체크
    if(!joinForm.getPasswd().equals(joinForm.getPasswdchk())) {
      bindingResult.reject("passwd","비밀번호가 동일하지 않습니다.");
      log.info("bindingResult={}",bindingResult);
      return "member/joinForm";
    }
    Member member = new Member();
    member.setEmail(joinForm.getEmail());
    member.setPasswd(joinForm.getPasswd());
    member.setNickname(joinForm.getNickname());
    member.setGender(joinForm.getGender());
    member.setHobby(hobbyToString(joinForm.getHobby()));
    member.setRegion(joinForm.getRegion());

    memberSVC.save(member);
    return "member/joinSuccess";
  }


  private String hobbyToString(List<String> hobby) {
    return StringUtils.join(hobby,",");
  }



//  GET	/members/findPW
  @GetMapping("/findPW")
//  public String findPWForm(Model model){
//
//    model.addAttribute("findPWForm", new FindPWForm());
//    return "member/findPW";
//  }

  public String findPWForm(@ModelAttribute FindPWForm findPWForm){
    return "member/findPW";
  }

  @PostMapping("/findPW")
  public String findPW(
      @Valid @ModelAttribute FindPWForm findPWForm,
      BindingResult bindingResult,
      HttpServletRequest request,
      Model model
  ){
    log.info("findPWForm={}", findPWForm);

    if(bindingResult.hasErrors()){
      return "member/findPW";
    }

    //1) email,nickname 인 회원 찾기
    boolean isExist = memberSVC.isExistByEmailAndNickname(findPWForm.getEmail(), findPWForm.getNickname());
    if(!isExist){

      return "member/findPW";
    }
    //2) 임시비밀 번호 생성
    PasswordGenerator.PasswordGeneratorBuilder passwordGeneratorBuilder = new PasswordGenerator.PasswordGeneratorBuilder();
    String tmpPwd = passwordGeneratorBuilder
        .useDigits(true)  //숫자포함여부
        .useLower(true)   //소문자포함
        .useUpper(true)   //대문자포함
        .usePunctuation(false) //특수문자포함
        .build()
        .generate(6); //비밀번호 자리수

    //3) 회원의 비밀번호를 임시비밀번호로 변경
    memberSVC.changePasswd(findPWForm.getEmail(),tmpPwd);

    //4) 메일 발송.
    String subject = "신규 비밀번호 전송";

    //로긴주소
    StringBuilder url = new StringBuilder();
    url.append("http://" + request.getServerName());    //localhost
    url.append(":" + request.getServerPort());          //prot
    url.append(request.getContextPath());               // /
    url.append("/login");

    //메일본문내용
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>");
    sb.append("<html lang='ko'>");
    sb.append("<head>");
    sb.append("  <meta charset='UTF-8'>");
    sb.append("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    sb.append("  <title>임시 비밀번호 발송</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("  <h1>신규비밀번호</h1>");
    sb.append("  <p>아래 비밀번호로 로그인 하셔서 비밀번호를 변경하세요</p>");
    sb.append("  <p>비밀번호 :" + tmpPwd + "</p>");
    sb.append("  <a href='"+ url +"'>로그인</a>");
    sb.append("</body>");
    sb.append("</html>");

    ms.sendMail(findPWForm.getEmail(), subject , sb.toString());

    model.addAttribute("info", "회원 이메일로 임시번호가 발송되었습니다");

    return "member/findPW";
  }

}
