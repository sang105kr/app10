package com.kh.app.web.form.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateForm {
  private Long productId;
  @NotBlank
  private String pname;
  @NotNull
  private Long quantity;
  @NotNull
  private Long price;
}
