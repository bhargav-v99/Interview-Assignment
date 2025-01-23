DROP TABLE IF EXISTS WEBSITE;

CREATE TABLE WEBSITE (
 id int not null auto_increment,
 name varchar(50),
 url varchar(250),
 created_by varchar(100),
 created_at timestamp,
 primary key (id),
 unique key (name)
 );