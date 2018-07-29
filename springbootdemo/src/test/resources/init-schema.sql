DROP TABLE IF EXISTS user;
create table user (
	id int(11) unsigned not null auto_increment,
	name varchar(64) not null default '',
    password varchar(128) not null default '',
    salt varchar(32) not null default '',
    head_url varchar(256) not null default '',
    primary key(id),
    unique key(name)
)engine=InnoDB default charset=utf8;

DROP TABLE IF EXISTS question;
create table question (
	id INT not null auto_increment,
	title varchar(255) not null,
    content text null,
    user_id int not null,
    created_date datetime not null,
    comment_count int not null,
    primary key(id),
    index date_index(created_date asc)
);

DROP TABLE IF EXISTS login_ticket;
  CREATE TABLE login_ticket (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    ticket VARCHAR(45) NOT NULL,
    expired DATETIME NOT NULL,
    status INT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX ticket_UNIQUE (ticket ASC)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
