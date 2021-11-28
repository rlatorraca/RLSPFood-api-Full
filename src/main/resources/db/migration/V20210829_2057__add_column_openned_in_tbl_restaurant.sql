alter table tbl_restaurant add opened tinyint(1) not null;
update tbl_restaurant set opened = false;