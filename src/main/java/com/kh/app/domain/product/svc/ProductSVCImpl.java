package com.kh.app.domain.product.svc;

import com.kh.app.domain.common.dao.UploadFileDAO;
import com.kh.app.domain.entity.Product;
import com.kh.app.domain.entity.UploadFile;
import com.kh.app.domain.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;
  private final UploadFileDAO uploadFileDAO;

  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  @Override
  public Long save(Product product, List<UploadFile> uploadFiles) {
    Long productId = save(product);
    if (uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file->file.setRid(productId));
      uploadFileDAO.addFiles(uploadFiles);
    }

    return productId;
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public int update(Long productId, Product product) {
    return productDAO.update(productId, product);
  }

  @Override
  public int update(Long productId, Product product, List<UploadFile> uploadFiles) {
    productDAO.update(productId, product);
    if (uploadFiles.size() > 0) {
      uploadFiles.stream().forEach(file->file.setRid(productId));
      uploadFileDAO.addFiles(uploadFiles);
    }
    return 0;
  }

  @Override
  public int delete(Long productId) {
    return productDAO.delete(productId);
  }

  @Override
  public int delete(Long productId, String code) {
    int cnt = productDAO.delete(productId);
    uploadFileDAO.deleteFileByCodeWithRid(code,productId);
    return cnt;
  }

  @Override
  public int deleteParts(List<Long> productIds) {
    return productDAO.deleteParts(productIds);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public boolean isExist(Long productId) {
    return productDAO.isExist(productId);
  }
}
