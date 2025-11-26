use lp;

create table items (
  id int primary key auto_increment,
  name varchar(50) not null,
    img_path varchar(50) not null,
    price int not null,
    discount_per int not null,
    comment text not null,
    created datetime not null default current_timestamp()
);

INSERT INTO items (name, img_path, price, discount_per, comment)
VALUES ('Iu 꽃갈피 LP', '/img/001.jpg', 300000, 10, '아이유의 리메이크 앨범입니다. \n80-90년대 명곡을 아이유의 색깔로 재해석한 앨범으로 아이유만의 감성을 느낄 수 있습니다.')
     , ('백예린 Every letter I sent you', '/img/002.jpg', 350000, 10, '재즈감성을 느낄 수 있는 백예린의 LP입니다.\n클리어한 LP판으로 소장하고 싶었으나 급전이 필요해서 팝니다.')
     , ('잔나비 2집 전설 LP', '/img/003.jpg', 600000, 5, '잔나비 2집 LP입니다. 소장 가치가 높은 LP입니다.')
     , ('로제 Rosie LP', '/img/004.jpg', 50000, 3, '로제의 정규 1집 ROSIE의 LP입니다.')
     , ('Oassis 정규 2집 LP', '/img/005.jpg', 60000, 10, 'Dont look back in Anger, Wonderwall과 같은 명곡들이 수록된 LP입니다.')
     , ('지브리 하울의 움직이는 성 LP', '/img/006.jpg', 60000, 5, '지브리 하울의 움직이는 성 OST를 담은 LP입니다.')
     , ('MJ Thriller LP', '/img/006.jpg', 60000, 5, '마이클 잭슨의 Thriller 앨범 LP입니다.');