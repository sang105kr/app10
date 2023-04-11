package com.kh.app.web;

import com.kh.app.domain.common.util.CommonCode;
import com.kh.app.domain.common.util.PasswordGenerator;
import com.kh.app.domain.entity.Code;
import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.svc.MemberSVC;
import com.kh.app.web.common.CodeDecode;
import com.kh.app.web.form.member.JoinForm;
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
  public String findPWForm(){

    return "member/findPW";
  }

  @PostMapping("findPW")
  public String findPW(@RequestParam String email, @RequestParam String nickname){

    //1) email,nickname 인 회원 찾기
    boolean isExist = memberSVC.isExistByEmailAndNickname(email, nickname);
    if(!isExist){

      return "member/findPW";
    }
    //2) 임시비밀 번호 생성
    PasswordGenerator.PasswordGeneratorBuilder passwordGeneratorBuilder = new PasswordGenerator.PasswordGeneratorBuilder();
    String pwd = passwordGeneratorBuilder
        .useDigits(true)  //숫자포함여부
        .useLower(true)   //소문자포함
        .useUpper(true)   //대문자포함
        .usePunctuation(false) //특수문자포함
        .build()
        .generate(6); //비밀번호 자리수

    //3) 회원의 비밀번호를 임시비밀번호로 변경


    //4) 메일 발송.

    return "member/findPW";
  }



}
