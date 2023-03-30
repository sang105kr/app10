--테이블 삭제
drop table uploadfile;
drop table bbs;
drop table member;
drop table notice;
drop table code;
drop table product;

--시퀀스삭제
drop sequence member_member_id_seq;
drop sequence notice_notice_id_seq;
drop sequence bbs_bbs_id_seq;
drop sequence uploadfile_uploadfile_id_seq;
drop sequence product_product_id_seq;

-------
--코드
-------
create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    detail      clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
alter table code add constraint bbs_pcode_id_fk
    foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--샘플데이터 of code
insert into code (code_id,decode,pcode_id,useyn) values ('A01','취미',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0101','수영','A01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0102','등산','A01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0103','골프','A01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0104','영화','A01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('A02','지역',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0201','서울','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0202','부산','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0203','대국','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0204','울산','A02','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0101','Spring','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0102','Datbase','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0103','Q_A','B01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('M01','회원구분',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0101','일반','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0102','우수','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A1','관리자1','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A2','관리자2','M01','Y');
commit;

-------
--회원
-------
create table member (
    member_id   number,         --내부 관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    gender      varchar2(6),    --성별
    hobby       varchar2(300),  --취미
    region      varchar2(30),   --지역
    gubun       varchar2(11)   default 'M0101', --회원구분 (일반,우수,관리자..)
    pic         blob,            --사진
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--제약조건
alter table member add constraint member_gubun_fk
    foreign key(gubun) references code(code_id);
alter table member modify email constraint member_passwd_uk unique;
alter table member modify email constraint member_passwd_nn not null;
alter table member add constraint member_gender_ck check (gender in ('남자','여자'));

--시퀀스
create sequence member_member_id_seq;
desc member;

insert into member (member_id,email,passwd,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test1@kh.com', '1234', '테스터1','남자','골프,독서','울산', 'M0101');
insert into member (member_id,email,passwd,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test2@kh.com', '1234', '테스터2','여자','골프,수영','부산', 'M0102');
insert into member (member_id,email,passwd,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','관리자1', '남자','등산,독서','서울','M01A1');
insert into member (member_id,email,passwd,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','관리자2', '여자','골프,독서','대구','M01A2');
select * from member;
commit;

---------
--공지사항
---------
create table notice(
    notice_id    number(8),
    subject     varchar2(100),
    content     clob,
    author      varchar2(12),
    hit         number(5) default 0,
    cdate       timestamp default systimestamp,
    udate       timestamp default systimestamp
);
--기본키생성
alter table notice add Constraint notice_notice_id_pk primary key (notice_id);

--제약조건 not null
alter table notice modify subject constraint notice_subject_nn not null;
alter table notice modify content constraint notice_content_nn not null;
alter table notice modify author constraint notice_author_nn not null;

--시퀀스
create sequence notice_notice_id_seq
start with 1
increment by 1
minvalue 0
maxvalue 99999999
nocycle;

-------
--게시판
-------
create table bbs(
    bbs_id      number(10),         --게시글 번호
    bcategory   varchar2(11),       --분류카테고리
    title       varchar2(150),      --제목
    email       varchar2(50),       --email
    nickname    varchar2(30),       --별칭
    hit         number(5) default 0,          --조회수
    bcontent    clob,               --본문
    pbbs_id     number(10),         --부모 게시글번호
    bgroup      number(10),         --답글그룹
    step        number(3) default 0,          --답글단계
    bindent     number(3) default 0,          --답글들여쓰기
    status      char(1),               --답글상태  (삭제: 'D', 임시저장: 'I')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);

--기본키
alter table bbs add Constraint bbs_bbs_id_pk primary key (bbs_id);

--외래키
alter table bbs add constraint bbs_bcategory_fk
    foreign key(bcategory) references code(code_id);
alter table bbs add constraint bbs_pbbs_id_fk
    foreign key(pbbs_id) references bbs(bbs_id);
alter table bbs add constraint bbs_email_fk
    foreign key(email) references member(email);

--제약조건
alter table bbs modify bcategory constraint bbs_bcategory_nn not null;
alter table bbs modify title constraint bbs_title_nn not null;
alter table bbs modify email constraint bbs_email_nn not null;
alter table bbs modify nickname constraint bbs_nickname_nn not null;
alter table bbs modify bcontent constraint bbs_bcontent_nn not null;

--시퀀스
create sequence bbs_bbs_id_seq;

---------
--첨부파일
---------
create table uploadfile(
    uploadfile_id   number(10),     --파일아이디
    code            varchar2(11),   --분류코드
    rid             varchar2(10),     --참조번호(게시글번호등)
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),   --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk
    foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;

--시퀀스
create sequence uploadfile_uploadfile_id_seq;

--상품관리
create table product(
    product_id  number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
create sequence product_product_id_seq;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);

--조회--
select product_id, pname, quantity, price
  from product
 where product_id = 2;

--수정--
update product
   set pname = '컴퓨터2',
       quantity = 10,
       price = 1200000;

--삭제
delete from product where product_id = 1;

--전체조회-
select product_id,pname,quantity,price from product;

commit;