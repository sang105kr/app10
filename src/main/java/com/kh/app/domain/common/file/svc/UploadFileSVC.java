package com.kh.app.domain.common.file.svc;

import com.kh.app.domain.entity.UploadFile;
import com.kh.app.web.common.AttachFileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UploadFileSVC {
  /**
   * 업로드 파일 등록 - 단건
   * @param uploadFile
   * @return 파일Id
   */
  Long addFile(UploadFile uploadFile);

  /**
   * 업로드 파일 등록 - 여러건
   * @param uploadFiles
   */
  void addFiles(List<UploadFile> uploadFiles);

  /**
   * 업로드파일조회
   * @param attachFileType
   * @param rid
   * @return
   */
  List<UploadFile> findFilesByCodeWithRid(AttachFileType attachFileType, Long rid);

  /**
   * 첨부파일조회
   * @param uploadfileId
   * @return
   */
  Optional<UploadFile> findFileByUploadFileId(Long uploadfileId);


  /**
   * 첨부파일 삭제 by uplaodfileId
   * @param uploadfileId 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByUploadFildId(Long uploadfileId);

  /**
   * 첨부파일 삭제 By code, rid
   * @param attachFileType 첨부파일 분류코드
   * @param rid 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByCodeWithRid(AttachFileType attachFileType, Long rid);

  /**
   *  물리 파일 저장 경로
   * @param attachFileType
   * @return d:/attach/분류코드/
   */
  public String getFullPath(AttachFileType attachFileType);

  /**
   * 물리 저장 파일
   * @param attachFileType
   * @param storeFilename
   * @return d:/attach/분류코드/xxx-xxx-xxx-xxx-xxx.png
   */
  public String getStoreFilename(AttachFileType attachFileType,String storeFilename);

  public UploadFile convert(MultipartFile mf, AttachFileType attachFileType);

  public List<UploadFile> convert(List<MultipartFile> mfs,AttachFileType attachFileType);

  /**
   * 물리파일 삭제-단건
   * @param attachFileType
   * @param sfname
   * @return
   */
  public boolean deleteFile(AttachFileType attachFileType ,String sfname);

  /**
   * 물리파일 삭제-여러건
   * @param attachFileType
   * @param sfnames
   * @return
   */
  public boolean deleteFiles(AttachFileType attachFileType, List<String> sfnames );
}
