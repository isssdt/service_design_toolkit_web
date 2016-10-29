/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  longnguyen
 * Created: Oct 28, 2016
 */

use service_design_toolkit;

alter table journey_field_researcher add column status varchar(50);

alter table touchpoint_field_researcher add column status varchar(50);

alter table touchpoint_field_researcher modify column `rating_id` int(11);
