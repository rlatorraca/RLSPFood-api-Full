set foreign_key_checks = 0;

lock tables tbl_city write, tbl_cuisine write, tbl_group write, tbl_paymenttype write,
	tbl_permission write, tbl_product write, tbl_province write,
	tbl_restaurant write, tbl_user write, tbl_user_group write,
	tbl_group_permission write, tbl_restaurant_paymenttype write, tbl_restaurant_user_manager write,
	tbl_order write, tbl_orderitem write, tbl_taxprovince write, tbl_product_photo write,
    oauth_client_details write;

delete from tbl_city;
delete from tbl_cuisine;
delete from tbl_group;
delete from tbl_paymenttype;
delete from tbl_permission;
delete from tbl_product;
delete from tbl_province;
delete from tbl_restaurant;
delete from tbl_user;
delete from tbl_user_group;
delete from tbl_group_permission;
delete from tbl_restaurant_paymenttype;
delete from tbl_restaurant_user_manager;
delete from tbl_order;
delete from tbl_orderitem;
delete from tbl_taxprovince;
delete from tbl_product_photo;
delete from oauth_client_details;

set foreign_key_checks = 1;

alter table tbl_city auto_increment = 1;
alter table tbl_cuisine auto_increment = 1;
alter table tbl_group auto_increment = 1;
alter table tbl_paymenttype auto_increment = 1;
alter table tbl_permission auto_increment = 1;
alter table tbl_product auto_increment = 1;
alter table tbl_province auto_increment = 1;
alter table tbl_restaurant auto_increment = 1;
alter table tbl_user auto_increment = 1;
alter table tbl_taxprovince auto_increment = 1;
alter table tbl_order auto_increment = 1;
alter table tbl_orderitem auto_increment = 1;
alter table oauth_client_details auto_increment = 1;


insert into tbl_cuisine (id, cuisine_name) values (1, 'Fusion');
insert into tbl_cuisine (id, cuisine_name) values (2, 'Haule');
insert into tbl_cuisine (id, cuisine_name) values (3, 'Nouvelle');
insert into tbl_cuisine (id, cuisine_name) values (4, 'Vegan');
insert into tbl_cuisine (id, cuisine_name) values (5, 'Vegetarian');
insert into tbl_cuisine (id, cuisine_name) values (6, 'Argentina');
insert into tbl_cuisine (id, cuisine_name) values (7, 'Spanish');
insert into tbl_cuisine (id, cuisine_name) values (8, 'Brazilian');
insert into tbl_cuisine (id, cuisine_name) values (9, 'Portuguese');

insert into tbl_paymenttype (payment_type, date_last_update) values ('Cash', utc_timestamp);
insert into tbl_paymenttype  (payment_type, date_last_update) values ('Credit card', utc_timestamp);
insert into tbl_paymenttype  (payment_type, date_last_update) values ('Debit card', utc_timestamp);
insert into tbl_paymenttype (payment_type, date_last_update) values ('Pay Pal', utc_timestamp);
insert into tbl_paymenttype  (payment_type, date_last_update) values ('Bitcoin', utc_timestamp);
insert into tbl_paymenttype  (payment_type, date_last_update) values ('Etherium', utc_timestamp);

insert into tbl_taxprovince (id, tax_percentual) values (1, 0.15);
insert into tbl_taxprovince (id, tax_percentual) values (2, 0.14975);
insert into tbl_taxprovince (id, tax_percentual) values (3, 0.13);
insert into tbl_taxprovince (id, tax_percentual) values (4, 0.12);
insert into tbl_taxprovince (id, tax_percentual) values (5, 0.11);
insert into tbl_taxprovince (id, tax_percentual) values (6, 0.05);


