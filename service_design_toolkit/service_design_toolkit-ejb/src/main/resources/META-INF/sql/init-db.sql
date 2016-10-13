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

CREATE TABLE channel (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE field_researcher (
  id int(11) NOT NULL,
  current_latitude varchar(20) NOT NULL,
  current_longitude varchar(20) NOT NULL,
  last_active timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT field_researcher_ibfk_1 FOREIGN KEY (id) REFERENCES sdt_user (id) ON UPDATE CASCADE
);


CREATE TABLE journey (
  id int(11) NOT NULL AUTO_INCREMENT,
  journey_name varchar(100) DEFAULT NULL,
  no_of_field_researcher int(11) NOT NULL,
  is_active char(1) NOT NULL DEFAULT 'Y',
  start_date date NOT NULL,
  can_be_registered char(1) NOT NULL,
  end_date date NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY journey_name (journey_name)
);


CREATE TABLE journey_field_researcher (
  id int(11) NOT NULL AUTO_INCREMENT,
  journey_id int(11) NOT NULL,
  field_researcher_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY journey_id (journey_id),
  KEY field_researcher_id (field_researcher_id),
  CONSTRAINT journey_field_researcher_ibfk_1 FOREIGN KEY (journey_id) REFERENCES journey (id) ON UPDATE CASCADE,
  CONSTRAINT journey_field_researcher_ibfk_2 FOREIGN KEY (field_researcher_id) REFERENCES field_researcher (id) ON UPDATE CASCADE
);

CREATE TABLE rating (
  id int(11) NOT NULL AUTO_INCREMENT,
  value varchar(45) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE sdt_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL,
  user_role_id int(11) NOT NULL,
  is_active char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (id),
  KEY user_role_id (user_role_id),
  CONSTRAINT sdt_user_ibfk_1 FOREIGN KEY (user_role_id) REFERENCES user_role (id) ON UPDATE CASCADE
);

CREATE TABLE touch_point (
  id int(11) NOT NULL AUTO_INCREMENT,
  journey_id int(11) NOT NULL,
  touch_point_desc varchar(500) DEFAULT NULL,
  latitude varchar(20) DEFAULT NULL,
  longitude varchar(20) DEFAULT NULL,
  radius varchar(10) NOT NULL DEFAULT '2',
  action varchar(200) DEFAULT NULL,
  channel_description varchar(200) DEFAULT NULL,
  channel_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY journey_id (journey_id),
  KEY touch_point_ibfk_2 (channel_id),
  CONSTRAINT touch_point_ibfk_1 FOREIGN KEY (journey_id) REFERENCES journey (id) ON UPDATE CASCADE,
  CONSTRAINT touch_point_ibfk_2 FOREIGN KEY (channel_id) REFERENCES channel (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE touchpoint_field_researcher (
  id int(11) NOT NULL AUTO_INCREMENT,
  field_researcher_id int(11) NOT NULL,
  touchpoint_id int(11) NOT NULL,
  comments varchar(200) DEFAULT NULL,
  reaction varchar(200) DEFAULT NULL,
  rating_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY field_researcher_id (field_researcher_id),
  KEY touchpoint_id (touchpoint_id),
  KEY touchpoint_field_researcher_ibfk_3_idx (rating_id),
  CONSTRAINT touchpoint_field_researcher_ibfk_1 FOREIGN KEY (field_researcher_id) REFERENCES field_researcher (id),
  CONSTRAINT touchpoint_field_researcher_ibfk_2 FOREIGN KEY (touchpoint_id) REFERENCES touch_point (id),
  CONSTRAINT touchpoint_field_researcher_ibfk_3 FOREIGN KEY (rating_id) REFERENCES rating (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

insert into user_role(role_name) values('Field Researcher');




