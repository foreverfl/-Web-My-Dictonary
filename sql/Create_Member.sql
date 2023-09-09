create table member (
   member_id bigint not null auto_increment,
   email varchar(255),
   name varchar(255),
   is_premium bit not null,
   nickname varchar(255),
   photo varchar(255),
   pw varchar(255),
   primary key (member_id)
);

