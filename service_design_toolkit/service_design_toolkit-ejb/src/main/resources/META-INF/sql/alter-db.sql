/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  longnguyen
 * Created: Oct 28, 2016
 */

ALTER TABLE touch_point DROP duration;
ALTER TABLE touch_point ADD duration int;

ALTER TABLE touchpoint_field_researcher DROP duration;
ALTER TABLE touchpoint_field_researcher ADD duration int;




