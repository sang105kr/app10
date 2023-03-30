package com.kh.app.domain.common.dao;

import com.kh.app.domain.entity.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CodeDAOImpl implements CodeDAO{
  private final NamedParameterJdbcTemplate template;

  /**
   * 하위코드 반환 by 부모코드
   *
   * @param pcodeId 부모코드
   * @return 하위코드
   */
  @Override
  public List<Code> findCodesByPcodeId(String pcodeId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select code_id,decode,detail,pcode_id,useyn ");
    sql.append("  from code ");
    sql.append(" where useyn = 'Y' ");
    sql.append("   and pcode_id = :pcodeId ");

    Map<String, String> param = Map.of("pcodeId",pcodeId);
    return template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Code.class));
  }

  /**
   * 전체코드
   *
   * @return
   */
  @Override
  public List<Code> find() {
    StringBuffer sql = new StringBuffer();
    sql.append("select code_id,decode,detail,pcode_id,useyn ");
    sql.append("  from code ");
    sql.append(" where useyn = 'Y' and pcode_id is not null");

    return template.query(sql.toString(),BeanPropertyRowMapper.newInstance(Code.class));
  }
}
