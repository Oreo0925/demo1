/* device */
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (1, 0, 'DEV0000000000001', '1號控制器', '000001', 'Celefish', '', '', 'DO1', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (2, 0, 'DEV0000000000002', '2號控制器', '000002', 'Celefish', '', '', 'DO1', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (3, 0, 'DEV0000000000003', '3號控制器', '000003', 'Celefish', '', '', 'DO2', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (4, 0, 'DEV0000000000004', '4號控制器', '000004', 'Celefish', '', '', 'DO3', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (5, 0, 'DEV0000000000005', '5號控制器', '000005', 'Celefish', '', '', 'DO4', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (6, 0, 'DEV0000000000006', '6號控制器', '000006', 'Celefish', '', '', 'DO5', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (7, 0, 'DEV0000000000007', '7號控制器', '000007', 'Celefish', '', '', 'DO5', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);
INSERT INTO device(id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (9, 0, 'DEV0000000000009', '9號控制器', '000009', 'Celefish', '', '', 'DO5', true, false, 1, 1, 1, false, 1, '2017-06-16 14:23:00', 1527592670892);

/* device_temp */
INSERT INTO device_temp(id, name, deleted) VALUES (1, 'Fish Device Template', false);

/* device_temp_dynamic_properties */
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (1, 1, 'last_clear_time', '上次清洗時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (2, 1, 'this_clear_time', '本次清洗時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (3, 1, 'clear_status', '清洗狀態', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (4, 1, 'last_maintained_time', '上次維修時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (5, 1, 'this_maintained_time', '本次清洗時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (6, 1, 'maintained_status', '維護狀態', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (7, 1, 'property_id', '財產編號(儀器編號)', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (8, 1, 'location', '存放地點', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (9, 1, 'keeper', '保管人員', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (10, 1, 'closed', '鎖定', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (11, 1, 'longitude', '經度', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (12, 1, 'latitude', '緯度', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (13, 1, 'control_status', '控制狀態', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (14, 1, 'used_status', '使用狀態', false, false);

INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (15, 1, 'effectivedate', '合同開始時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (16, 1, 'enddate', '合同結束時間', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (17, 1, 'oxy_limit_up', '溶氧警告上限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (18, 1, 'oxy_limit_down1', '溶氧警告下限1', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (19, 1, 'oxy_limit_down2', '溶氧警告下限2', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (20, 1, 'dissolverValue', '溶氧值', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (21, 1, 'alertline1', '溶氧報警線1', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (22, 1, 'alertline2', '溶氧報警線2', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (23, 1, 'ph', 'PH值', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (24, 1, 'temperature', '溫度', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (25, 1, 'salinity', '鹹度', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (26, 1, 'atmo', '大氣壓', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (27, 1, 'channel_A', '控制1參數配置', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (28, 1, 'status_A', '控制1運行狀態', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (29, 1, 'hasAmmeter_A', '控制1是否有電表', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (30, 1, 'ammeterType_A', '控制1電表型號', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (31, 1, 'ammeterId_A', '控制1電表標號', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (32, 1, 'power_A', '控制1功率', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (33, 1, 'voltageUp_A', '控制1電壓上限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (34, 1, 'voltageDown_A', '控制1電壓下限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (35, 1, 'electricCurrentUp_A', '控制1電流上限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (36, 1, 'electricCurrentDown_A', '控制1電流下限', false, false);

INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (37, 1, 'channel_B', '控制2參數配置', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (38, 1, 'status_B', '控制2運行狀態', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (39, 1, 'hasAmmeter_B', '控制2是否有電表', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (40, 1, 'ammeterType_B', '控制2電表型號', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (41, 1, 'ammeterId_B', '控制2電表標號', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (42, 1, 'power_B', '控制2功率', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (43, 1, 'voltageUp_B', '控制2電壓上限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (44, 1, 'voltageDown_B', '控制2電壓下限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (45, 1, 'electricCurrentUp_B', '控制2電流上限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (46, 1, 'electricCurrentDown_B', '控制2電流下限', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (47, 1, 'influxDB', 'InfluxDB', false, false);
INSERT INTO device_temp_dynamic_properties(id, device_temp_id, english_name, name, deleted, fix) VALUES (48, 1, 'influxTable', 'InfluxTable', false, false);

/* device_static_properties */
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (1, 'id', 'ID', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (2, 'parent_id', '母設備', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (3, 'full_id', '設備標示(ID)', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (4, 'name', '設備名稱', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (5, 'identifier', '識別碼(設備標示)', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (6, 'rule_note', '告警狀態', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (7, 'rule_condition', '告警線', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (8, 'description', '備註', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (9, 'enabled', '啟用', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (10, 'automatic', '自動', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (11, 'status', '設備狀態', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (12, 'device_type_id', '設備類型', true);
INSERT INTO device_static_properties(id, english_name, name,  fix) VALUES (13, 'creator', '創件者', true);

/* device_temp_1 */
INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
1, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '5', '2',
'3', '15', '9', '10', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
2, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
3, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
4, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
5, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
6, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

INSERT INTO device_temp_1(device_id, last_clear_time_, this_clear_time_, clear_status_, last_maintained_time_,
this_maintained_time_, maintained_status_, property_id_, location_, keeper_,
closed_, longitude_, latitude_, control_status_, used_status_,
effectivedate_, enddate_, oxy_limit_, oxy_limit_up_, oxy_limit_down1_,
oxy_limit_down2_, dissolverValue_, alertline1_, alertline2_, ph_, 
temperature_, salinity_, atmo_, channel_A_, status_A_,
hasAmmeter_A_, ammeterType_A_, ammeterId_A_, power_A_, voltageUp_A_,
voltageDown_A_, electricCurrentUp_A_, electricCurrentDown_A_, channel_B_, status_B_,
hasAmmeter_B_, ammeterType_B_, ammeterId_B_, power_B_, voltageUp_B_,
voltageDown_B_, electricCurrentUp_B_, electricCurrentDown_B_, influxDB_, influxTable_) VALUES (
7, '2017-05-16 14:23:00', '2017-06-16 14:23:00', '已完成' , '2017-05-16 14:23:00', 
'2017-06-16 14:23:00', '已完成', 'SEOC1980-4099', '公司', '庫管', 
'遠程鎖定', '120.122317', '30.303244', '0', '0',
'2018-06-01', '2017-05-16', '15', '20', '10',
'5', '15', '10', '5', '6',
'16', '0', '101.3', '控制1參數配置', '1',
'true', 'DSM3', 'D001', '1V', '5V',
'5V', '1A', '1A', '控制2參數配置', '2',
'false', 'DSM3', 'D002', '1V', '5V',
'5V', '1A', '1A', 'celefish', 'celefish');

/* device_type */
INSERT INTO device_type (id, name, protocal, frequency) VALUES (1, 'KD326', 0, '');

/* cn_group */
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (1, 0, '全區', '地址', '慶魚堂Admin的Group', 1, 3, false);
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (2, 1, '浙江省', '地址', '慶魚堂Admin的Group', 1, 3, false);
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (3, 2, '浙江A漁塘', '地址', '慶魚堂Admin的Group', 1, 3, false);
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (4, 2, '浙江B漁塘', '地址', '慶魚堂Admin的Group', 1, 3, false);
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (5, 2, '浙江C漁塘', '地址', '慶魚堂Admin的Group', 1, 3, false);
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (6, 3, '青魚1號糖', '中國浙江省金华市义乌市鸽溪路', '慶魚堂Admin的Group', 1, 3, false);
/*
INSERT INTO cn_group(id, parent_id, name, location, description, creator, map_type, deleted) VALUES (6, 2, '找不到的裝置', '地址', '慶魚堂Admin的Group', 1, 3, true);
*/

/* device_groups */
INSERT INTO device_group(device_id, group_id) VALUES (1, 6);
INSERT INTO device_group(device_id, group_id) VALUES (2, 6);
INSERT INTO device_group(device_id, group_id) VALUES (3, 6);
INSERT INTO device_group(device_id, group_id) VALUES (4, 6);
INSERT INTO device_group(device_id, group_id) VALUES (5, 6);
INSERT INTO device_group(device_id, group_id) VALUES (6, 6);
INSERT INTO device_group(device_id, group_id) VALUES (7, 6);
INSERT INTO device_group(device_id, group_id) VALUES (9, 6);

/* group_properties */
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (1, 6, 'owner', '管家', '李四', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (2, 6, 'ownerPhone', '管家電話', '13300120200,13300120201,13300120202', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (3, 6, 'ownerManager', '養殖管家', '王五', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (4, 6, 'ownerManagerPhone', '養殖管家電話', '13300120200,13300120201,13300120202', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (5, 6, 'species', '魚種', '青魚', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (6, 6, 'longitude', '經度', '120.122317', 1, 0);
INSERT INTO group_properties(id, group_id, english_name, name, value, fix, deleted) VALUES (7, 6, 'latitude', '緯度', '30.303244', 1, 0);


/* role */
INSERT INTO role(id, name, description, creator, priority) VALUES (1, 'ADMIN', '慶漁堂最大權限管理者', 1, 0);
INSERT INTO role(id, name, description, creator, priority) VALUES (2, '浙江省管理人', '浙江省管理人', 1, 0);
INSERT INTO role(id, name, description, creator, priority) VALUES (3, '浙江省A漁塘', '浙江省A漁塘管理人', 1, 0);
INSERT INTO role(id, name, description, creator, priority) VALUES (4, '浙江省B漁塘', '浙江省B漁塘管理人', 1, 0);
INSERT INTO role(id, name, description, creator, priority) VALUES (5, '浙江省C漁塘', '浙江省C漁塘管理人', 1, 0);
INSERT INTO role(id, name, description, creator, priority) VALUES (6, '青魚1號糖', '青魚1號塘值班人', 1, 0);

/* rule_device */
INSERT INTO rule_device(rule_id, device_id) VALUES (1, 1);

/* rule_group */
INSERT INTO rule_group(rule_id, group_id) VALUES (1, 1);

/* menu */
INSERT INTO menu(parent_id, name, description, sequence) VALUES (0, '電子廣告牌', '電子廣告牌設定', 0);

/* role_menu */
INSERT INTO role_menu(role_id, menu_id) VALUES (1, 1);

/* fish_rule_log */
/* 已完成 */
INSERT INTO fish_rule_log(group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, 
aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed, af_pro_id)
VALUES (3, 3, 1, '2017-06-16 14:23:00', 1, 111, 15.01, 15.01, 15.01, 2, '2017-06-16 14:23:00', 
true, true, true, true, 'memo', true, 1, 2, '2017-06-16 14:23:00', '[{"resultStatus":1,"resultTime":"2018-06-14 10:53:55.0"},{"resultStatus":1,"resultTime":"2018-06-14 10:54:28.0"}]', 1, '');

INSERT INTO fish_rule_log(group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, 
aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed, af_pro_id)
VALUES (4, 4, 1, '2017-06-16 14:23:00', 1, 111, 15.01, 15.01, 15.01, 2, '2017-06-16 14:23:00', 
true, true, true, true, 'memo', true, 1, 2, '2017-06-16 14:23:00', '[{"resultStatus":1,"resultTime":"2018-06-14 10:53:55.0"},{"resultStatus":1,"resultTime":"2018-06-14 10:54:28.0"}]', 2, 'DIS000001');

INSERT INTO fish_rule_log(group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, 
aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed, af_pro_id)
VALUES (5, 5, 1, '2017-06-16 14:23:00', 1, 111, 15.01, 15.01, 15.01, 2, '2017-06-16 14:23:00', 
true, true, true, true, 'memo', true, 1, 2, '2017-06-16 14:23:00', '[{"resultStatus":1,"resultTime":"2018-06-14 10:53:55.0"},{"resultStatus":1,"resultTime":"2018-06-14 10:54:28.0"}]', 2, 'DIS000002');


/* 此Log未被領取 */
INSERT INTO fish_rule_log(group_id, device_id, alarm_type, alarm_time, closed, check_member_id) 
VALUES (5, 3, 1, '2017-06-16 14:23:00', false, 0);
INSERT INTO fish_rule_log(group_id, device_id, alarm_type, alarm_time, closed, check_member_id) 
VALUES (5, 4, 1, '2017-06-17 14:23:00', false, 0);
INSERT INTO fish_rule_log(group_id, device_id, alarm_type, alarm_time, closed, check_member_id) 
VALUES (5, 3, 2, '2017-06-18 14:23:00', false, 0);
INSERT INTO fish_rule_log(group_id, device_id, alarm_type, alarm_time, closed, check_member_id) 
VALUES (5, 4, 2, '2017-06-19 14:23:00', false, 0);
/* 此Log已領取但未完成 */
INSERT INTO fish_rule_log(group_id, device_id, alarm_type, alarm_time, closed, check_member_id, check_time, history) 
VALUES (5, 5, 2, '2018-06-26 15:37:00', false, 1, '2017-06-16 14:23:00', '[{"resultStatus":1,"resultTime":"2018-06-14 10:53:55.0"},{"resultStatus":1,"resultTime":"2018-06-14 10:54:28.0"}]');


/* member */
INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (1, 'MEM0000000000001', 'duty1', '慶漁堂管理人', 'celefish@gmail.com', 0, '202cb962ac59075b964b07152d234b70', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'success', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (2, 'MEM0000000000002', 'duty2', '浙江省管理人', 'celefish@gmail.com', 1, '202cb962ac59075b964b07152d234b70', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'success', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (3, 'MEM0000000000003', 'duty3', '浙江省A管理人', 'celefish@gmail.com', 1, '202cb962ac59075b964b07152d234b70', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'success', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (4, 'MEM0000000000004', 'duty4', '浙江省B慶漁堂管理人', 'celefish@gmail.com', 1, '202cb962ac59075b964b07152d234b70', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'success', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (5, 'MEM0000000000005', 'duty5', '浙江省C慶漁堂管理人', 'celefish@gmail.com', 1, '202cb962ac59075b964b07152d234b70', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'emailToken', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (6, 'MEM0000000000006', 'adminisatrtor6', '青魚1號塘值班人1', 'celefish@gmail.com', 1, '123', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'emailToken', 'sso', '');

INSERT INTO member(id, full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token) 
VALUES (7, 'MEM0000000000007', 'adminisatrtor7', '青魚1號塘值班人2', 'celefish@gmail.com', 1, '123', '描述', 'MEM0000000000001', '地址', true, '192.168.5.10', '2017-05-16 14:23:00', 'emailToken', 'sso', '');


/* member_properties */
INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(1, 1, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(2, 2, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(3, 3, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(4, 4, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(5, 5, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(6, 6, "suspend", "暫停", "false", true, false);

INSERT INTO member_properties(id, member_id, english_name, name, value, fix, deleted)
values(7, 7, "suspend", "暫停", "false", true, false);

/* member_role */
INSERT INTO member_role(role_id, member_id) VALUES (1, 1);
INSERT INTO member_role(role_id, member_id) VALUES (2, 2);
INSERT INTO member_role(role_id, member_id) VALUES (3, 3);
INSERT INTO member_role(role_id, member_id) VALUES (4, 4);
INSERT INTO member_role(role_id, member_id) VALUES (5, 5);
INSERT INTO member_role(role_id, member_id) VALUES (6, 6);
INSERT INTO member_role(role_id, member_id) VALUES (6, 7);

/* role_group */
INSERT INTO role_group(role_id, group_id) VALUES (1, 1);
INSERT INTO role_group(role_id, group_id) VALUES (2, 1);
INSERT INTO role_group(role_id, group_id) VALUES (3, 1);
INSERT INTO role_group(role_id, group_id) VALUES (4, 1);
INSERT INTO role_group(role_id, group_id) VALUES (5, 1);
INSERT INTO role_group(role_id, group_id) VALUES (2, 2);
INSERT INTO role_group(role_id, group_id) VALUES (3, 3);
INSERT INTO role_group(role_id, group_id) VALUES (4, 4);
INSERT INTO role_group(role_id, group_id) VALUES (1, 5);
INSERT INTO role_group(role_id, group_id) VALUES (2, 5);
INSERT INTO role_group(role_id, group_id) VALUES (3, 5);
INSERT INTO role_group(role_id, group_id) VALUES (4, 5);
INSERT INTO role_group(role_id, group_id) VALUES (5, 5);
INSERT INTO role_group(role_id, group_id) VALUES (6, 6);
