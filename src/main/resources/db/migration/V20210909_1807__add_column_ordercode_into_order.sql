alter table tbl_order add ordercode varchar(36) not null after id;
update tbl_order set ordercode = uuid();
alter table tbl_order add constraint uk_order_ordercode unique (ordercode);