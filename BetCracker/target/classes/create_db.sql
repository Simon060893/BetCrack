create table expense (id_exp Number(15) PRIMARY KEY,type_exp VARCHAR2(15),count_exp NUMBER(5));

create table UserE(id_us Number(15),FIO Varchar2(50),dat_reg DAte,admins Varchar2(5),ip_reg VARCHAR2(15),
login VARCHAR2(25),password VARCHAR2(25),email VARCHAR2(30),id_exp Number(15),
PRIMARY KEY (id_us), FOREIGN KEY (id_exp) REFERENCES expense);

create table history_result_events(id_hstr NUMBER(10),first_name VARCHAR2(50),sec_name VARCHAR2(50), result_event VARCHAR2(10));

var last_objid Number;
create trigger make_connetct  on SUPEROBJ after insert
FOR EACH ROW
BEGIN
  if()
  end;
/
--   for each rows




