alter table tbl_restaurant add active tinyint(1) not null;
update tbl_restaurant set active = true;