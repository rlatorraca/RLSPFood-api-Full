create table tbl_orderitem (
    id bigint not null auto_increment,
    quantity smallint(6) not null,
    unitprice decimal(10,2) not null,
    totalprice decimal(10,2) not null,
    comments varchar(255) null,

    order_id bigint not null,
    product_id bigint not null,

    primary key(id),
    unique key uk_item_order_product (order_id, product_id),

    constraint fk_item_order_order foreign key (order_id) references tbl_order (id),
    constraint fk_item_order_product foreign key (product_id) references tbl_product (id)
) engine=InnoDB default charset=utf8;