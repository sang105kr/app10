package com.kh.app.domain.common.svc;

import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class MultipartFileToUploadFile {

  @Value("${attach.root_dir}") // d:/attach/
  private String ROOT_DIR;

  //물리 파일 저장 경로
  //  d:/attach/분류코드/
  public String getFullPath(AttachFileType attachFileType) {
    StringBuffer path = new StringBuffer();
    path = path.append(ROOT_DIR).append(attachFileType.name()).append("/"); // d:/attach/분류코드/

    //경로가 없으면 생성성
    createFolder(path.toString());
    return path.toString();
  }

  //분류코드 폴더 생성
  private void createFolder(String path) {
    File folder = new File(path);
    if(!folder.exists()) folder.mkdir();
  }

  public UploadFile convert(MultipartFile mf, AttachFileType attachFileType){
    if(mf.isEmpty()) return null;
    UploadFile uploadFile = new UploadFile();
    uploadFile.setCode(attachFileType.name()); //"F010301"
//    uploadFile.setRid(rid);
    uploadFile.setUpload_filename(mf.getOriginalFilename());

    String storeFilename = createStoreFilename(mf.getOriginalFilename());
    uploadFile.setStore_filename(storeFilename);

    uploadFile.setFsize(String.valueOf(mf.getSize()));
    uploadFile.setFtype(mf.getContentType());

    //물리 파일 저장
    storeFile(mf,attachFileType,storeFilename);

    return uploadFile;
  }

  private void storeFile(MultipartFile mf, AttachFileType attachFileType,String storeFilename) {
    String fullPath = getFullPath(attachFileType);
    File file = new File(fullPath+storeFilename);

    try {
      mf.transferTo(file);  // 디렉토리에 저장
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
        .append(extractExt(originalFile)); // png,jpg
    return storeFileName.toString();
  }

  //확장자 추출
  private String extractExt(String originalFile) {
    int posOfExt =originalFile.lastIndexOf(".");
    String ext = originalFile.substring(posOfExt + 1);
    return ext;
  }
}