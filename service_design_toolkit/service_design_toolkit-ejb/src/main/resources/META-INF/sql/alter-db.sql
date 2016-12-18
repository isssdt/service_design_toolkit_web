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

-- TASK 418
ALTER TABLE sdt_user drop column description;
ALTER TABLE sdt_user ADD password varchar(30);

