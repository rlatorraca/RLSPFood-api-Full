create table tbl_city(
     id bigint not null auto_increment,
     city_name varchar(120) not null,
     province_name varchar(120) not null,

     primary key (id)
) engine=innoDB default charset=utf8;