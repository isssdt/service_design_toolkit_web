/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  longnguyen
 * Created: Oct 28, 2016
 */


ALTER TABLE touchpoint_field_researcher ADD duration_unit varchar(50);
ALTER TABLE touchpoint_field_researcher ADD FOREIGN KEY (duration_unit)
REFERENCES master_data(id);
ALTER TABLE touchpoint_field_researcher ADD photo_location varchar(500);
ALTER TABLE touchpoint_field_researcher ADD action_time timestamp;
ALTER TABLE touch_point ADD sequence_no int;
ALTER TABLE journey ADD is_geo char(1);



