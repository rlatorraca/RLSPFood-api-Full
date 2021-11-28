

    create table tbl_group (
       id bigint not null auto_increment,
        group_name varchar(100) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_group_permission (
       group_id bigint not null,
        permission_id bigint not null
    ) engine=InnoDB

    create table tbl_paymenttype (
       id bigint not null auto_increment,
        payment_type varchar(100) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_permission (
       id bigint not null auto_increment,
        permission_description varchar(255),
        permission_name varchar(100) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_product (
       id bigint not null auto_increment,
        product_active bit not null,
        product_description varchar(255) not null,
        product_name varchar(120) not null,
        product_price decimal(19,2) not null,
        restaurant_id bigint not null,
        primary key (id)
    ) engine=InnoDB


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
    ) engine=InnoDB

    create table tbl_restaurant_paymenttype (
       restaurant_id bigint not null,
        payment_type_id bigint not null
    ) engine=InnoDB

    create table tbl_user (
       id bigint not null auto_increment,
        created_user datetime,
        user_email varchar(255) not null,
        last_modified_user datetime not null,
        user_name varchar(255) not null,
        user_password varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table tbl_user_group (
       user_id bigint not null,
        groups_id bigint not null
    ) engine=InnoDB

    alter table tbl_group_permission
       add constraint fk_permission_group
       foreign key (permission_id) 
       references tbl_permission (id)

    alter table tbl_group_permission 
       add constraint fk_group_persmission
       foreign key (group_id) 
       references tbl_group (id)

    alter table tbl_product 
       add constraint fk_product_restaurant
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_restaurant 
       add constraint fk_restaurant_address_city
       foreign key (address_city_id) 
       references tbl_city (id)

    alter table tbl_restaurant 
       add constraint fk_restaurant_cuisine
       foreign key (cuisine_id) 
       references tbl_cuisine (id)

    alter table tbl_restaurant_paymenttype 
       add constraint fk_restaurant_paymenttype
       foreign key (payment_type_id) 
       references tbl_paymenttype (id)

    alter table tbl_restaurant_paymenttype 
       add constraint fk_paymenttype_restaurant
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_user_group 
       add constraint fk_user_group
       foreign key (groups_id) 
       references tbl_group (id)

    alter table tbl_user_group 
       add constraint fk_group_user
       foreign key (user_id) 
       references tbl_user (id)
insert into tbl_cuisine (id, cuisine_name) values (1, 'Fusion')
insert into tbl_cuisine (id, cuisine_name) values (2, 'Haule')
insert into tbl_cuisine (id, cuisine_name) values (3, 'Nouvelle')
insert into tbl_cuisine (id, cuisine_name) values (4, 'Vegan')
insert into tbl_cuisine (id, cuisine_name) values (5, 'Vegetarian')
insert into tbl_cuisine (id, cuisine_name) values (6, 'Argentina')
insert into tbl_cuisine (id, cuisine_name) values (7, 'Spanish')
insert into tbl_cuisine (id, cuisine_name) values (8, 'Brazilian')
insert into tbl_paymenttype (payment_type) values ('Cash')
insert into tbl_paymenttype  (payment_type) values ('Credit card')
insert into tbl_paymenttype  (payment_type) values ('Debit card')
insert into tbl_paymenttype (payment_type) values ('Pay Pal')
insert into tbl_paymenttype  (payment_type) values ('Bitcoin')
insert into tbl_paymenttype  (payment_type) values ('Etherium')

insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update ) values ('Mirazur', 15.60, 1, 'A1BC2D', 'Street A', '1', 'suite 111', 'downtown', 1, utc_timestamp(4), utc_timestamp(4))
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Noma', 34.10, 1, 'B1BC2D', 'Street B', '2', 'suite 222', 'Bay B', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Asador Etxebarri', 42.20, 2, 'C1BC2D', 'Street C', '3', 'suite 333', 'Bay C', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Gaggan', 35.00, 2, 'D1BC2D', 'Street D', '4', 'suite 444', 'downtown', 4, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Garanium', 45.67, 3, 'E1BC2D', 'Street E', '5', 'suite 555', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Central', 56.90, 1, 'F1BC2D', 'Street F', '6', 'suite 777', 'Water A', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Mugaritx', 64.00, 1, 'G1BC2D', 'Street G', '7', 'suite 888', 'downtown', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Arpège', 74.50, 1, 'H1BC2D', 'Street H', '8', 'suite 999', 'Qatar Beach', 5, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Disfrutar', 51.00, 2, 'I1BC2D', 'Street I', '9', 'suite 1010', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Maido', 40.00, 1, 'J1BC2D', 'Street J', '10', 'suite 1222', 'downtown', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (4, 1), (4, 2), (4, 3), (4, 5), (5, 2), (5, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (6, 1), (6, 2), (7, 3), (7, 4), (7, 5), (8, 1)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (8, 2), (8, 3), (8, 4), (8, 5), (8, 6)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (9, 1), (9, 2), (9, 3), (9, 4), (9, 5)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6)

    create table tbl_city (
       id bigint not null auto_increment,
        city_name varchar(255) not null,
        province_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_cuisine (
       id bigint not null auto_increment,
        cuisine_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_group (
       id bigint not null auto_increment,
        group_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_group_permission (
       group_id bigint not null,
        permission_id bigint not null
    ) engine=InnoDB

    create table tbl_paymenttype (
       id bigint not null auto_increment,
        payment_type varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_permission (
       id bigint not null auto_increment,
        permission_description varchar(255),
        permission_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_product (
       id bigint not null auto_increment,
        product_active bit not null,
        product_description varchar(255) not null,
        product_name varchar(255) not null,
        product_price decimal(19,2) not null,
        restaurant_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_province (
       id bigint not null auto_increment,
        province_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_restaurant (
       id bigint not null auto_increment,
        address_complement varchar(255),
        address_district varchar(255),
        address_number varchar(255),
        address_postalcode varchar(255),
        address_street varchar(255),
        created_date datetime not null,
        date_last_update datetime not null,
        delivery_fee decimal(19,2) not null,
        name_restaurant varchar(100) not null,
        address_city_id bigint,
        cuisine_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_restaurant_paymenttype (
       restaurant_id bigint not null,
        payment_type_id bigint not null
    ) engine=InnoDB

    create table tbl_user (
       id bigint not null auto_increment,
        created_user datetime,
        user_email varchar(255) not null,
        last_modified_user datetime not null,
        user_name varchar(255) not null,
        user_password varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table tbl_user_group (
       user_id bigint not null,
        groups_id bigint not null
    ) engine=InnoDB

    alter table tbl_city 
       add constraint FKgwql0duanim53l63pnnalpd5v 
       foreign key (province_id) 
       references tbl_province (id)

    alter table tbl_group_permission 
       add constraint FKc7xf7prj59lk6kwkk6obdqerg 
       foreign key (permission_id) 
       references tbl_permission (id)

    alter table tbl_group_permission 
       add constraint FKc2trr457n4jt6sfyg1pjuoy56 
       foreign key (group_id) 
       references tbl_group (id)

    alter table tbl_product 
       add constraint FK1irloauhg9dk9jpneluuii8m1 
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_restaurant 
       add constraint FK46frfnjha5oap1inuthd9t1ov 
       foreign key (address_city_id) 
       references tbl_city (id)

    alter table tbl_restaurant 
       add constraint FK1824cmvbcy32b10cwfl16dn7l 
       foreign key (cuisine_id) 
       references tbl_cuisine (id)

    alter table tbl_restaurant_paymenttype 
       add constraint FKo0yevbhbs3frubhkc72so4mfo 
       foreign key (payment_type_id) 
       references tbl_paymenttype (id)

    alter table tbl_restaurant_paymenttype 
       add constraint FK2ymep6cdceqg6vhceqfikuvd3 
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_user_group 
       add constraint FKn7xmib4krnr5wsmp9xhnujs8t 
       foreign key (groups_id) 
       references tbl_group (id)

    alter table tbl_user_group 
       add constraint FKtbwc0qulooue7m7gbe2d0ad54 
       foreign key (user_id) 
       references tbl_user (id)
insert into tbl_cuisine (id, cuisine_name) values (1, 'Fusion')
insert into tbl_cuisine (id, cuisine_name) values (2, 'Haule')
insert into tbl_cuisine (id, cuisine_name) values (3, 'Nouvelle')
insert into tbl_cuisine (id, cuisine_name) values (4, 'Vegan')
insert into tbl_cuisine (id, cuisine_name) values (5, 'Vegetarian')
insert into tbl_cuisine (id, cuisine_name) values (6, 'Argentina')
insert into tbl_cuisine (id, cuisine_name) values (7, 'Spanish')
insert into tbl_cuisine (id, cuisine_name) values (8, 'Brazilian')
insert into tbl_paymenttype (payment_type) values ('Cash')
insert into tbl_paymenttype  (payment_type) values ('Credit card')
insert into tbl_paymenttype  (payment_type) values ('Debit card')
insert into tbl_paymenttype (payment_type) values ('Pay Pal')
insert into tbl_paymenttype  (payment_type) values ('Bitcoin')
insert into tbl_paymenttype  (payment_type) values ('Etherium')
insert into tbl_province (province_name) values ('Alberta')
insert into tbl_province (province_name) values ('British Columbia')
insert into tbl_province (province_name) values ('Manitoba')
insert into tbl_province (province_name) values ('New Brunswick')
insert into tbl_province (province_name) values ('Newfoundland and Labrador')
insert into tbl_province (province_name) values ('Northwest Territories.')
insert into tbl_province (province_name) values ('Nova Scotia')
insert into tbl_province (province_name) values ('Nunavut')
insert into tbl_province (province_name) values ('Ontario')
insert into tbl_province (province_name) values ('Quebec')
insert into tbl_province (province_name) values ('Prince Edward Island')
insert into tbl_province (province_name) values ('Saskatchewan')
insert into tbl_province (province_name) values ('Yukon')
insert into tbl_city(id, city_name, province_id) values (1, 'Ottawa', 9)
insert into tbl_city(id, city_name, province_id) values (2, 'Toronto', 9)
insert into tbl_city(id, city_name, province_id) values (3, 'Montreal', 10)
insert into tbl_city(id, city_name, province_id) values (4, 'Ville du Quebec', 10)
insert into tbl_city(id, city_name, province_id) values (5, 'Vancouver', 2)
insert into tbl_city(id, city_name, province_id) values (6, 'Calgary', 1)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update ) values ('Mirazur', 15.60, 1, 'A1BC2D', 'Street A', '1', 'suite 111', 'downtown', 1, utc_timestamp(4), utc_timestamp(4))
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Noma', 34.10, 1, 'B1BC2D', 'Street B', '2', 'suite 222', 'Bay B', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Asador Etxebarri', 42.20, 2, 'C1BC2D', 'Street C', '3', 'suite 333', 'Bay C', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Gaggan', 35.00, 2, 'D1BC2D', 'Street D', '4', 'suite 444', 'downtown', 4, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Garanium', 45.67, 3, 'E1BC2D', 'Street E', '5', 'suite 555', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Central', 56.90, 1, 'F1BC2D', 'Street F', '6', 'suite 777', 'Water A', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Mugaritx', 64.00, 1, 'G1BC2D', 'Street G', '7', 'suite 888', 'downtown', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Arpège', 74.50, 1, 'H1BC2D', 'Street H', '8', 'suite 999', 'Qatar Beach', 5, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Disfrutar', 51.00, 2, 'I1BC2D', 'Street I', '9', 'suite 1010', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Maido', 40.00, 1, 'J1BC2D', 'Street J', '10', 'suite 1222', 'downtown', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (4, 1), (4, 2), (4, 3), (4, 5), (5, 2), (5, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (6, 1), (6, 2), (7, 3), (7, 4), (7, 5), (8, 1)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (8, 2), (8, 3), (8, 4), (8, 5), (8, 6)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (9, 1), (9, 2), (9, 3), (9, 4), (9, 5)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6)

    create table tbl_city (
       id bigint not null auto_increment,
        city_name varchar(255) not null,
        province_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_cuisine (
       id bigint not null auto_increment,
        cuisine_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_group (
       id bigint not null auto_increment,
        group_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_group_permission (
       group_id bigint not null,
        permission_id bigint not null
    ) engine=InnoDB

    create table tbl_paymenttype (
       id bigint not null auto_increment,
        payment_type varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_permission (
       id bigint not null auto_increment,
        permission_description varchar(255),
        permission_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_product (
       id bigint not null auto_increment,
        product_active bit not null,
        product_description varchar(255) not null,
        product_name varchar(255) not null,
        product_price decimal(19,2) not null,
        restaurant_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_province (
       id bigint not null auto_increment,
        province_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_restaurant (
       id bigint not null auto_increment,
        address_complement varchar(255),
        address_district varchar(255),
        address_number varchar(255),
        address_postalcode varchar(255),
        address_street varchar(255),
        created_date datetime not null,
        date_last_update datetime not null,
        delivery_fee decimal(19,2) not null,
        name_restaurant varchar(100) not null,
        address_city_id bigint,
        cuisine_id bigint not null,
        primary key (id)
    ) engine=InnoDB

    create table tbl_restaurant_paymenttype (
       restaurant_id bigint not null,
        payment_type_id bigint not null
    ) engine=InnoDB

    create table tbl_user (
       id bigint not null auto_increment,
        created_user datetime,
        user_email varchar(255) not null,
        last_modified_user datetime not null,
        user_name varchar(255) not null,
        user_password varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table tbl_user_group (
       user_id bigint not null,
        groups_id bigint not null
    ) engine=InnoDB

    alter table tbl_city 
       add constraint FKgwql0duanim53l63pnnalpd5v 
       foreign key (province_id) 
       references tbl_province (id)

    alter table tbl_group_permission 
       add constraint FKc7xf7prj59lk6kwkk6obdqerg 
       foreign key (permission_id) 
       references tbl_permission (id)

    alter table tbl_group_permission 
       add constraint FKc2trr457n4jt6sfyg1pjuoy56 
       foreign key (group_id) 
       references tbl_group (id)

    alter table tbl_product 
       add constraint FK1irloauhg9dk9jpneluuii8m1 
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_restaurant 
       add constraint FK46frfnjha5oap1inuthd9t1ov 
       foreign key (address_city_id) 
       references tbl_city (id)

    alter table tbl_restaurant 
       add constraint FK1824cmvbcy32b10cwfl16dn7l 
       foreign key (cuisine_id) 
       references tbl_cuisine (id)

    alter table tbl_restaurant_paymenttype 
       add constraint FKo0yevbhbs3frubhkc72so4mfo 
       foreign key (payment_type_id) 
       references tbl_paymenttype (id)

    alter table tbl_restaurant_paymenttype 
       add constraint FK2ymep6cdceqg6vhceqfikuvd3 
       foreign key (restaurant_id) 
       references tbl_restaurant (id)

    alter table tbl_user_group 
       add constraint FKn7xmib4krnr5wsmp9xhnujs8t 
       foreign key (groups_id) 
       references tbl_group (id)

    alter table tbl_user_group 
       add constraint FKtbwc0qulooue7m7gbe2d0ad54 
       foreign key (user_id) 
       references tbl_user (id)
insert into tbl_cuisine (id, cuisine_name) values (1, 'Fusion')
insert into tbl_cuisine (id, cuisine_name) values (2, 'Haule')
insert into tbl_cuisine (id, cuisine_name) values (3, 'Nouvelle')
insert into tbl_cuisine (id, cuisine_name) values (4, 'Vegan')
insert into tbl_cuisine (id, cuisine_name) values (5, 'Vegetarian')
insert into tbl_cuisine (id, cuisine_name) values (6, 'Argentina')
insert into tbl_cuisine (id, cuisine_name) values (7, 'Spanish')
insert into tbl_cuisine (id, cuisine_name) values (8, 'Brazilian')
insert into tbl_paymenttype (payment_type) values ('Cash')
insert into tbl_paymenttype  (payment_type) values ('Credit card')
insert into tbl_paymenttype  (payment_type) values ('Debit card')
insert into tbl_paymenttype (payment_type) values ('Pay Pal')
insert into tbl_paymenttype  (payment_type) values ('Bitcoin')
insert into tbl_paymenttype  (payment_type) values ('Etherium')
insert into tbl_province (province_name) values ('Alberta')
insert into tbl_province (province_name) values ('British Columbia')
insert into tbl_province (province_name) values ('Manitoba')
insert into tbl_province (province_name) values ('New Brunswick')
insert into tbl_province (province_name) values ('Newfoundland and Labrador')
insert into tbl_province (province_name) values ('Northwest Territories.')
insert into tbl_province (province_name) values ('Nova Scotia')
insert into tbl_province (province_name) values ('Nunavut')
insert into tbl_province (province_name) values ('Ontario')
insert into tbl_province (province_name) values ('Quebec')
insert into tbl_province (province_name) values ('Prince Edward Island')
insert into tbl_province (province_name) values ('Saskatchewan')
insert into tbl_province (province_name) values ('Yukon')
insert into tbl_city(id, city_name, province_id) values (1, 'Ottawa', 9)
insert into tbl_city(id, city_name, province_id) values (2, 'Toronto', 9)
insert into tbl_city(id, city_name, province_id) values (3, 'Montreal', 10)
insert into tbl_city(id, city_name, province_id) values (4, 'Ville du Quebec', 10)
insert into tbl_city(id, city_name, province_id) values (5, 'Vancouver', 2)
insert into tbl_city(id, city_name, province_id) values (6, 'Calgary', 1)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update ) values ('Mirazur', 15.60, 1, 'A1BC2D', 'Street A', '1', 'suite 111', 'downtown', 1, utc_timestamp(4), utc_timestamp(4))
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Noma', 34.10, 1, 'B1BC2D', 'Street B', '2', 'suite 222', 'Bay B', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Asador Etxebarri', 42.20, 2, 'C1BC2D', 'Street C', '3', 'suite 333', 'Bay C', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Gaggan', 35.00, 2, 'D1BC2D', 'Street D', '4', 'suite 444', 'downtown', 4, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Garanium', 45.67, 3, 'E1BC2D', 'Street E', '5', 'suite 555', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Central', 56.90, 1, 'F1BC2D', 'Street F', '6', 'suite 777', 'Water A', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Mugaritx', 64.00, 1, 'G1BC2D', 'Street G', '7', 'suite 888', 'downtown', 3, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Arpège', 74.50, 1, 'H1BC2D', 'Street H', '8', 'suite 999', 'Qatar Beach', 5, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Disfrutar', 51.00, 2, 'I1BC2D', 'Street I', '9', 'suite 1010', 'downtown', 1, utc_timestamp, utc_timestamp)
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update  ) values ('Maido', 40.00, 1, 'J1BC2D', 'Street J', '10', 'suite 1222', 'downtown', 2, utc_timestamp, utc_timestamp)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (4, 1), (4, 2), (4, 3), (4, 5), (5, 2), (5, 3)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (6, 1), (6, 2), (7, 3), (7, 4), (7, 5), (8, 1)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (8, 2), (8, 3), (8, 4), (8, 5), (8, 6)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (9, 1), (9, 2), (9, 3), (9, 4), (9, 5)
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5)
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6)
