  
drop database cterm;
create database cterm;
use cterm;

SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS docuAttach;
DROP TABLE IF EXISTS docuAuth;
DROP TABLE IF EXISTS docu;
DROP TABLE IF EXISTS msgto;
DROP TABLE IF EXISTS msg;
DROP TABLE IF EXISTS noticeAttach;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS overAuth;
DROP TABLE IF EXISTS overtime;
DROP TABLE IF EXISTS vacationAuth;
DROP TABLE IF EXISTS vacation;
DROP TABLE IF EXISTS work;
DROP TABLE IF EXISTS member;




/* Create Tables */

CREATE TABLE docu
(
	docuNO int unsigned NOT NULL AUTO_INCREMENT,
	title varchar(100) NOT NULL,
	note varchar(1000),
	id mediumint unsigned NOT NULL,
	wdate datetime DEFAULT now() NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (docuNO)
);


CREATE TABLE docuAttach
(
	newname char(36) NOT NULL,
	orgname varchar(255) NOT NULL,
	docuNO int unsigned NOT NULL,
	PRIMARY KEY (newname),
	UNIQUE (newname)
);


CREATE TABLE docuAuth
(
	auDocuNO int unsigned NOT NULL AUTO_INCREMENT,
	docuNO int unsigned NOT NULL,
	id mediumint unsigned NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (auDocuNO)
);


CREATE TABLE member
(
	id mediumint unsigned NOT NULL AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	pw varchar(30) NOT NULL,
	dept char(1) DEFAULT '0' NOT NULL,
	duty char(1) DEFAULT '4' NOT NULL,
	joindate datetime DEFAULT now() NOT NULL,
	retiredate datetime,
	state char(1) DEFAULT '1' NOT NULL,
	tel char(13) DEFAULT '000-0000-0000',
	mail varchar(30),
	addr varchar(50),
	vaca tinyint unsigned DEFAULT 0 NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE msg
(
	msgNO int unsigned NOT NULL AUTO_INCREMENT,
	id mediumint unsigned NOT NULL,
	note varchar(1000) DEFAULT '내용 없음' NOT NULL,
	wdate datetime DEFAULT now() NOT NULL,
	PRIMARY KEY (msgNO)
);


CREATE TABLE msgto
(
	msgtoNO int unsigned NOT NULL AUTO_INCREMENT,
	msgNO int unsigned NOT NULL,
	id mediumint unsigned NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (msgtoNO)
);


CREATE TABLE notice
(
	nNO int unsigned NOT NULL AUTO_INCREMENT,
	nTitle varchar(100) DEFAULT '공지사항입니다' NOT NULL,
	nNote varchar(1000) DEFAULT '내용입니다' NOT NULL,
	wdate datetime DEFAULT now() NOT NULL,
	id mediumint unsigned NOT NULL,
	PRIMARY KEY (nNO)
);


CREATE TABLE noticeAttach
(
	nNO int unsigned NOT NULL,
	newname char(36) NOT NULL,
	orgname varchar(255) NOT NULL,
	PRIMARY KEY (newname),
	UNIQUE (newname)
);


CREATE TABLE overAuth
(
	auOverNO int unsigned NOT NULL AUTO_INCREMENT,
	overNO int unsigned NOT NULL,
	id mediumint unsigned NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (auOverNO)
);


CREATE TABLE overtime
(
	overNO int unsigned NOT NULL AUTO_INCREMENT,
	id mediumint unsigned NOT NULL,
	date date,
	start time,
	end time,
	wdate datetime DEFAULT now() NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (overNO)
);


CREATE TABLE vacation
(
	vacaNO int unsigned NOT NULL AUTO_INCREMENT,
	date datetime NOT NULL,
	why varchar(100) DEFAULT '사유 미작성' NOT NULL,
	wdate datetime DEFAULT now() NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	id mediumint unsigned NOT NULL,
	PRIMARY KEY (vacaNO)
);


CREATE TABLE vacationAuth
(
	auVacaNO int unsigned NOT NULL AUTO_INCREMENT,
	id mediumint unsigned NOT NULL,
	vacaNO int unsigned NOT NULL,
	state char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (auVacaNO)
);


CREATE TABLE work
(
	workNO int unsigned NOT NULL AUTO_INCREMENT,
	id mediumint unsigned NOT NULL,
	date date NOT NULL,
	start time,
	end time,
	PRIMARY KEY (workNO)
);



/* Create Foreign Keys */

ALTER TABLE docuAttach
	ADD FOREIGN KEY (docuNO)
	REFERENCES docu (docuNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE docuAuth
	ADD FOREIGN KEY (docuNO)
	REFERENCES docu (docuNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE docu
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE docuAuth
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE msg
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE msgto
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notice
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE overAuth
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE overtime
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE vacation
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE vacationAuth
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE work
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE msgto
	ADD FOREIGN KEY (msgNO)
	REFERENCES msg (msgNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE noticeAttach
	ADD FOREIGN KEY (nNO)
	REFERENCES notice (nNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE overAuth
	ADD FOREIGN KEY (overNO)
	REFERENCES overtime (overNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE vacationAuth
	ADD FOREIGN KEY (vacaNO)
	REFERENCES vacation (vacaNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

