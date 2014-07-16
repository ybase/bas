  create table tb_superType
  (
    superTypeId int primary key auto_increment,     
    typeName varchar(50)                       
  );
  
  insert into tb_superType values(null,'IT技术');
  insert into tb_superType values(null,'文学');
  insert into tb_superType values(null,'艺术');