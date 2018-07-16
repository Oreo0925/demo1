/* device */
CREATE TABLE device(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    parent_id bigint(10) NOT NULL DEFAULT 0, 
    full_id varchar(30) NOT NULL DEFAULT '', 
    name varchar(30) NOT NULL DEFAULT '', 
    identifier varchar(30) NOT NULL DEFAULT '', 
    rule_note varchar(30) NOT NULL DEFAULT '', 
    rule_condition varchar(30) NOT NULL DEFAULT '', 
    description varchar(100) NOT NULL DEFAULT '', 
    customer varchar(100) NOT NULL DEFAULT 'Celefish', 
    enabled bit(1) DEFAULT true, 
    automatic bit(1) DEFAULT false, 
    status int NOT NULL DEFAULT 0, 
    last_record_time datetime DEFAULT NULL,
    record_time_limit bigint NOT NULL DEFAULT 50000,
    device_type_id int NOT NULL DEFAULT 0, 
    creator int DEFAULT NULL DEFAULT 0, 
    deleted bit(1) NOT NULL DEFAULT 0, 
    temp_id int NOT NULL DEFAULT 0, 
    CONSTRAINT PK_device PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_type */
CREATE TABLE device_type(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    name varchar(30) DEFAULT NULL, 
    protocal int NOT NULL, 
    frequency varchar(30) DEFAULT NULL, 
    CONSTRAINT PK_type PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_dynamic_properties */
CREATE TABLE device_dynamic_properties(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
	device_id bigint(10) NOT NULL, 
    english_name varchar(100) NOT NULL, 
    name varchar(30) NOT NULL, 
    value varchar(50) NOT NULL,
    fix bit(1) NOT NULL, 
    CONSTRAINT PK_device_dynamic_properties PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_temp */
CREATE TABLE device_temp(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    name varchar(30) NOT NULL, 
    deleted bit(1) NOT NULL, 
    CONSTRAINT PK_device_temp PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_temp_mapping */
CREATE TABLE device_temp_mapping(
	temp_id bigint(10) NOT NULL, 
	device_id bigint(10) NOT NULL, 
    CONSTRAINT PK_device_temp_mapping PRIMARY KEY (temp_id, device_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_temp_dynamic_properties  */
CREATE TABLE device_temp_dynamic_properties(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    device_temp_id bigint(10) NOT NULL, 
    english_name varchar(100) NOT NULL, 
    name varchar(50) NOT NULL, 
    deleted bit(1) NOT NULL, 
    fix bit(1) NOT NULL, 
    CONSTRAINT PK_type PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_static_properties */
CREATE TABLE device_static_properties(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    english_name varchar(100) NOT NULL, 
    name varchar(50) NOT NULL, 
    fix bit(1) NOT NULL, 
    CONSTRAINT PK_device_static_properties PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* device_group */
CREATE TABLE device_group(
	device_id bigint(10) NOT NULL, 
    group_id bigint(10) NOT NULL, 
    CONSTRAINT PK_device_group PRIMARY KEY (device_id, group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* cn_group */
CREATE TABLE cn_group(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    parent_id bigint(10) NOT NULL, 
    name varchar(30) NOT NULL, 
    location varchar(30) DEFAULT NULL, 
    description varchar(100), 
    creator bigint(10) NOT NULL,
    map_type varchar(50) NOT NULL, 
    deleted bit(1) NOT NULL,
    CONSTRAINT PK_groups PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* group_properties */
CREATE TABLE group_properties(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    group_id bigint(10) NOT NULL, 
    english_name varchar(100) NOT NULL, 
    name varchar(30) NOT NULL, 
    value varchar(100) NOT NULL,
    fix bit(1) NOT NULL,
    deleted bit(1) NOT NULL,
    CONSTRAINT PK_device_group PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* role */
CREATE TABLE role(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
	name varchar(30) NOT NULL, 
	description varchar(100) NOT NULL, 
	creator bigint(10) NOT NULL,
	priority bigint(10) NOT NULL,
	CONSTRAINT PK_role PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* role_group */
CREATE TABLE role_group(
    role_id bigint(10) NOT NULL,
   group_id bigint(10) NOT NULL,
   CONSTRAINT PK_role_group PRIMARY KEY (role_id, group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* member_role */
CREATE TABLE member_role(
	member_id bigint(10) NOT NULL, 
	role_id bigint(10) NOT NULL, 
	CONSTRAINT PK_member_role PRIMARY KEY (member_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* role_function  */
CREATE TABLE role_function(
	role_id bigint(10) NOT NULL, 
    function_id bigint(10) NOT NULL, 
    CONSTRAINT PK_role_functions PRIMARY KEY (role_id, function_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* role_menu */
CREATE TABLE role_menu(
	role_id bigint(10) NOT NULL, 
    menu_id bigint(10) NOT NULL, 
    CONSTRAINT PK_role_menu PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* functions  */
CREATE TABLE functions(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    menu_id bigint(10) NOT NULL, 
    name varchar(30) NOT NULL, 
    description varchar(100), 
    CONSTRAINT PK_functions PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* menu */
CREATE TABLE menu(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    parent_id bigint(10) NOT NULL, 
    name varchar(50) NOT NULL, 
    description varchar(100), 
    sequence int, 
    CONSTRAINT PK_menu PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* member */
CREATE TABLE member(
	id bigint(10) NOT NULL AUTO_INCREMENT,
	full_id varchar(30) NOT NULL,
	login_id varchar(75) NOT NULL, 
	name varchar(75),
	email varchar(75),
	parent_id bigint(10) NOT NULL, 
	password varchar(100) NOT NULL, 
	description varchar(75),
	af_member_id varchar(30),
	address varchar(75),
	enable bit(1) NOT NULL,
	last_login_ip varchar(30),
	last_login_time varchar(30),
	email_token varchar(50) DEFAULT NULL, 
	sso varchar(30),
	oauth_token varchar(30),
    event_priority bigint DEFAULT 0, 
	phone varchar(30) NOT NULL DEFAULT '',
	deleted bit(1) NOT NULL DEFAULT false,
	CONSTRAINT PK_member PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* member_properties */
CREATE TABLE member_properties(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
	member_id bigint(10) NOT NULL, 
  english_name varchar(100) NOT NULL,
	name varchar(30) NOT NULL, 
	value varchar(50) NOT NULL , 
  fix bit(1) NOT NULL DEFAULT true,
	deleted bit(1) NOT NULL DEFAULT false, 
	CONSTRAINT PK_member__properties PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* invited_member */
CREATE TABLE invited_member(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
	member_id bigint(10) DEFAULT NULL, 
	login_id varchar(75) NOT NULL, 
	email_token varchar(50) DEFAULT NULL,
	deleted bit(1) NOT NULL,
	CONSTRAINT PK_invited_member PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* forget_pwd */
CREATE TABLE forget_pwd(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
	member_id bigint(10) DEFAULT NULL, 
	valid_time int(30) DEFAULT NULL, 
	verification_code varchar(50) DEFAULT NULL, 
	status varchar(50) DEFAULT NULL, 
	change_time datetime DEFAULT CURRENT_TIMESTAMP, 
	CONSTRAINT PK_forget_pwd PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* weblog */
CREATE TABLE weblog (
  id bigint(10) NOT NULL AUTO_INCREMENT,
  username varchar(20) DEFAULT NULL,
  host varchar(20) NOT NULL,
  method varchar(10) NOT NULL,
  path varchar(200) DEFAULT NULL,
  parameters varchar(1000) DEFAULT NULL,
  requestbody text,
  responsebody text,
  execdate datetime DEFAULT CURRENT_TIMESTAMP,
  md5 varchar(32) NOT NULL,
  PRIMARY KEY (id),
  KEY name_host_index (username,host),
  KEY index_path (path),
  KEY all_index (username,host,path,execdate)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/* rule */
CREATE TABLE rule(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    device_type_id bigint(10) NOT NULL, 
    title varchar(30) NOT NULL, 
    modes int(5) NOT NULL, 
    conditions varchar(30) NOT NULL, 
    action_type int(5) NOT NULL, 
    status varchar(30) DEFAULT NULL, 
    action_id bigint(10), 
    CONSTRAINT PK_rule PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* payload */
CREATE TABLE payload(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    device_type_id bigint(10) NOT NULL, 
    key_name varchar(30) NOT NULL, 
    definition varchar(30) DEFAULT NULL, 
    unit varchar(30) DEFAULT NULL, 
    CONSTRAINT PK_payload PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* to_do_list */
CREATE TABLE to_do_list(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    rule_id bigint(10) NOT NULL, 
    rule_conditions varchar(30) NOT NULL, 
    rule_status varchar(30) DEFAULT NULL, 
    start_time datetime DEFAULT CURRENT_TIMESTAMP, 
    finish_time datetime DEFAULT NULL, 
    finished bit(1) NOT NULL, 
    feedback varchar(100) DEFAULT NULL, 
    CONSTRAINT PK_to_do_list PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* rule_log */
CREATE TABLE rule_log(
	id bigint(10) NOT NULL AUTO_INCREMENT, 
    ip varchar(30) DEFAULT NULL, 
    member_id bigint(10), 
    device_id bigint(10) NOT NULL, 
    group_id bigint(10) NOT NULL, 
    group_name varchar(30) DEFAULT NULL, 
    rule_id bigint(10) NOT NULL, 
    rule_action_id int(5), 
    rule_conditions varchar(30) DEFAULT NULL, 
    rule_status varchar(30) DEFAULT NULL, 
    feedback varchar(100) DEFAULT NULL, 
    payload_data varchar(100) DEFAULT NULL, 
    trigger_time datetime DEFAULT CURRENT_TIMESTAMP, 
    CONSTRAINT PK_rule_log PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* sequence */
CREATE TABLE sequence(
	id int(10) NOT NULL AUTO_INCREMENT, 
	name varchar(45) DEFAULT NULL, 
	prefix_code varchar(45) DEFAULT '',
    increment int(11) DEFAULT '1',
	min_value int(11) DEFAULT '1',
	max_value int(11) DEFAULT '0',
	cur_value int(11) DEFAULT '0',
	cycle bit(1) DEFAULT b'0',
	today date DEFAULT NULL,
	type enum('1','2','3') DEFAULT NULL,
	CONSTRAINT PK_member PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* rule_device */
CREATE TABLE rule_device(
	rule_id bigint(10) NOT NULL, 
    device_id bigint(10) NOT NULL, 
    CONSTRAINT PK_rule_device PRIMARY KEY (rule_id, device_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* rule_group */
CREATE TABLE rule_group(
	rule_id bigint(10) NOT NULL, 
    group_id bigint(10) NOT NULL, 
    CONSTRAINT PK_rule_group PRIMARY KEY (rule_id, group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
 * ----------------------------------------------------------------
 * celeFish table
 * 
 */


CREATE TABLE `fish_rule_log` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(10) NOT NULL,
  `device_id` bigint(10) NOT NULL,
  `check_member_id` bigint(10) DEFAULT '0',
  `check_time` datetime DEFAULT NULL,
  `status_code` varchar(3) DEFAULT '',
  `control_code` varchar(3) NOT NULL DEFAULT '',
  `dissolved_oxygen` float(5,2) NOT NULL DEFAULT '0.00',
  `temperature` float(5,2) NOT NULL DEFAULT '0.00',
  `ph` float(5,2) NOT NULL DEFAULT '0.0',
  `alarm_type` int(11) DEFAULT '0',
  `alarm_time` datetime DEFAULT NULL,
  `aerator_open` bit(1) NOT NULL DEFAULT b'0',
  `aerator_normal` bit(1) NOT NULL DEFAULT b'0',
  `used_drug` bit(1) NOT NULL DEFAULT b'0',
  `water_quality` bit(1) NOT NULL DEFAULT b'0',
  `memo` varchar(100) NOT NULL DEFAULT '',
  `inform_manager` bit(1) NOT NULL DEFAULT b'0',
  `result` varchar(100) NOT NULL DEFAULT '',
  `result_status` varchar(50) NOT NULL DEFAULT '',
  `result_time` datetime DEFAULT NULL,
  `history` text,
  `closed` int(11) DEFAULT '0',
  `af_pro_id` varchar(50) NOT NULL DEFAULT '',
  `call_auto` bit(1) NOT NULL DEFAULT b'0',
  `call_times` int(11) DEFAULT '0',
  `call_sid` varchar(45) DEFAULT NULL,
  `call_date` datetime DEFAULT NULL,
  `call_status` varchar(45) DEFAULT '',
  `call_response` bit(1) DEFAULT b'0',
  `call_duration` int(11) DEFAULT '0',
  `call_number` varchar(45) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*針對 fish_rule_log 不需重建實直接執行
ALTER TABLE `fish_rule_log` 
ADD COLUMN `call_auto` BIT(1) NOT NULL DEFAULT 0 AFTER `af_pro_id`,
ADD COLUMN `call_times` INT(11) NULL DEFAULT 0 AFTER `call_auto`,
ADD COLUMN `call_sid` VARCHAR(45) NULL AFTER `call_times`,
ADD COLUMN `call_date` DATETIME NULL AFTER `call_sid`,
ADD COLUMN `call_status` VARCHAR(45) NULL DEFAULT '' AFTER `call_date`,
ADD COLUMN `call_response` BIT(1) NULL DEFAULT 0 AFTER `call_status`,
ADD COLUMN `call_duration` INT(11) NULL DEFAULT 0 AFTER `call_response`,
ADD COLUMN `call_number` VARCHAR(45) NULL DEFAULT '' AFTER `call_duration`;
*/

CREATE TABLE device_temp_1(
    device_id bigint(10) NOT NULL, 
    last_clear_time_ varchar(100) DEFAULT NULL, 
    this_clear_time_ varchar(100) DEFAULT NULL, 
    clear_status_ varchar(100) DEFAULT NULL, 
    last_maintained_time_ varchar(100) DEFAULT NULL, 
    this_maintained_time_ varchar(100) DEFAULT NULL, 
    maintained_status_ varchar(100) DEFAULT NULL, 
    property_id_ varchar(100) DEFAULT NULL, 
    location_ varchar(100) DEFAULT NULL, 
    keeper_ varchar(100) DEFAULT NULL, 
    closed_ varchar(100) DEFAULT NULL, 
    longitude_ varchar(100) DEFAULT NULL, 
    latitude_ varchar(100) DEFAULT NULL, 
    control_status_ varchar(100) DEFAULT NULL, 
    used_status_ varchar(100) DEFAULT NULL,
    effectivedate_ varchar(100) DEFAULT NULL, 
    enddate_ varchar(100) DEFAULT NULL, 
    oxy_limit_ varchar(100) DEFAULT NULL, 
    oxy_limit_up_ varchar(100) DEFAULT NULL, 
    oxy_limit_down1_ varchar(100) DEFAULT NULL, 
    oxy_limit_down2_ varchar(100) DEFAULT NULL, 
    dissolverValue_ varchar(100) DEFAULT NULL, 
    alertline1_ varchar(100) DEFAULT NULL, 
    alertline2_ varchar(100) DEFAULT NULL, 
    ph_ varchar(100) DEFAULT NULL, 
    temperature_ varchar(100) DEFAULT NULL, 
    salinity_ varchar(100) DEFAULT NULL, 
    atmo_ varchar(100) DEFAULT NULL, 
    channel_A_ varchar(100) DEFAULT NULL,
    status_A_ varchar(100) DEFAULT NULL,
    hasAmmeter_A_ varchar(100) DEFAULT NULL,
    ammeterType_A_ varchar(100) DEFAULT NULL,
    ammeterId_A_ varchar(100) DEFAULT NULL,
    power_A_ varchar(100) DEFAULT NULL,
    voltageUp_A_ varchar(100) DEFAULT NULL,
    voltageDown_A_ varchar(100) DEFAULT NULL,
    electricCurrentUp_A_ varchar(100) DEFAULT NULL,
    electricCurrentDown_A_ varchar(100) DEFAULT NULL,
    channel_B_ varchar(100) DEFAULT NULL,
    status_B_ varchar(100) DEFAULT NULL,
    hasAmmeter_B_ varchar(100) DEFAULT NULL,
    ammeterType_B_ varchar(100) DEFAULT NULL,
    ammeterId_B_ varchar(100) DEFAULT NULL,
    power_B_ varchar(100) DEFAULT NULL,
    voltageUp_B_ varchar(100) DEFAULT NULL,
    voltageDown_B_ varchar(100) DEFAULT NULL,
    electricCurrentUp_B_ varchar(100) DEFAULT NULL,
    electricCurrentDown_B_ varchar(100) DEFAULT NULL,
    influxDB_ varchar(100) DEFAULT NULL,
    influxTable_ varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `table_last_update` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` bit(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY(name)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8; 

insert into table_last_update (name,flag) values('fish_rule_log', true);

DELIMITER $$
CREATE TRIGGER trigger_insert_fish_rule_log AFTER INSERT ON `fish_rule_log`
FOR EACH ROW
BEGIN
  UPDATE table_last_update set flag = not flag WHERE ID=1;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER trigger_update_fish_rule_log AFTER UPDATE ON `fish_rule_log`
FOR EACH ROW
BEGIN
  UPDATE table_last_update set flag = not flag WHERE ID=1;
END $$
DELIMITER ;
