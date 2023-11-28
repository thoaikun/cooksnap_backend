CREATE DATABASE IF NOT EXISTS cooksnap_database;
USE cooksnap_database;

CREATE TABLE user (
    user_id         INTEGER     AUTO_INCREMENT PRIMARY KEY ,
    full_name       VARCHAR(30)         ,
    day_of_birth    DATE                ,
    height          FLOAT               ,
    weight          FLOAT               ,
    email           VARCHAR(255)        ,
    password        VARCHAR(255)        ,
    role            VARCHAR(255)
);

CREATE TABLE token (
                       id          INTEGER     AUTO_INCREMENT    not null,
                       token       VARCHAR(255)    unique,
                       token_type  VARCHAR(255)    check (token_type in ('BEARER')),
                       expired     BOOLEAN         not null,
                       revoked     BOOLEAN         not null,
                       user_id     INTEGER,
                       FOREIGN KEY (user_id) REFERENCES user(user_id),
                       primary key (id)
);

CREATE TABLE otp (
                     otp_id          INTEGER   AUTO_INCREMENT   PRIMARY KEY ,
                     otp_code        VARCHAR(6)     ,
                     otp_expired     BOOLEAN     ,
                     user_id         INTEGER      ,
                     FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE comment (
    comment_id      INTEGER     PRIMARY KEY ,
    text            VARCHAR(255),
    user_id         INTEGER,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);


CREATE TABLE dish (
    dish_id         INTEGER     PRIMARY KEY ,
    name            VARCHAR(255)        ,
    image_link      VARCHAR(255)        ,
    about           VARCHAR(1027)
);

CREATE TABLE rating (
    rate_id         INTEGER     PRIMARY KEY ,
    rate_score      INTEGER     ,
    dish_id         INTEGER     ,
    user_id         INTEGER     ,
    FOREIGN KEY (dish_id) REFERENCES dish(dish_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE favorite_list (
    id              INTEGER     PRIMARY KEY ,
    user_id         INTEGER         ,
    FOREIGN KEY (user_id)   REFERENCES user(user_id)
);

CREATE TABLE favorite_dish (
    id              INTEGER     PRIMARY KEY ,
    dish_id         INTEGER ,
    favorite_list_id   INTEGER ,
    FOREIGN KEY (dish_id) REFERENCES dish(dish_id),
    FOREIGN KEY (favorite_list_id) REFERENCES favorite_list(id)
);


CREATE TABLE date (
    id          INTEGER     PRIMARY KEY ,
    type_date   VARCHAR(10),
    dish_id     INTEGER ,
    FOREIGN KEY (dish_id) REFERENCES dish(dish_id)
);
