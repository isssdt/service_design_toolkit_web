/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  longnguyen
 * Created: Oct 28, 2016
 */

CREATE TABLE master_data (
    id varchar(50) primary key,
    data_value varchar(50)
);

ALTER TABLE touch_point DROP duration_day;
ALTER TABLE touch_point DROP duration_hour;
ALTER TABLE touch_point DROP duration_minute;

ALTER TABLE touch_point ADD duration decimal(3,2);
ALTER TABLE touch_point ADD duration_unit varchar(50);

ALTER TABLE touch_point ADD FOREIGN KEY (duration_unit)
REFERENCES master_data(id);

insert into master_data values('TOUCH_POINT_DURATION_UNIT_DAY', 'Day');
insert into master_data values('TOUCH_POINT_DURATION_UNIT_HOUR', 'Hour');
insert into master_data values('TOUCH_POINT_DURATION_UNIT_MINUTE', 'Minute');

ALTER TABLE touchpoint_field_researcher ADD duration decimal(3,2);





