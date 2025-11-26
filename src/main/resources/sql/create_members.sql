use lp;

create table members
(
    id       int primary key auto_increment comment '아이디',
    name     varchar(50)  not null comment '회원명',
    login_id varchar(50)  not null unique comment '로그인 아이디',
    login_pw varchar(100) not null comment '로그인 패스워드',
    address varchar(100) not null comment '주소',
    created  datetime     not null default current_timestamp() comment '생성 일시'
) comment '회원 테이블';


insert into members (name, login_id, login_pw, address) values ('ssg', 'ssg1234', 'ssg1234', '서울 강남 1111');