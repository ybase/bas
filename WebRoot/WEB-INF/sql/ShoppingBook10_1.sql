-- 数据库名为
create database ShoppingBook;

-- 1.用户表tb_user:
create table tb_user
(
	id int primary key auto_increment,   -- 用户ID
    	name varchar(30) not null,            -- 用户名
    	password varchar(16) not null,        -- 密码
	email varchar(50) not null,               -- 邮箱
	trueName varchar(40) not null,           -- 昵称
    	sex varchar(30),            -- 性别
    	birthday varchar(20),        -- 生日
	address varchar(100),           -- 住址
	postcode varchar(10),           -- 邮编
	phone varchar(15),             -- 固定电话
	mphone varchar(15),           -- 手机
   	 question varchar(30) not null,         -- 问题
   	 answer varchar(30) not null,         -- 答案
	img varchar(100) ,                  -- 用户头像
	 score int default 1000                -- 积分 初始化为1000  不让用户填写 系统自动添加
);
create table tb_user
(
	id int primary key auto_increment,  
    name varchar(30) not null,           
    password varchar(16) not null,        
	email varchar(50) not null,              
	trueName varchar(40) not null,           
    sex varchar(30),            
    birthday varchar(20),        
	address varchar(100),           
	postcode varchar(10),          
	phone varchar(15),          
	mphone varchar(15),          
   	question varchar(30) not null,        
   	answer varchar(30) not null,        
	img varchar(100) ,                 
	score int default 1000               
);
--  2.大类表
create table tb_superType
  (
    superTypeId int primary key auto_increment,      -- 大类Id
    typeName varchar(50)                        -- 大类名
  );
  
  create table tb_superType
  (
    superTypeId int primary key auto_increment,     
    typeName varchar(50)                       
  );

--  3.小类表

create table tb_subType
  (
     subTypeId int primary key auto_increment,         -- 小类id
     superTypeId int,                              -- 大类id
     subTypeName varchar(50),                     -- 小类名
     foreign key(superTypeId) references tb_superType(superTypeId)        -- 设置外键
  );
  
  create table tb_subType
  (
     subTypeId int primary key auto_increment,         
     superTypeId int,                              
     subTypeName varchar(50),                    
     foreign key(superTypeId) references tb_superType(superTypeId)      
  );


4.书表

  create table tb_book                 
(
  bookId int primary key auto_increment,     -- 书号
  superTypeId int,                              -- 大类ID                    
  subTypeId int,                                -- 小类ID                      
  bookName varchar(200),						-- 书名
  ISBN varchar(20),                             -- ISBN号  流水号
  introduce text,                               -- 书本简介                             
  price float,                                  -- 原价                          
  nowPrice float,                               -- 现价                     
  picture varchar(200),  						-- 图片
  pages int,									-- 页数
  publisher varchar(40),						-- 出版社
  author varchar(20),                           -- 作者              
  inTime timestamp,                             -- 入库时间                         
  newBooks int,                                 -- 是否新品                    
  saleBooks int,                                -- 是否打折品                    
  hostBooks int,                                -- 是否热卖品  
  specialBooks int,                             -- 是否是特种书
  bookNum  int,                                 -- 书的数量                      
  foreign key(subTypeId ) references tb_subType(subTypeId),
  foreign key(superTypeId) references tb_superType (superTypeId)
 );

 create table tb_book                 
(
  bookId int primary key auto_increment,     
  superTypeId int,                                        
  subTypeId int,                                            
  bookName varchar(200),						
  ISBN varchar(20),                           
  introduce text,                                                     
  price float,                                                     
  nowPrice float,                                          
  picture varchar(200),  						
  pages int,									
  publisher varchar(40),						
  author varchar(20),                                  
  inTime timestamp,                                            
  newBooks int,                                          
  saleBooks int,                                               
  hostBooks int,                                
  specialBooks int,                            
  bookNum  int,                                               
  foreign key(subTypeId ) references tb_subType(subTypeId),
  foreign key(superTypeId) references tb_superType (superTypeId)
 );


5.订单表

  create table tb_order
  (
    orderId int primary key auto_increment,   -- 订单id
name varchar(20),                    -- 用户名
realName varchar(20),                -- 真实姓名
    address varchar(20),                  -- 详细住址
    postcode varchar(10),                 -- 邮编
    email varchar(20),                   -- 邮箱
    orderDate timestamp,                -- 下订单时间
    flag int                           -- 是否发货 0 未发货 1 发货 2 货物冻结
  );
  
    create table tb_order
  (
    orderId int primary key auto_increment,   
	name varchar(20),                 
	realName varchar(20),              
    address varchar(20),                 
    postcode varchar(10),                
    email varchar(20),                   
    orderDate timestamp,                
    flag int                           
  );

6.订单详细信息
create table tb_orderItem
  (
    orderItemId int primary key auto_increment,-- 订单项id 
    orderId int,                              -- 订单id
    bookId int,                              -- 书号
    bookName varchar(50),                   -- 书名
    price float,                             -- 价格
    bookNum int,                          -- 书的数量
    foreign key(orderId) references tb_order(orderId),
    foreign key(bookId) references tb_book(bookId)
  );

create table tb_orderItem
  (
    orderItemId int primary key auto_increment,    
    orderId int,                            
    bookId int,                              
    bookName varchar(50),                 
    price float,                           
    bookNum int,                         
    foreign key(orderId) references tb_order(orderId),
    foreign key(bookId) references tb_book(bookId)
  );
7.留言板

create table tb_note 
(
  id int primary key auto_increment,        -- 用户id
  title varchar(20) not null ,               -- 标题
  author varchar(20) not null ,             -- 用户名
  content varchar(50) not null ,             -- 内容
  ly_time timestamp,                    -- 留言时间
  imgs varchar(50)                     -- 用户头像
);
create table tb_note 
(
  id int primary key auto_increment,       
  title varchar(20) not null ,              
  noteName varchar(20) not null ,            
  content varchar(50) not null ,            
  ly_time timestamp,                    
  imgs varchar(20)                     
);

