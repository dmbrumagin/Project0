
create table customer(
	customer_id varchar(50) primary key default gen_random_uuid(),
	username varchar(20) unique,
	user_password varchar(20),	
	first_name varchar(20) not null,
	last_name varchar(20) not null	
	);

create table account(
	account_id bigserial primary key,	
	account_balance numeric,
	account_type varchar(20) not null,
	account_holder varchar(50) references customer(customer_id) on delete cascade,
	secondary_account_holder varchar(50) references customer(customer_id) on delete cascade
	);

create table transaction_log(
	transaction_id bigserial primary key,
    origin_account bigint references account(account_id) on delete set null,
    destination_account bigint references account(account_id) on delete set null,
    amount_of_transaction numeric,
	type_of_transaction varchar(10),
	time_of_transaction bigint
	);

drop table transaction_log;
drop table account;
drop table customer;

	