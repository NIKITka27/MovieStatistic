create table film
(
    id       bigint not null
        primary key auto_increment,
    name text   not null
);
create table user
(
    id       bigint not null
         auto_increment,
    name text   not null,
    password text not null,
    email text not null,
    primary key(id)

);
create table role
(
    id   bigint not null auto_increment,
    name text   not null,
    primary key(id)

);
create table user_roles
(
    user_id bigint not null,
    roles_id   bigint not null,
    primary key (user_id, roles_id),
        foreign key (user_id) references user (id),
        foreign key (roles_id) references role (id)
);
create table review
(
    id bigint not null auto_increment,
    user_id bigint not null,
    film_id bigint not null,
    rating int not null,
    created_at date not null,
    primary key (id) ,
    foreign key (user_id) REFERENCES user(id),
    foreign key (film_id) REFERENCES film(id)
);



