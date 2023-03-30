package com.kh.app.domain.common.util;

import com.kh.app.domain.common.dao.CodeDAO;
import com.kh.app.domain.entity.Code;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class CommonCode {

  private final CodeDAO codeDAO;
  private List<Code> codes;

  public List<Code> findCodesByPcodeId(String pcodeId){
    List<Code> collect = codes.stream()
        .filter(code -> code.getPcodeId().equals(pcodeId))
        .collect(Collectors.toList());
    return collect;
  }

  public void find(){
    codes = codeDAO.find();
    log.info("codes={}",codes);
  }

  @PostConstruct //생성자 호출후 수행되는 메소드
  public void init(){
    find();
  }
}
