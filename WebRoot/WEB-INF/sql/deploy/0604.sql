create table dr_record(
	id int primary key auto_increment,
	drtype int not null, -- 0-BLOG 1-IMAGE 2-TALK 3-TOP
	relid int not null,
	crdate varchar(8),
	crtime varchar(9),
	crusr int,
	usrname varchar(40)
);