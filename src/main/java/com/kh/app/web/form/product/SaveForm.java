package com.kh.app.web.form.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SaveForm {

  @NotBlank  //null과 빈문자열("")을 허용 안함, 문자열 타입에만 사용
  @Size(min=2,max=10)
  private String pname;
  @NotNull  //모든 타입에 대해 null허용 안함
  @Positive //양수
  @Max(1000) //최대값
  private Long quantity;
  @NotNull
  @Positive //양수
  @Min(1000) //최소값
  private Long price;
  //@NotEmpty null,빈문자열(""),공백문자(" ") 허용안함,
  //           문자열,컬렉션타입(요소가 1개이상 존재)에 사용

}
