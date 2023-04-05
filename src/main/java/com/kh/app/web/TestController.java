package com.kh.app.web;

import com.kh.app.web.form.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/test/members")
public class TestController {

  // http://localhost:9080/test/members/{id}/{age}> @PathVariable("id")
  @ResponseBody
  @GetMapping("/{id}/{age}")
  public String case1(@PathVariable("id") String id, @PathVariable("age") Long age ){
    log.info("id={},age={}",id,age);
    return id+":"+age;
  }
  // http://localhost:9080/test/members?id=hong&age=30
  @ResponseBody
  @GetMapping
  public String case2(@RequestParam("id") String id, @RequestParam("age") Long age ){
    log.info("id={},age={}",id,age);
    return id+":"+age;
  }

  // http://localhost:9080/test/members
  @ResponseBody
  @GetMapping("/header")
  public String case3(@RequestHeader("id") String id, @RequestHeader("age") Long age ){
    log.info("id={},age={}",id,age);
    return id+":"+age;
  }

  // form ,post 요청 => @RequestParam
  @ResponseBody
  @PostMapping
  public String case4(@RequestParam("id") String id, @RequestParam("age") Long age ){
    log.info("id={},age={}",id,age);
    return id+":"+age;
  }
  // form ,post 요청 => @ModelAttribute
  @ResponseBody
  @PostMapping("/object")
  public String case5(@ModelAttribute Member member){
    log.info("id={},age={}",member.getId(),member.getAge());
    return member.getId()+":"+member.getAge();
  }

  // form ,post 요청, json => @RequestBody
  @ResponseBody
  @PostMapping("/object/json")
  public String case6(@RequestBody Member member){
    log.info("id={},age={}",member.getId(),member.getAge());
    return member.getId()+":"+member.getAge();
  }

  // form ,post 요청, json => @RequestBody
  // 응답, json
  @ResponseBody
  @PostMapping("/object/json2")
  public Member case7(@RequestBody Member member){
    log.info("id={},age={}",member.getId(),member.getAge());
    return member;
  }

  // form ,post 요청, json => @RequestBody
  // 응답, json
  @ResponseBody
  @PostMapping("/object/json3")
  public RestResponse<Object> case8(@RequestBody Member member){
    log.info("id={},age={}",member.getId(),member.getAge());
    RestResponse<Object> res = null;

    res = RestResponse.createRestResponse("00","성공",member);
    return res;
  }
}
