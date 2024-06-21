CREATE TABLE user (
      id bigint auto_increment not null ,
      firstName varchar(255),
      lastName varchar(255),
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
    Add column createDate timestamp;
drop table tache;
CREATE TABLE projects_user (
                              projects_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL,
                              PRIMARY KEY (projects_id, user_id),
                              FOREIGN KEY (projects_id) REFERENCES project(id),
                              FOREIGN KEY (user_id) REFERENCES user(id)
);
CREATE TABLE tache (
                       id bigint auto_increment not null,
                       description TEXT,
                       statut varchar(50),
                       PRIMARY KEY (ID),
                       id_u long not null,
                       id_p bigint not null,
                       FOREIGN KEY (id_u) REFERENCES user(id),
                       FOREIGN KEY (id_p) REFERENCES project(id)
);

