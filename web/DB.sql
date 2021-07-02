create database BLOG_SYSTEM;
use BLOG_SYSTEM;
-- user

drop table image;
drop table tags;
drop table comment;
drop table blog;
drop table user;
create table user (
			uid VARCHAR(10) PRIMARY KEY, 
			pwd VARCHAR(15) NOT NULL, 
			email VARCHAR(50) NOT NULL, 
			name VARCHAR(25) NOT NULL, 
			bio VARCHAR(500),
			dob date NOT NULL 
);
create table blog (
			bid VARCHAR(30) PRIMARY KEY,
			uid VARCHAR(10),
			bname VARCHAR(15) NOT NULL,
 			bdate DATE NOT NULL,
 			likes int, 
 			dislikes int,
 			content VARCHAR(5000),
 			FOREIGN KEY (uid) REFERENCES USER(uid)
);
create table image (
				blog_id VARCHAR(30), 
				img_id  int  AUTO_INCREMENT PRIMARY KEY, 
				img VARCHAR(500) NOT NULL,
				FOREIGN KEY(blog_id) REFERENCES blog(bid) ON DELETE CASCADE
				)ENGINE=INNODB;

CREATE TABLE TAGS(
				blog_id VARCHAR(30) NOT NULL,
				tag VARCHAR(20) NOT NULL,
				FOREIGN KEY(blog_id) REFERENCES blog(bid) ON DELETE CASCADE

);
create table comment (
				comment_id int PRIMARY KEY AUTO_INCREMENT, 
				uid VARCHAR(10) NOT NULL,
			 	bid VARCHAR(30) NOT NULL, 
			 	content VARCHAR(500) NOT NULL, 
			 	likes int NOT NULL,
  				dislikes int NOT NULL,
  				cdate date, 
 				FOREIGN KEY (uid) REFERENCES USER(uid),
 				FOREIGN KEY (bid) REFERENCES blog(bid)
);






















