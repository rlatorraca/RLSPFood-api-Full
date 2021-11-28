create table tbl_cuisine(
    id bigint not null auto_increment,
    cuisine_name varchar(120) not null,

    primary key (id)
) engine=innoDB default charset=utf8;