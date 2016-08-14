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

create table journey (id int not null auto_increment primary key,journey_name varchar(100) not null,no_of_field_researcher int not null,is_active char(1));

create table touch_point (id int not null auto_increment primary key,journey_id int not null,touch_point_desc varchar(100) not null,latitude varchar(20) not null,longitude varchar(20) not null,radius varchar(10) not null,foreign key (journey_id) references journey(id) on update cascade on delete restrict);

