create table was_db.mboard(
  num INT UNSIGNED NOT NULL AUTO_INCREMENT,
  writer varchar(30) not null,
  title varchar(60) not null,
  passwd varchar(12) not null,
  content text not null,
  reg_date datetime not null,
   primary key (num)
);