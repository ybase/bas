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