drop database dormitory;

create database dormitory character set utf8;

use dormitory;


create table dr_user(
	id int primary key auto_increment,
	passwd varchar(40) not null,
	name varchar(40) not null,
	email varchar(50) not null,
	phone varchar(20) not null,
	address varchar(200),
	visit numeric(10),
	status numeric(10), -- 0:未登录 1:已登录 2:停用
	role varchar(10), -- admin管理员
	logindate varchar(8),
	logintime varchar(9)
);

create table dr_blog(
id int primary key auto_increment,
theme varchar(100) not null,
blogdesc blob,
crdate varchar(8) not null,
crtime varchar(9) not null,
yescount numeric(10),
crusr int,
usrname varchar(40),
imgid int,
picpath varchar(200)
);

create table dr_image(
id int primary key auto_increment,
picpath varchar(200) unique not null,
crdate varchar(8) not null,
crtime varchar(9) not null,
yescount numeric(10),
position varchar(5) -- C:首页位置(经典照片)  O:其他位置 
);

create table dr_talk(
id int primary key auto_increment,
talkdesc varchar(400),
crdate varchar(8) not null,
crtime varchar(9) not null,
crusr int,
usrname varchar(40),--  用户名称
yescount int,
nocount int,
blogid int
);

create table dr_top(
id int primary key auto_increment,
name varchar(20) not null,
topdesc varchar(100),
crdate varchar(8),
crtime varchar(9),
crusr int,
yescount numeric(10),
nocount numeric(10)
);

create table dr_record(
	id int primary key auto_increment,
	drtype int not null, -- 0-BLOG 1-IMAGE 2-TALK 3-TOP
	relid int not null,
	crdate varchar(8),
	crtime varchar(9),
	crusr int,
	usrname varchar(40)
);

create table dr_plan(
	id int primary key auto_increment,
	plandesc varchar(200),
	crdate varchar(8),
	crtime varchar(9),
	crusr int,
	usrname varchar(40)
);

-- 初始化数据
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419004,'ed0bf01975be7e3ade866ee9a9c365','管理员','862968455@qq.com','88888888','福建厦门',0,0,'admin');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419001,'5b3c3b2672015a14cefab16264d31c','沧桑','894766302@qq.com','88888888','江西南昌',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419003,'e4ef6d69e18916de8ceefee92c7dec','阿凡达','741870293@qq.com','88888888','江西南昌',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419002,'e9aae70cee12b5968787d3d1bb0bc6','邱二','349781885@qq.com','88888888','福建光泽',0,0,'');
insert into dr_user(id,passwd,name,email,phone,address,visit,status,role) values(419005,'1e0fb576a2390c6c1231b5ad0e2f83','死胖子','623241925@qq.com','88888888','广东深圳',0,0,'');

insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(1,'搅死棍法','厕所堵了好几天，薰了好几天，受不了了，棍法诞生了','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(2,'黑丝男二','宿舍有一男，癖好黑丝诱惑，这世界不作死不会死','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(3,'无敌鞋子','据说某男，一鞋子赶跑隔壁好几个宿舍，史上最牛鞋子','20140501','120000000',5,0,0);
insert into dr_top(id,name,topdesc,crdate,crtime,crusr,yescount,nocount) values(4,'无敌爪手','据说某男与某男撕扯...，第二天某男小内内亮了....','20140501','120000000',5,0,0);
