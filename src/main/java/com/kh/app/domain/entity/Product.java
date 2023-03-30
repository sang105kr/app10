package com.kh.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@ToString, @EqualsAndHashCode, @Getter,  @Setter, @RequiredArgsConstructor
@AllArgsConstructor //모든멤버필드를 매개변수로하는 생성자 생성
@NoArgsConstructor  //디폴트생성자
public class Product {
  private Long productId; // PRODUCT_ID	NUMBER(10,0)
  private String pname;   //PNAME	VARCHAR2(30 BYTE)
  private Long quantity;  //QUANTITY	NUMBER(10,0)
  private Long price;     //PRICE	NUMBER(10,0)

}
