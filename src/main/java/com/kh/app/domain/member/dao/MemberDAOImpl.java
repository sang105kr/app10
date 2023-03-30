package com.kh.app.domain.member.dao;

import com.kh.app.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO{

  private final NamedParameterJdbcTemplate template;

//  public MemberDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  /**
   * 가입
   * @param member
   * @return
   */
  @Override
  public Member save(Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member ( ");
    sql.append("    member_id, ");
    sql.append("    email, ");
    sql.append("    passwd, ");
    sql.append("    nickname, ");
    sql.append("    gender, ");
    sql.append("    hobby, ");
    sql.append("    region ");
    sql.append(") values( ");
    sql.append("    member_member_id_seq.nextval, ");
    sql.append("    :email, ");
    sql.append("    :passwd, ");
    sql.append("    :nickname, ");
    sql.append("    :gender, ");
    sql.append("    :hobby, ");
    sql.append("    :region ");
    sql.append(") ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    KeyHolder keyHolder = new GeneratedKeyHolder(); //insert된 레코드에서 컬럼값추출
    template.update(sql.toString(),param,keyHolder,new String[]{"member_id"});

    long memberId = keyHolder.getKey().longValue();

    member.setMemberId(memberId);
    return member;
  }

  /**
   * @param memberId
   * @param member
   */
  @Override
  public void update(Long memberId, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("   set nickname = ?, ");
    sql.append("       gender = ?, ");
    sql.append("       hobby = ?, ");
    sql.append("       region = ? ");
    sql.append(" where member_id = ? ");
    sql.append(" where email = ? ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nickname",member.getNickname())
        .addValue("gender",member.getGender())
        .addValue("hobby",member.getHobby())
        .addValue("region",member.getRegion())
        .addValue("member_id",memberId)
        .addValue("email",member.getEmail());

    template.update(sql.toString(),param);
  }

  /**
   * @param email
   * @return
   */
  @Override
  public Optional<Member> findByEmail(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id, ");
    sql.append("       email, ");
    sql.append("       passwd, ");
    sql.append("       nickname, ");
    sql.append("       gender, ");
    sql.append("       hobby, ");
    sql.append("       region ");
    sql.append("  from member ");
    sql.append(" where email = :email ");

    try {
      Map<String, String> param = Map.of("email", email);
      Member member = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Member.class)
      );
      return Optional.of(member);
    } catch (EmptyResultDataAccessException e) {
      //조회결과가 없는 경우
      return Optional.empty();
    }
  }

  /**
   * @param memberId
   * @return
   */
  @Override
  public Optional<Member> findById(Long memberId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id as memberId, ");
    sql.append("       email, ");
    sql.append("       passwd, ");
    sql.append("       nickname, ");
    sql.append("       gender, ");
    sql.append("       hobby, ");
    sql.append("       region ");
    sql.append("  from member ");
    sql.append(" where member_id = :member_id ");

    try {
      Map<String, Long> param = Map.of("member_id", memberId);
      Member member = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Member.class)
      );
      return Optional.of(member);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * @return
   */
  @Override
  public List<Member> findAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select member_id as memberId, ");
    sql.append("       email, ");
    sql.append("       passwd, ");
    sql.append("       nickname, ");
    sql.append("       gender, ");
    sql.append("       hobby, ");
    sql.append("       region ");
    sql.append("  from member ");
    sql.append(" order by member_id desc ");

    List<Member> list = template.query(
        sql.toString(),
        BeanPropertyRowMapper.newInstance(Member.class)
    );

    return list;
  }

  /**
   * @param email
   */
  @Override
  public void delete(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from member ");
    sql.append(" where email = :email ");

    Map<String, String> param = Map.of("email", email);
    template.update(sql.toString(), param);
  }

  /**
   * @param email
   * @return
   */
  @Override
  public boolean isExist(String email) {
    boolean flag = false;
    String sql = "select count(email) from member where email = :email ";

    Map<String, String> param = Map.of("email", email);

    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  /**
   * @param email
   * @param passwd
   * @return
   */
  @Override
  public Optional<Member> login(String email, String passwd) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id, email, nickname, gubun ");
    sql.append("  from member ");
    sql.append(" where email = :email and passwd = :passwd ");

    Map<String, String> param = Map.of("email", passwd,"passwd",passwd);
    // 레코드1개를 반환할경우 query로 list를 반환받고 list.size() == 1 ? list.get(0) : null 처리하자!!
    List<Member> list = template.query(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Member.class) //자바객체 <=> 테이블 레코드 자동 매핑
    );

    return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
  }

  /**
   * @param nickname
   * @return
   */
  @Override
  public Optional<String> findEmailByNickname(String nickname) {
    StringBuffer sql = new StringBuffer();
    sql.append("select email ");
    sql.append("  from member ");
    sql.append(" where nickname = :nackname ");

    Map<String, String> param = Map.of("nackname", nickname);
    List<String> result = template.query(
        sql.toString(),
        param,
        (rs, rowNum)->rs.getNString("email")
    );

    return (result.size() == 1) ? Optional.of(result.get(0)) : Optional.empty();
  }

}
