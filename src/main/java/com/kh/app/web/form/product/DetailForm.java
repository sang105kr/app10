package com.kh.app.web.form.product;

import lombok.Data;

@Data
public class DetailForm {
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;
}
