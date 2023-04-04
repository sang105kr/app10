package com.kh.app.domain.common.svc;

import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class MultipartFileToUploadFile {

  public UploadFile convert(MultipartFile mf, AttachFileType attachFileType){
    if(mf.isEmpty()) return null;
    UploadFile uploadFile = new UploadFile();
    uploadFile.setCode(attachFileType.name()); //"F010301"
//    uploadFile.setRid(rid);
    uploadFile.setUpload_filename(mf.getOriginalFilename());
    uploadFile.setStore_filename(createStoreFilename(mf.getOriginalFilename()));
    uploadFile.setFsize(String.valueOf(mf.getSize()));
    uploadFile.setFtype(mf.getContentType());
    return uploadFile;
  }

  public List<UploadFile> convert(List<MultipartFile> mfs,AttachFileType attachFileType) {
    if(mfs.size() < 1) return null;
    List<UploadFile> uploadFiles = new ArrayList<>();
    for (MultipartFile mf : mfs) {
      if(mf.isEmpty()) continue;
      uploadFiles.add(convert(mf,attachFileType));
    }
    return uploadFiles;
  }

  //임의파일명 생성
  private String createStoreFilename(String originalFile) {
    StringBuffer storeFileName = new StringBuffer();
    storeFileName.append(UUID.randomUUID().toString()) // xxx-yyy-zzz-ttt..
        .append(".")
        .append(extractExt(originalFile)); // xxx-yyy-zzz-ttt..
    return storeFileName.toString();
  }

  //확장자 추출
  private String extractExt(String originalFile) {
    int posOfExt =originalFile.lastIndexOf(".");
    String ext = originalFile.substring(posOfExt + 1);
    return ext;
  }
}