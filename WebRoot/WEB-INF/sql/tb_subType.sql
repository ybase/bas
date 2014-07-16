  create table tb_subType
  (
     subTypeId int primary key auto_increment,         
     superTypeId int,                              
     subTypeName varchar(50),                    
     foreign key(superTypeId) references tb_superType(superTypeId)      
  );
  
    insert into tb_subType values(null,1,'程序设计');
  	insert into tb_subType values(null,1,'网络技术');
  	insert into tb_subType values(null,1,'计算机基础');
  	insert into tb_subType values(null,2,'中国文学');
  	insert into tb_subType values(null,2,'外国文学');
  	insert into tb_subType values(null,3,'油画');
  	insert into tb_subType values(null,3,'水彩');
  	insert into tb_subType values(null,3,'雕刻');