create table tbl_product_photo (
    product_id bigint not null,
    file_name varchar(200) not null,
    description varchar(150) not null,
    content_type varchar(80) not null,
    size int not null,

    primary key (product_id),
    constraint fk_product_photo_product foreign key (product_id) references tbl_product(id)
) engine=innoDB default charset=utf8