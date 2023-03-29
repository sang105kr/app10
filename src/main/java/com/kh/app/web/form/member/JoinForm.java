package com.kh.app.web.form.member;

import lombok.Data;

import java.util.List;

@Data
public class JoinForm {
  private String email;
  private String passwd;
  private String passchk;
  private String nickname;
  private String gender;
  private List<String> hobby;
  private String region;
}

