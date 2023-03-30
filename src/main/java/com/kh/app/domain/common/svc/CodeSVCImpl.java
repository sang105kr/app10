package com.kh.app.domain.common.svc;

import com.kh.app.domain.common.dao.CodeDAO;
import com.kh.app.domain.entity.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeSVCImpl implements CodeSVC{
  private final CodeDAO codeDAO;

  /**
   * 하위코드 반환 by 부모코드
   *
   * @param pcodeId 부모코드
   * @return 하위코드
   */
  @Override
  public List<Code> findCodeByPcodeId(String pcodeId) {

    return codeDAO.findCodesByPcodeId(pcodeId);
  }
}
