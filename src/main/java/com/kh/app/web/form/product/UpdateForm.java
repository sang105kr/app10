package com.kh.app.web.form.product;

import com.kh.app.domain.entity.UploadFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateForm {
  private Long productId;
  @NotBlank
  private String pname;
  @NotNull
  private Long quantity;
  @NotNull
  private Long price;

  private MultipartFile attachFile;        // 일반 파일
  private List<MultipartFile> imageFiles;  // 이미지파일

  private UploadFile attachedFile;        // 일반 파일
  private List<UploadFile> imagedFiles;  // 이미지파일
}