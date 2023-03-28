package com.kh.app.dao;

import com.kh.app.domain.entity.Product;
import com.kh.app.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDAOImplTest2 {
  @Autowired
  private ProductDAO productDAO;
  private static final int COUNT = 3;
  private static List<Long> productIds = new ArrayList<>();

  @Test
  @DisplayName("등록")
  @Order(1)
  void save() {
    List<Product> products = new ArrayList<>();
    for(int i=1; i<=COUNT; i++) {
      products.add(new Product(null, "노트북"+i, 1L * i, 1000000L * i));
    }
    products.stream().forEach(product -> productIds.add(productDAO.save(product)));
  }
  @Test
  @DisplayName("조회")
  @Order(2)
  void findById() {
    int idx = 0;
    Optional<Product> findedProduct = productDAO.findById(productIds.get(idx));
    Product product = findedProduct.orElseThrow();
    Assertions.assertThat(product.getPname()).isEqualTo("노트북"+(idx+1));
    Assertions.assertThat(product.getQuantity()).isEqualTo(1L * (idx+1));
    Assertions.assertThat(product.getPrice()).isEqualTo(1000000L*(idx+1));
  }
  @Test
  @DisplayName("수정")
  @Order(3)
  void update() {
    int idx = ProductDAOImplTest2.COUNT-1;
    Product product = new Product(null, "노트북_수정", 9L, 9000000L);
    int updatedRowCnt = productDAO.update(productIds.get(idx),product);
    Assertions.assertThat(updatedRowCnt).isEqualTo(1);

    Optional<Product> findedProduct = productDAO.findById(productIds.get(idx));
    Product p = findedProduct.orElseThrow();
    Assertions.assertThat(p.getPname()).isEqualTo("노트북_수정");
    Assertions.assertThat(p.getQuantity()).isEqualTo(9L);
    Assertions.assertThat(p.getPrice()).isEqualTo(9000000L);
  }
  @Test
  @DisplayName("목록")
  @Order(4)
  void findAll() {
    List<Product> list = productDAO.findAll();
    list.stream().forEach(product -> log.info("product={}",product));
    Assertions.assertThat(list.size()).isEqualTo(ProductDAOImplTest2.COUNT);
  }
  @Test
  @DisplayName("삭제")
  @Order(5)
  void deleteByProductId() {
    int idx = 0;
    int deleteRowCnt = productDAO.delete(productIds.get(idx));
    Assertions.assertThat(deleteRowCnt).isEqualTo(1);

    boolean exist = productDAO.isExist(productIds.get(idx));
    Assertions.assertThat(exist).isFalse();

    List<Product> list = productDAO.findAll();
    list.stream().forEach(product -> log.info("product={}",product));
    Assertions.assertThat(list.size()).isEqualTo(ProductDAOImplTest2.COUNT-1);
  }
}
