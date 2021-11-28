create table tbl_taxprovince(
        id bigint not null auto_increment,
        tax_percentual decimal(5,5) not null,

        primary key (id)
) engine=innoDB default charset=utf8;

create table tbl_province(
     id bigint not null auto_increment,
     province_name varchar(100) not null,
     taxprovince_id bigint not null,

     primary key (id)
) engine=innoDB default charset=utf8;
alter table tbl_province add constraint fk_province_taxprovince foreign key (taxprovince_id) references tbl_taxprovince(id);