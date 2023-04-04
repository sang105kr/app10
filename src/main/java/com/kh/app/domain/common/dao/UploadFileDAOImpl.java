package com.kh.app.domain.common.dao;

import com.kh.app.domain.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UploadFileDAOImpl implements UploadFileDAO{

  private final NamedParameterJdbcTemplate template;

//  public UploadFileDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  //업로드파일 등록-단건
  @Override
  public Long addFile(UploadFile uploadFile) {

    StringBuffer sql = makeAddFileSql();
    SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"uploadfile_id"});

    return keyHolder.getKey().longValue();
  }

  //업로드파일 등록-여러건
  @Override
  public void addFiles(List<UploadFile> uploadFiles) {

    StringBuffer sql = makeAddFileSql();
    if(uploadFiles.size() == 1){
      addFile(uploadFiles.get(0));
//      SqlParameterSource param = new BeanPropertySqlParameterSource(uploadFiles.get(0));
//      template.update(sql.toString(),param);
    }else {
      SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(uploadFiles);
      //배치 처리 : 여러건의 갱신작업을 한꺼번에 처리하므로 단건처리할때보다 성능이 좋다.
      template.batchUpdate(sql.toString(), params);
    }
  }

  private StringBuffer makeAddFileSql() {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO uploadfile ( ");
    sql.append("  uploadfile_id, ");
    sql.append("  code, ");
    sql.append("  rid, ");
    sql.append("  store_filename, ");
    sql.append("  upload_filename, ");
    sql.append("  fsize, ");
    sql.append("  ftype ");
    sql.append(") VALUES ( ");
    sql.append("  uploadfile_uploadfile_id_seq.nextval, ");
    sql.append("  :code, ");
    sql.append("  :rid, ");
    sql.append("  :store_filename, ");
    sql.append("  :upload_filename, ");
    sql.append("  :fsize, ");
    sql.append("  :ftype ");
    sql.append(") ");
    return sql;
  }

  //조회
  @Override
  public List<UploadFile> findFilesByCodeWithRid(String code, Long rid) {
    StringBuffer sql = new StringBuffer();

    sql.append("SELECT  ");
    sql.append("   uploadfile_id, ");
    sql.append("   code, ");
    sql.append("   rid,  ");
    sql.append("   store_filename, ");
    sql.append("   upload_filename,  ");
    sql.append("   fsize,  ");
    sql.append("   ftype,  ");
    sql.append("   cdate,  ");
    sql.append("   udate ");
    sql.append("  FROM  uploadfile  ");
    sql.append(" WHERE CODE = :code  ");
    sql.append("   AND RID = :rid  ");

    return template.query(
        sql.toString(),
        Map.of("code",code,"rid",rid),
        BeanPropertyRowMapper.newInstance(UploadFile.class));
  }

  //첨부파일 조회
  @Override
  public Optional<UploadFile> findFileByUploadFileId(Long uploadfileId) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select * ");
    sql.append("  from uploadfile ");
    sql.append(" where uploadfile_id = :uploadfile_id ");

    UploadFile uploadFile = null;
    try {
      Map<String, Long> param = Map.of("uploadfile_id", uploadfileId);
      uploadFile = template.queryForObject(sql.toString(),param,BeanPropertyRowMapper.newInstance(UploadFile.class));
      return Optional.of(uploadFile);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  // 첨부파일 삭제 by uplaodfileId
  @Override
  public int deleteFileByUploadFildId(Long uploadfileId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from uploadfile ");
    sql.append(" where uploadfile_id = :uploadfile_id ");

    return template.update(sql.toString(), Map.of("uploadFile_id",uploadfileId));
  }

  // 첨부파일 삭제 by code, rid
  @Override
  public int deleteFileByCodeWithRid(String code, Long rid) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from uploadfile ");
    sql.append(" where code = :code ");
    sql.append("   and rid = :rid ");

    return template.update(sql.toString(),Map.of("code",code,"rid",rid));
  }
}