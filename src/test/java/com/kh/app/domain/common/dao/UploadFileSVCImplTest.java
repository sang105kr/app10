package com.kh.app.domain.common.dao;

import com.kh.app.domain.common.file.dao.UploadFileDAO;
import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class UploadFileSVCImplTest {

  @Autowired
  private UploadFileDAO uploadFileDAO;

  @Test
  @DisplayName("단건첨부")
  void addFile() {
    UploadFile uploadFile = new UploadFile();
    uploadFile.setCode("F010301");
    uploadFile.setRid(10L);
    uploadFile.setStore_filename(UUID.randomUUID()+".png");
    uploadFile.setUpload_filename("배경이미지.png");
    uploadFile.setFsize("100");
    uploadFile.setFtype("image/png");
    Long fid = uploadFileDAO.addFile(uploadFile);

    Assertions.assertThat(fid).isGreaterThan(0L);

  }


  @Test
  @DisplayName("여러건첨부")
  void addFiles() {
    List<UploadFile> files = new ArrayList<>();
    for(int i=0; i<5; i++) {
      UploadFile uploadFile = new UploadFile();
      uploadFile.setCode("F010302");
      uploadFile.setRid(10L);
      uploadFile.setStore_filename(UUID.randomUUID() + ".png");
      uploadFile.setUpload_filename("배경이미지"+(i+1)+".png");
      uploadFile.setFsize("100");
      uploadFile.setFtype("image/png");
      files.add(uploadFile);
    }

    uploadFileDAO.addFiles(files);
    Optional<List<UploadFile>> list = uploadFileDAO.findFilesByCodeWithRid(AttachFileType.F010302, 10L);
    Assertions.assertThat(list.get().size()).isEqualTo(5);
  }
}