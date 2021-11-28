create table tbl_group (
           id bigint not null auto_increment,
           group_name varchar(100) not null,
           primary key (id)
) engine=InnoDB;


create table tbl_permission (
        id bigint not null auto_increment,
        permission_description varchar(255),
        permission_name varchar(100) not null,
        primary key (id)
) engine=InnoDB;

create table tbl_group_permission (
                                      group_id bigint not null ,
                                      permission_id bigint not null
) engine=InnoDB;

create table tbl_product (
                             id bigint not null auto_increment,
                             product_active bit not null,
                             product_description varchar(255) not null,
                             product_name varchar(120) not null,
                             product_price decimal(19,2) not null,
                             restaurant_id bigint not null,
                             primary key (id)
) engine=InnoDB;


create table tbl_restaurant (
                                id bigint not null auto_increment,
                                address_complement varchar(255),
                                address_district varchar(100),
                                address_number varchar(25),
                                address_postalcode varchar(20),
                                address_street varchar(255),
                                created_date datetime not null,
                                date_last_update datetime not null,
                                delivery_fee decimal(19,2) not null,
                                name_restaurant varchar(100) not null,
                                address_city_id bigint,
                                cuisine_id bigint not null,
                                primary key (id)
) engine=InnoDB;

create table tbl_paymenttype (
                                 id bigint not null auto_increment,
                                 payment_type varchar(100) not null,
                                 primary key (id)
) engine=InnoDB;

create table tbl_restaurant_paymenttype (
                                            restaurant_id bigint not null,
                                            payment_type_id bigint not null
) engine=InnoDB;

create table tbl_user (
                          id bigint not null auto_increment,
                          user_created datetime,
                          user_email varchar(255) not null,
                          user_last_modified datetime not null,
                          user_name varchar(255) not null,
                          user_password varchar(255),
                          primary key (id)
) engine=InnoDB;

create table tbl_user_group (
                                user_id bigint not null,
                                group_id bigint not null
) engine=InnoDB;

alter table tbl_group_permission
    add constraint fk_permission_group
        foreign key (permission_id)
            references tbl_permission (id);

alter table tbl_group_permission
    add constraint fk_group_permission
        foreign key (group_id)
            references tbl_group (id);

alter table tbl_product
    add constraint fk_product_restaurant
        foreign key (restaurant_id)
            references tbl_restaurant (id);

alter table tbl_restaurant
    add constraint fk_restaurant_address_city
        foreign key (address_city_id)
            references tbl_city (id);

alter table tbl_restaurant
    add constraint fk_restaurant_cuisine
        foreign key (cuisine_id)
            references tbl_cuisine (id);

alter table tbl_restaurant_paymenttype
    add constraint fk_restaurant_paymenttype
        foreign key (payment_type_id)
            references tbl_paymenttype (id);

alter table tbl_restaurant_paymenttype
    add constraint fk_paymenttype_restaurant
        foreign key (restaurant_id)
            references tbl_restaurant (id);

alter table tbl_user_group
    add constraint fk_user_group
        foreign key (group_id)
            references tbl_group (id);

alter table tbl_user_group
    add constraint fk_group_user
        foreign key (user_id)
            references tbl_user (id);