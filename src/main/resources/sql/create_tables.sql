CREATE TABLE user (
      id bigint auto_increment not null ,
      firstname varchar(255),
      lastname varchar(255),
      email varchar(255),
      password varchar(255),
      create_date timestamp,
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
alter table user
    ADD column createDate timestamp;
CREATE TABLE project_user (
                              project_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL,
                              PRIMARY KEY (project_id, user_id),
                              FOREIGN KEY (project_id) REFERENCES project(id),
                              FOREIGN KEY (user_id) REFERENCES user(id)
);
