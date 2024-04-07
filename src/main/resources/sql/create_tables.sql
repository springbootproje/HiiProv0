CREATE TABLE user (
      id bigint auto_increment not null ,
      firstname varchar(255),
      lastname varchar(255),
      email varchar(255),
      password varchar(255),
      PRIMARY KEY (ID)
);
CREATE TABLE project (
     id bigint auto_increment not null ,
     title varchar(255),
     description varchar(255),
     user_id bigint not null ,
     PRIMARY KEY (ID),
     FOREIGN KEY (user_id) REFERENCES user(id)
);