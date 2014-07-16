create database ShoppingBook10_1;
use ShoppingBook10_1;
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