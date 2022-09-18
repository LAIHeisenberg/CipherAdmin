-- *********************************
-- 2022-09-18  laiyz
INSERT INTO seckms.sys_permission ( pid, sub_count, type, title, name, component, menu_sort, icon, path, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time) VALUES ( 3, 0, 1, '恢复', '', 'log/recover', 4, 'sysconf', '/logrecover', null, false, false, 'klms:log', null, null, '2022-09-05 07:48:27', '2022-09-18 12:35:24');
update sys_permission set sub_count = 4 where id = 3;
insert into sys_role_permission (role_id, permission_id) value (2,119);
update sys_permission set menu_sort = 3 where id =19;
update sys_permission set permission = 'klms:log' where id in (17,18,19,119);

-- *********************************




