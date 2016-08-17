/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  longnguyen
 * Created: Aug 13, 2016
 */

use service_design_toolkit;

create table journey (
    id int not null auto_increment primary key,
    journey_name varchar(100) unique not null,
    no_of_field_researcher int not null,
    is_active char(1) not null default 'Y');

create table touch_point (
    id int not null auto_increment primary key,
    journey_id int not null,
    touch_point_desc varchar(100) not null,
    latitude varchar(20) not null,
    longitude varchar(20) not null,
    radius varchar(10) not null default '2',
    foreign key (journey_id) references journey(id) on update cascade on delete restrict
);

create table user_role (
    id int not null auto_increment primary key,
    role_name varchar(50) not null
);

create table sdt_user (
    id int not null auto_increment primary key,
    username varchar(20) not null,
    user_role_id int not null,
    is_active char(1) not null default 'Y',
    foreign key (user_role_id) references user_role(id) on update cascade on delete restrict
);

create table field_researcher (
    id int not null primary key,
    current_latitude varchar(20) not null,
    current_longitude varchar(20) not null,
    last_active timestamp not null default current_timestamp,
    foreign key (id) references sdt_user(id) on update cascade on delete restrict
);