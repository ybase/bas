create table tb_note 
(
  id int primary key auto_increment,       
  title varchar(20) not null ,              
  author varchar(20) not null ,            
  content varchar(50) not null ,            
  ly_time timestamp,                    
  imgs varchar(20)                     
);