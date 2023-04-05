package com.kh.app.domain.common.svc;

import com.kh.app.domain.common.dao.UploadFileDAO;
import com.kh.app.domain.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadFileSVCImpl implements UploadFileSVC{

  private final UploadFileDAO uploadFileDAO;

  @Override
  public Long addFile(UploadFile uploadFile) {
    return uploadFileDAO.addFile(uploadFile);
  }

  @Override
  public void addFiles(List<UploadFile> uploadFiles) {
    uploadFileDAO.addFiles(uploadFiles);
  }

  @Override
  public List<UploadFile> findFilesByCodeWithRid(String code, Long rid) {
    return uploadFileDAO.findFilesByCodeWithRid(code, rid);
  }

  @Override
  public Optional<UploadFile> findFileByUploadFileId(Long uploadfileId) {
    return uploadFileDAO.findFileByUploadFileId(uploadfileId);
  }

  @Override
  public int deleteFileByUploadFildId(Long uploadfileId) {
    return uploadFileDAO.deleteFileByUploadFildId(uploadfileId);
  }

  @Override
  public int deleteFileByCodeWithRid(String code, Long rid) {
    return uploadFileDAO.deleteFileByCodeWithRid(code, rid);
  }
}
