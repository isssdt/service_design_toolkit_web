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

ALTER TABLE touch_point ADD duration_day varchar(4);
ALTER TABLE touch_point ADD duration_hour varchar(2);
ALTER TABLE touch_point ADD duration_minute varchar(2);

ALTER TABLE journey ADD is_sequence char(1);

