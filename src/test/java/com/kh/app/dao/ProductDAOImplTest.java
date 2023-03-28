package com.kh.app.dao;

import com.kh.app.domain.entity.Product;
import com.kh.app.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  //등록
  @Test
  @DisplayName("상품등록")
  void save(){
    Product product = new Product();
    product.setPname("복사기");
    product.setQuantity(10L);
    product.setPrice(1000000L);

    Long productId = productDAO.save(product);
    log.info("productId={}",productId);
    Assertions.assertThat(productId).isGreaterThan(0L);
  }

  //조회
  @Test
  @DisplayName("상품조회")
  void findById(){
    Long productId = 163L;
    Optional<Product> product = productDAO.findById(productId);
//    if(product.isPresent()) {
//      log.info("product={}", product.get());
//    }else{
//      log.info("조회한 결과 없음");
//    }
//    Assertions.assertThat(product.stream().count())
//        .isEqualTo(1);
    Product findedProduct = product.orElseThrow();// 없으면 java.util.NoSuchElementException
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("복사기");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(10L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1000000L);
  }

  //수정
  @Test
  @DisplayName("상품수정")
  void update() {
    Long productId = 163L;
    Product product = new Product();
    product.setPname("복사기_수정");
    product.setQuantity(20L);
    product.setPrice(2000000L);
    int updatedRowCount = productDAO.update(productId, product);
    Optional<Product> findedProduct = productDAO.findById(productId);

    Assertions.assertThat(updatedRowCount).isEqualTo(1);
    Assertions.assertThat(findedProduct.get().getPname()).isEqualTo(product.getPname());
    Assertions.assertThat(findedProduct.get().getQuantity()).isEqualTo(product.getQuantity());
    Assertions.assertThat(findedProduct.get().getPrice()).isEqualTo(product.getPrice());
  }

  //삭제
  @Test
  @DisplayName("상품삭제")
  void delete() {
    Long productId = 165L;
    int deletedRowCount = productDAO.delete(productId);
    Optional<Product> findedProduct = productDAO.findById(productId);
   // Product product = findedProduct.orElseThrow();
    //case1)
//    Assertions.assertThat(findedProduct.ofNullable("없음").orElseThrow())
//        .isNotEqualTo("없음");

    //case2)
    Assertions.assertThatThrownBy(()->findedProduct.orElseThrow())
        .isInstanceOf(NoSuchElementException.class);
  }

  //목록
  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Product> list = productDAO.findAll();
    //case1)
//    for(Product product : list){
//      log.info("product={}",product);
//    }
    //case2)
//    list.stream().forEach(product ->log.info("product={}",product));

    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("상품존재")
  void isExist(){
    Long prodocutId = 244L;
    boolean exist = productDAO.isExist(prodocutId);
    Assertions.assertThat(exist).isTrue();
  }
  @Test
  @DisplayName("상품무")
  void isExist2(){
    Long prodocutId = 0L;
    boolean exist = productDAO.isExist(prodocutId);
    Assertions.assertThat(exist).isFalse();
  }

  @Test
  @DisplayName("전체 삭제")
  void deleteAll(){
    int deletedRows = productDAO.deleteAll();
    int countOfRecord = productDAO.countOfRecord();
    Assertions.assertThat(countOfRecord).isEqualTo(0);
  }

  @Test
  @DisplayName("레코드 건수")
  void countOfRecord(){
    int countOfRecord = productDAO.countOfRecord();
    log.info("countOfRecord={}",countOfRecord);
  }


  @Test
  @DisplayName("부분삭제")
  void deleteParts(){
    List<Long> productIds = Arrays.asList(326L,327L,325L,341L);
    int rows = productDAO.deleteParts(productIds);
    Assertions.assertThat(rows).isEqualTo(productIds.size());
  }
}
