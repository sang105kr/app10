package com.kh.app.web;

import com.kh.app.domain.common.svc.MultipartFileToUploadFile;
import com.kh.app.domain.common.svc.UploadFileSVC;
import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import com.kh.app.web.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachFileController {

  private final UploadFileSVC uploadFileSVC;
  private final MultipartFileToUploadFile multipartFileToUploadFile;

  //첨부파일 다운로드
  @GetMapping("/down/{fid}")
  public ResponseEntity<Resource> downLoadAttach(
      @PathVariable("fid") Long uploadfileId
  ) throws MalformedURLException {

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(uploadfileId);
    if(uploadFile.isEmpty()) new BizException("첨부파일을 찾을 수 없습니다.");
    log.info("첨부파일 경로={}",getStoreFilePath(uploadFile.get()));

    Resource resource = new UrlResource(getStoreFilePath(uploadFile.get()));
    //한글파일명 깨짐 방지를위한 인코딩
    String encodeUploadFileName = UriUtils.encode(uploadFile.get().getUpload_filename(), StandardCharsets.UTF_8);
    //Http응답 메세지 헤더에 첨부파일이 있음을 알림
    String contentDisposition = "attachment; filename="+ encodeUploadFileName;

    return ResponseEntity.ok()  //응답코드 200
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
  }

  //이미지 뷰
  @GetMapping("/view/{fid}")
  public ResponseEntity<Resource> viewAttach(
      @PathVariable("fid") Long uploadfileId
  ) throws MalformedURLException {

    Optional<UploadFile> uploadFile = uploadFileSVC.findFileByUploadFileId(uploadfileId);
    if(uploadFile.isEmpty()) new BizException("첨부파일을 찾을 수 없습니다.");
    log.info("이미지 경로={}",getStoreFilePath(uploadFile.get()));
    Resource resource = new UrlResource(getStoreFilePath(uploadFile.get()));
//    //한글파일명 깨짐 방지를위한 인코딩
//    String encodeUploadFileName = UriUtils.encode(uploadFile.get().getUpload_filename(), StandardCharsets.UTF_8);
//    //Http응답 메세지 헤더에 첨부파일이 있음을 알림
//    String contentDisposition = "attachment; filename="+ encodeUploadFileName;

    return ResponseEntity.ok()  //응답코드 200
        .body(resource);
  }

  //파일시스템내에 물리적인 경로가져오기
  private String getStoreFilePath(UploadFile metaInofOfuploadFile) {
    StringBuffer storeFilePath = new StringBuffer();
    storeFilePath.append("file:")
        //  d:/attach/분류코드/
        .append(multipartFileToUploadFile.getFullPath(AttachFileType.F010301.valueOf(metaInofOfuploadFile.getCode())))
        //  d:/attach/분류코드/xxx-yyy-xxx-uuu.png
        .append(metaInofOfuploadFile.getStore_filename());
    return storeFilePath.toString();
  }

  //첨부파일 삭제-단건
  @ResponseBody
  @DeleteMapping("/{fid}")
  public RestResponse<Object> deleteFileByFid(
      @PathVariable Long fid) {
    int affectedRow = uploadFileSVC.deleteFileByUploadFildId(fid);

    RestResponse<Object> result = null;
    if(affectedRow == 1){
      result = RestResponse.createRestResponse("00", "성공", null);
    }else{
      result = RestResponse.createRestResponse("99", "fail", null);
    }
    return result;
  }
}
