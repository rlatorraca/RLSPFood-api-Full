create table tbl_order (
    id bigint not null auto_increment,
    beforetax decimal(10,2) not null,
    deliveryfee decimal(10,2) not null,
    aftertax decimal(10,2) not null,
    taxpercentual decimal(10,2) not null,


    createddate datetime not null,
    confirmationdate datetime null,
    canceleddate datetime null,
    deliverydate datetime null,

    status varchar(15) not null,
    address_city_id bigint not null,
    address_postalcode varchar(9) not null,
    address_street varchar(150) not null,
    address_number varchar(10) not null,
    address_complement varchar(150) not null,
    address_district varchar(100) not null,

    restaurant_id bigint not null,
    user_client_id bigint not null,
    paymenttype_id bigint not null,

    primary key (id),

    constraint fk_address_city foreign key (address_city_id) references tbl_city (id),
    constraint fk_restaurant foreign key (restaurant_id) references tbl_restaurant (id),
    constraint fk_user_client foreign key (user_client_id) references tbl_user (id),
    constraint fk_paymenttype foreign key (paymenttype_id) references tbl_paymenttype (id)

) engine=InnoDB default charset=utf8;