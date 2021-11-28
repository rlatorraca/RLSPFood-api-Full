create table tbl_restaurant_user_manager (
         restaurant_id bigint not null,
         user_id bigint not null,

         primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8;

alter table tbl_restaurant_user_manager add constraint fk_restaurant_user_restaurant
    foreign key (restaurant_id) references tbl_restaurant (id);

alter table tbl_restaurant_user_manager add constraint fk_restaurant_user_user
    foreign key (user_id) references tbl_user (id);