insert into tbl_province (province_name,taxprovince_id) values ('Alberta', 6);
insert into tbl_province (province_name,taxprovince_id) values ('British Columbia', 4);
insert into tbl_province (province_name,taxprovince_id) values ('Manitoba', 4);
insert into tbl_province (province_name,taxprovince_id) values ('New Brunswick', 1);
insert into tbl_province (province_name,taxprovince_id) values ('Newfoundland and Labrador', 1);
insert into tbl_province (province_name,taxprovince_id) values ('Northwest Territories.', 6);
insert into tbl_province (province_name,taxprovince_id) values ('Nova Scotia', 1);
insert into tbl_province (province_name,taxprovince_id) values ('Nunavut', 6);
insert into tbl_province (province_name,taxprovince_id) values ('Ontario', 3);
insert into tbl_province (province_name,taxprovince_id) values ('Quebec', 2);
insert into tbl_province (province_name,taxprovince_id) values ('Prince Edward Island', 1);
insert into tbl_province (province_name,taxprovince_id) values ('Saskatchewan', 5);
insert into tbl_province (province_name,taxprovince_id) values ('Yukon', 6);

insert into tbl_city(id, city_name, province_id) values (1, 'Ottawa', 9);
insert into tbl_city(id, city_name, province_id) values (2, 'Toronto', 9);
insert into tbl_city(id, city_name, province_id) values (3, 'Montreal', 10);
insert into tbl_city(id, city_name, province_id) values (4, 'Ville du Quebec', 10);
insert into tbl_city(id, city_name, province_id) values (5, 'Vancouver', 2);
insert into tbl_city(id, city_name, province_id) values (6, 'Calgary', 1);

insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Mirazur', 15.60, 1, 'A1BC2D', 'Street A', '1', 'suite 111', 'downtown', 1, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Noma', 34.10, 1, 'B1BC2D', 'Street B', '2', 'suite 222', 'Bay B', 3, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Asador Etxebarri', 42.20, 2, 'C1BC2D', 'Street C', '3', 'suite 333', 'Bay C', 3, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Gaggan', 35.00, 2, 'D1BC2D', 'Street D', '4', 'suite 444', 'downtown', 4, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Garanium', 45.67, 3, 'E1BC2D', 'Street E', '5', 'suite 555', 'downtown', 1, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Central', 56.90, 1, 'F1BC2D', 'Street F', '6', 'suite 777', 'Water A', 2, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Mugaritx', 64.00, 1, 'G1BC2D', 'Street G', '7', 'suite 888', 'downtown', 3, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Arpège', 74.50, 1, 'H1BC2D', 'Street H', '8', 'suite 999', 'Qatar Beach', 5, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Disfrutar', 51.00, 2, 'I1BC2D', 'Street I', '9', 'suite 1010', 'downtown', 1, utc_timestamp, utc_timestamp, true, true);
insert into tbl_restaurant (name_restaurant, delivery_fee, cuisine_id, address_postalcode, address_street, address_number, address_complement, address_district, address_city_id, created_date, date_last_update, active, opened) values ('Maido', 40.00, 1, 'J1BC2D', 'Street J', '10', 'suite 1222', 'downtown', 2, utc_timestamp, utc_timestamp, true, true);

insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (4, 1), (4, 2), (4, 3), (4, 5), (5, 2), (5, 3);
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (6, 1), (6, 2), (7, 3), (7, 4), (7, 5), (8, 1);
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (8, 2), (8, 3), (8, 4), (8, 5), (8, 6);
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (9, 1), (9, 2), (9, 3), (9, 4), (9, 5);
insert into tbl_restaurant_paymenttype (restaurant_id, payment_type_id) values (10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6);

insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 0, 3);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 0, 3);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 0, 4);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into tbl_product (product_name, product_description, product_price, product_active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into tbl_group (id, group_name) values (1, 'Manager');
insert into tbl_group (id, group_name) values (2, 'Sales Associate');
insert into tbl_group (id, group_name) values (3, 'Administrative Assistant');
insert into tbl_group (id, group_name) values (4, 'Register');


insert into tbl_user (id, user_name, user_email, user_password, user_created, user_last_modified) values
                       (1, 'Ann Green Gable', 'ann_man@rlspfood.com.ca', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (2, 'Pascoal Moreira Cabral', 'pascoal_sa@rlspfood.com.ca', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (3, 'Albert Einstein', 'albert_aa@rlspfood.com.ca', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (4, 'Maximus Alexandre', 'maximus_reg@rlspfood.com.ca', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (5, 'Julius Cesar', 'julio_cesar@rlspfood.com.ca', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (6, 'Marcus Aurelio Einstein', 'marcusaurelius@gmail.com', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp),
                       (7, 'Cleopatra do Egito', 'cleodoegito@gmail.com', '$2a$12$Arz3KGigB/uD993RiMwlWuqnL.9wSe8Iln3AhUfpz1O2m5LtVFJYy', utc_timestamp, utc_timestamp);

insert into tbl_permission (id, permission_description, permission_name) values
                           (1, 'Has permissions for edit cuisine resources', 'EDIT_CUISINE'),
                           (2, 'Has permissions for create and edit payment types resources', 'EDIT_PAYMENT_TYPES'),
                           (3, 'Has permissions for create and edit city resources', 'EDIT_CITIES'),
                           (4, 'Has permissions for create and edit city resources', 'EDIT_PROVINCES'),
                           (5, 'Has permissions for query user, groups and permissions resources', 'QUERY_USERS_GROUPS_PERMISSIONS'),
                           (6, 'Has permissions for create user, groups and permissions resources', 'EDIT_USERS_GROUPS_PERMISSIONS'),
                           (7, 'Has permissions for create and edit restaurant resources', 'EDIT_RESTAURANTS'),
                           (8, 'Has permissions for create and edit product resources', 'EDIT_PRODUCTS'),
                           (9, 'Has permissions for query order resources', 'QUERY_ORDERS'),
                           (10, 'Has permissions for crete and edit order resources', 'EDIT_ORDERS'),
                           (11, 'Has permissions for generate reports', 'GENERATE_REPORTS');



insert into tbl_group_permission (group_id, permission_id)
select 1, id from tbl_permission;


insert into tbl_group_permission (group_id, permission_id)
select 2, id from tbl_permission where permission_name like 'QUERY_%';

insert into tbl_group_permission (group_id, permission_id)
select 2, id from tbl_permission where permission_name = 'EDIT_RESTAURANT';


insert into tbl_group_permission (group_id, permission_id)
select 3, id from tbl_permission where permission_name like 'QUERY_%';


insert into tbl_group_permission (group_id, permission_id)
select 4, id from tbl_permission where permission_name like '%_RESTAURANTS' or permission_name like '%_PRODUCTS';

insert into tbl_user_group (user_id, group_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into tbl_restaurant_user_manager (restaurant_id, user_id) values (1, 5), (2, 5), (3, 6), (4, 6), (5, 7), (6, 7);

insert into tbl_order (id, ordercode, restaurant_id, user_client_id, paymenttype_id,
                       address_city_id, address_postalcode, address_street,
                       address_number, address_complement, address_district,
                       status, createddate, beforetax,
                       deliveryfee, taxpercentual , aftertax)
        values (1,'3170207a-5f37-43c7-b3e5-6e29e3138499', 1, 6, 1, 1, 'A1C2S4', 'Bean street', '500', 'Apt 801', 'downtown', 'CREATED', '2021-08-30 11:56:29', 268.02, 10, 0.13, 302.86);

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
        values (1, 1, 1, 1, 78.9, 78.9, 'Spicy sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (2, 1, 3, 2, 44.11, 88.22, 'Donair sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (3, 1, 5, 1, 78.9, 78.9, 'Spicy sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (4, 1, 7, 4, 5.50, 22.00, 'No spicy');

insert into tbl_order (id, ordercode, restaurant_id, user_client_id, paymenttype_id,
                       address_city_id, address_postalcode, address_street,
                       address_number, address_complement, address_district,
                       status, createdDate, beforeTax,
                       deliveryFee, taxpercentual , afterTax)
values (2, 'd5aa1c2d-f077-40ae-944a-f1e3e6e3aa84', 5, 5, 1, 1, 'M2C0X7', 'Principal street', '3300', 'Apt 2309', 'New Glasgow', 'CONFIRMED', '2020-10-05 11:56:29', 194.06, 10, 0.12, 217.34);

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (5, 2, 1, 1, 48.9, 48.9, 'Spicy sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (6, 2, 2, 2, 21.11, 42.22, 'No comments');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (7, 2, 3, 1, 102.94, 102.94, 'Spicy sauce');

insert into tbl_order (id, ordercode, restaurant_id, user_client_id, paymenttype_id,
                       address_city_id, address_postalcode, address_street,
                       address_number, address_complement, address_district,
                       status, createdDate, deliveryDate, beforeTax,
                       deliveryFee, taxpercentual , afterTax)
values (3, '600c1fd5-1db1-45ee-b740-f54ca916b46c', 6, 5, 1, 1, 'Z2C4X7', 'Inversion street', '3300', 'Apt 2309', 'Georgetown', 'DELIVERED', '2019-09-27 09:30:00', '2019-10-30 10:23:50', 60.11, 10, 0.12, 77.33);

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (8, 3, 1, 1, 10.0, 10.0, 'Spicy sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (9, 3, 6, 2, 20.11, 20.22, 'No comments');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (10, 3, 3, 1, 30.00, 30.00, 'Spicy sauce');

insert into tbl_order (id, ordercode, restaurant_id, user_client_id, paymenttype_id,
                       address_city_id, address_postalcode, address_street,
                       address_number, address_complement, address_district,
                       status, createdDate, deliveryDate, beforeTax,
                       deliveryFee, taxpercentual , afterTax)
values (4, 'b87e5f22-8178-4237-bf68-8b66b12bfa29', 6, 7, 1, 1, 'D2C4X7', 'Water street', '44', 'Apt 2309', 'Peper', 'DELIVERED', '2019-09-22 11:10:00', '2019-10-30 11:56:29', 194.06, 10, 0.12, 217.34);

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (11, 4, 1, 1, 48.9, 48.9, 'Spicy sauce');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (12, 4, 2, 2, 21.11, 42.22, 'No comments');

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (13, 4, 3, 1, 102.94, 102.94, 'Spicy sauce');

insert into tbl_order (id, ordercode, restaurant_id, user_client_id, paymenttype_id,
                       address_city_id, address_postalcode, address_street,
                       address_number, address_complement, address_district,
                       status, createdDate, deliveryDate, beforeTax,
                       deliveryFee, taxpercentual , afterTax)
values (5, '122f931e-795e-421b-87d5-f7502658cc70', 7, 6, 1, 1, 'E9C4X8', 'Projected street', '098', 'Apt 2309', 'New Caledonia', 'DELIVERED', '2019-10-30 02:10:00', '2019-10-30 03:13:20',48.90, 10, 0.12, 54.78);

insert into tbl_orderitem (id, order_id, product_id, quantity, unitprice, totalprice, comments)
values (14, 5, 1, 1, 48.9, 48.9, 'Spicy sauce');

insert into oauth_client_details (
    client_id,
    resource_ids,
    client_secret,
    scope,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    autoapprove
) values (
   'rlspfood-web',
    null,
    '$2a$12$b688vY.adBMMtqt4qUJKceaA.1Fn077sjFkD3HHQ8ebP8gA45i6cK',
    'READ,WRITE',
    'password',
     null,
     null,
     60 * 60 * 6,
     60 * 60 * 24 * 7,
     null
);

insert into oauth_client_details (
    client_id,
    resource_ids,
    client_secret,
    scope,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    autoapprove
) values (
     'rlspfood-mobile',
     null,
     '$2a$12$b688vY.adBMMtqt4qUJKceaA.1Fn077sjFkD3HHQ8ebP8gA45i6cK',
     'READ,WRITE',
     'password',
     null,
     null,
     60 * 60 * 3,
     60 * 60 * 24 * 2,
     null
 );

insert into oauth_client_details (
    client_id,
    resource_ids,
    client_secret,
    scope,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    autoapprove
) values (
   'food-analytics',
    null,
   '$2a$12$b688vY.adBMMtqt4qUJKceaA.1Fn077sjFkD3HHQ8ebP8gA45i6cK',
    'READ,WRITE',
    'authorization_code',
    'http://www.foodanalytics.local:8084',
     null,
     null,
     null,
     null
);

insert into oauth_client_details (
    client_id,
    resource_ids,
    client_secret,
    scope,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    autoapprove
) values (
   'billing-token',
    null,
   '$2a$12$b688vY.adBMMtqt4qUJKceaA.1Fn077sjFkD3HHQ8ebP8gA45i6cK',
   'READ,WRITE',
   'client_credentials',
    null,
   'QUERY_ORDERS,GENERATE_REPORTS',
   null,
   null,
   null
);

unlock tables;
