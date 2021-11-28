alter table tbl_paymenttype add date_last_update datetime not null;
update tbl_paymenttype set date_last_update = utc_timestamp;
alter table tbl_paymenttype modify column date_last_update datetime not null;
