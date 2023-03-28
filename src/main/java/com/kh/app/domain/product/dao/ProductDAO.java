package com.kh.app.domain.product.dao;

import com.kh.app.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  /**
   * 등록
   * @param product 상품
   * @return 상품아이디
   */
  Long save(Product product);

  /**
   * 조회
   * @param productId 상품아이디
   * @return 상품
   */
  Optional<Product> findById(Long productId);

  /**
   * 수정
   * @param productId 상품아이디
   * @param product 상품
   * @return 수정된 레코드 수
   */
  int update(Long productId,Product product);

  /**
   * 삭제
   * @param productId 상품아이디
   * @return 삭제된 레코드 수
   */
  int delete(Long productId);

  /**
   * 부분삭제
   * @param productIds
   * @return
   */
  int deleteParts(List<Long> productIds);

  /**
   * 전체 삭제
   * @return 삭제한 레코드 건수
   */
  int deleteAll();

  /**
   * 목록
   * @return 상품목록
   */
  List<Product> findAll();

  /**
   * 상품존재유무
   * @param productId 상품아이디
   * @return 
   */
  boolean isExist(Long productId);

  /**
   * 등록된 상품수
   * @return 레코드 건수
   */
  int countOfRecord();
}
