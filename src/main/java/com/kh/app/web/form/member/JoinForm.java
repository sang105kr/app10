package com.kh.app.web.form.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class JoinForm {
  @Email
  private String email;
  @NotBlank
  @Size(min=4,max=12)
  private String passwd;
  @NotBlank
  @Size(min=4,max=12)
  private String passwdchk;

  @Size(max=10)
  private String nickname;
  private String gender;
  private List<String> hobby;
  private String region;
}

