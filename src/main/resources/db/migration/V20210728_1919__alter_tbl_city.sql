alter table tbl_city add column province_id bigint not null;
alter table tbl_city drop column province_name;