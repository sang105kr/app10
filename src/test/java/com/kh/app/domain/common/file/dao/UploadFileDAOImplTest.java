package com.kh.app.domain.common.file.dao;

import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
class UploadFileDAOImplTest {

  @Autowired
  private UploadFileDAO uploadFileDAO;

  @Test
  void findFilesByCodeWithRid() {
    Optional<List<UploadFile>> filesByCodeWithRid = uploadFileDAO.findFilesByCodeWithRid(AttachFileType.F010301, 1111L);
    Assertions.assertThatThrownBy(()->filesByCodeWithRid.orElseThrow()).isInstanceOf(NoSuchElementException.class);

  }
}