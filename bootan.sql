DROP TABLE IF EXISTS `tbl_student`;
CREATE TABLE `tbl_student` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_dict`;
CREATE TABLE `tbl_dict` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT "",
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_dict_data`;
CREATE TABLE `tbl_dict_data` (
  `id` varchar(255) NOT NULL,
  `dict_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL DEFAULT "",
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `department_type` int(11) NOT NULL default '0',
  `description` varchar(255) NOT NULL default '',
  `is_default` int(11) NOT NULL DEFAULT 0,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_role_permission`;
CREATE TABLE `tbl_role_permission` (
  `id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `permission_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) NOT NULL default '0',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL default '',
  `nick_name` varchar(255) NOT NULL default '',
  `avatar` varchar(1023) NOT NULL default '',
  `description` varchar(255) NOT NULL default '',
  `sex` varchar(255) NOT NULL default '',
  `address` varchar(255) NOT NULL default '',
  `mobile` varchar(255) NOT NULL default '',
  `remark` varchar(255) NOT NULL default '',
  `department_id` varchar(255) NOT NULL default '',
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_user_role`;
CREATE TABLE `tbl_user_role` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_role_department`;
CREATE TABLE `tbl_role_department` (
  `id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `department_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) NOT NULL default '0',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL default '',
  `component` varchar(255) NOT NULL default '',
  `path` varchar(255) NOT NULL default '',
  `title` varchar(255) NOT NULL default '',
  `icon` varchar(255) NOT NULL default '',
  `level` int(11) NOT NULL DEFAULT "1",
  `button_type` varchar(255) NOT NULL default '',
  `redirect_url` varchar(255) NOT NULL default '',
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_department`;
CREATE TABLE `tbl_department` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) NOT NULL default '0',
  `name` varchar(255) NOT NULL,
  `parent_name` varchar(255) NOT NULL default '',
  `description` varchar(255) NOT NULL default '',
  `head` varchar(255) NOT NULL default '',
  `vice_head` varchar(255) NOT NULL default '',
  `is_parent` int(11) NOT NULL DEFAULT 0,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_log`;
CREATE TABLE `tbl_log` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255)  NOT NULL DEFAULT "",
  `request_url` varchar(255) NOT NULL default '',
  `request_type` varchar(255) NOT NULL default '',
  `request_param` text,
  `username` varchar(255) NOT NULL default '',
  `ip` varchar(255) NOT NULL default '',
  `ip_info` varchar(255) NOT NULL default '',
  `cost_time` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_file`;
CREATE TABLE `tbl_file` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT "",
  `size` int(11) NOT NULL default '0',
  `url` varchar(255) NOT NULL default '',
  `file_key` varchar(255) NOT NULL default '',
  `content_type` varchar(255) NOT NULL default '',
  `location` int(11) NOT NULL default '1',
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_message`;
CREATE TABLE `tbl_message` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT "",
  `content` text,
  `new_auto_send` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_message_state`;
CREATE TABLE `tbl_message_state` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT "",
  `user_id` varchar(255) NOT NULL,
  `message_id` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT "1",
  `sort_order` int(11) NOT NULL DEFAULT "50",
  `status` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL DEFAULT '',
  `updated_by` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `tbl_permission` VALUES ('379506240705630261', '0', 'bootan', '', '', '', 'Bootan系统', 'ios-aperture', '0', '', '', '1', '50', '1', '2019-09-17 04:00:23', '2019-10-10 03:14:34', '', '');
INSERT INTO `tbl_permission` VALUES ('379516240705630351', '379506240705630261', 'user-index', '', 'Main', '/user', '系统管理', 'ios-settings', '1', '', '', '1', '50', '1', '2019-09-17 04:05:54', '2019-10-10 15:37:22', '', '');
INSERT INTO `tbl_permission` VALUES ('379536240705630122', '379516240705630351', 'normal-user-manage', '', 'sys/user/index', '/user/index', '用户管理', 'ios-person', '2', '', '', '1', '50', '1', '2019-09-17 04:12:41', '2019-10-11 21:33:47', '', '');
INSERT INTO `tbl_permission` VALUES ('379546240705630128', '379536240705630122', 'view-normal-user-manage', '', '', '/user/index', '查看', '', '3', '查看权限(view)', '', '1', '50', '1', '2019-09-17 04:13:31', '2019-10-10 03:17:02', '', '');
INSERT INTO `tbl_permission` VALUES ('379556240705630322', '379506240705630261', 'number', '', 'Main', '/bootan/me111', '111', 'ios-add', '2', '', '', '1', '50', '1', '2019-09-18 02:52:30', '2019-10-10 19:25:14', '', '');
INSERT INTO `tbl_permission` VALUES ('379706240705630208', '379536240705630122', 'user-save', '', '', '/user/save', '新增', '', '3', '', '', '1', '50', '1', '2019-10-10 03:18:08', '2019-10-10 19:23:46', '', '');
INSERT INTO `tbl_permission` VALUES ('379707112927924224', '379516240705630351', 'role-index', '', 'sys/role/index', 'sys/role/index', '角色管理', 'ios-people', '2', '', '', '0', '50', '1', '2019-10-10 03:21:36', '2019-10-10 04:50:48', '', '');
INSERT INTO `tbl_permission` VALUES ('379724436808536064', '379506240705630261', 'monitor', '', 'Main', '/monitor', '系统监控', 'ios-podium', '1', '', '', '0', '51', '1', '2019-10-10 04:30:26', '2019-10-10 04:30:26', '', '');
INSERT INTO `tbl_permission` VALUES ('379724707043348480', '0', 'bootan-frontend', '', '', '', 'Bootan Frontend', 'md-book', '0', '', '', '1', '51', '1', '2019-10-10 04:31:31', '2019-10-10 04:31:37', '', '');
INSERT INTO `tbl_permission` VALUES ('379726775246262272', '379724436808536064', 'bootan-redis-index', '', 'monitor/redis/index', '/bootan/redis/index', 'Redis监控', 'ios-aperture', '2', '', 'http://192.168.0.15:8088/swagger-ui.html', '0', '50', '1', '2019-10-10 04:39:44', '2019-10-29 12:13:29', '', '');
INSERT INTO `tbl_permission` VALUES ('380338536295043072', '379516240705630351', 'permission-index', '', 'sys/permission/index', '/permission/index', '权限管理', 'ios-apps', '2', '', '', '0', '51', '1', '2019-10-11 21:10:39', '2019-10-11 21:10:57', '', '');
INSERT INTO `tbl_permission` VALUES ('380340671619403776', '379516240705630351', 'dict-index', '', 'sys/dict/index', '/dict/index', '字典管理', 'ios-book', '2', '', '', '0', '50', '1', '2019-10-11 21:19:08', '2019-10-11 21:19:08', '', '');
INSERT INTO `tbl_permission` VALUES ('380341055947673600', '379516240705630351', '/department/index', '', 'sys/department/index', '/department/index', '部门管理', 'ios-calculator', '2', '', '', '0', '50', '1', '2019-10-11 21:20:40', '2019-10-11 21:20:40', '', '');
INSERT INTO `tbl_permission` VALUES ('386521283522859008', '379724436808536064', '/sys', '', 'sys/monitor/monitor', '/sys', 'Swagger监控', 'ios-alarm', '2', '', 'http://192.168.0.15:8088/swagger-ui.html', '0', '50', '1', '2019-10-28 22:38:41', '2019-10-29 01:04:50', '', '');

INSERT INTO `tbl_role` VALUES ('380419680122310655', '超级管理员', '1', '超级管理员', '0', '1', '50', '1', '2019-09-18 23:27:23', '2019-10-12 02:52:37', '', '');

INSERT INTO `tbl_role_permission` VALUES ('386522395722256384', '380419680122310655', '379506240705630261', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395760005121', '380419680122310655', '379516240705630351', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395801948160', '380419680122310655', '379536240705630122', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395839696897', '380419680122310655', '379546240705630128', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395881639936', '380419680122310655', '379556240705630322', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395919388672', '380419680122310655', '379706240705630208', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395957137409', '380419680122310655', '379707112927924224', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522395994886145', '380419680122310655', '379724436808536064', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522396032634881', '380419680122310655', '379724707043348480', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522396074577920', '380419680122310655', '379726775246262272', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522396112326656', '380419680122310655', '380338536295043072', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522396150075393', '380419680122310655', '380340671619403776', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');
INSERT INTO `tbl_role_permission` VALUES ('386522396187824129', '380419680122310655', '380341055947673600', '', '1', '50', '1', '2019-10-28 22:43:06', '2019-10-28 22:43:06', '', '');

INSERT INTO `tbl_user` VALUES ('379546240705631267', '', 'admin', '$2a$10$7TCWckRONpj9ocjVaWoH5uBGV36amoWw3bTbayl0aoL25FLIgX9ka', '', '', '', '', '', '', '', '', '', '', '1', '50', '1', '2019-09-06 04:57:32', '2019-09-17 09:55:17', '', '');

INSERT INTO `tbl_user_role` VALUES ('378546240705631285', '379546240705631267', '380419680122310655', '', '1', '50', '1', '2019-09-19 23:22:16', '2019-09-19 23:22:16', '', '');

INSERT INTO `tbl_dict` VALUES ('372605411162139628', 'sex', '性别', '1', '50', '1', '2019-09-15 22:18:11', '2019-09-15 22:18:11', '', '');
INSERT INTO `tbl_dict` VALUES ('372605421162139653', 'permission_button_type', '按钮权限', '1', '50', '1', '2019-09-17 22:45:51', '2019-09-17 22:45:51', '', '');

INSERT INTO `tbl_dict_data` VALUES ('372655121162139612', '372605411162139628', '男', '1', '1', '50', '1', '2019-09-16 10:36:33', '2019-09-16 10:36:33', '', '');
INSERT INTO `tbl_dict_data` VALUES ('372655121162139613', '372605411162139628', '女', '2', '1', '51', '1', '2019-09-16 10:36:33', '2019-09-16 10:36:33', '', '');
INSERT INTO `tbl_dict_data` VALUES ('372655121162139614', '372605411162139628', '保密', '3', '1', '52', '1', '2019-09-16 10:36:33', '2019-09-16 10:36:33', '', '');


INSERT INTO `tbl_dict_data` VALUES ('372655421162139623', '372605421162139653', '查看权限(index)', 'index', '1', '50', '1', '2019-09-17 22:46:47', '2019-09-22 23:34:14', '', '');
INSERT INTO `tbl_dict_data` VALUES ('372655421162139624', '372605421162139653', '新增权限(save)', 'save', '1', '51', '1', '2019-09-17 22:46:47', '2019-09-22 23:34:14', '', '');
INSERT INTO `tbl_dict_data` VALUES ('372655421162139625', '372605421162139653', '新增权限(update)', 'update', '1', '52', '1', '2019-09-17 22:46:47', '2019-09-22 23:34:14', '', '');
INSERT INTO `tbl_dict_data` VALUES ('372655421162139626', '372605421162139653', '删除权限(delete)', 'delete', '1', '53', '1', '2019-09-17 22:46:47', '2019-09-22 23:34:14', '', '');

INSERT INTO `tbl_department` VALUES ('372655422162139231', '0', '技术部', '一级部门', '', '', '', '1', '1', '50', '1', '2019-09-16 20:29:30', '2019-09-26 05:10:37', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162139232', '372655422162139231', 'vue', '技术部', '', '', '', '0', '1', '50', '1', '2019-09-16 20:30:19', '2019-09-28 22:35:29', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162139233', '372655422162139231', 'java', '技术部', '', '', '', '0', '1', '50', '1', '2019-09-16 20:30:19', '2019-09-28 22:35:29', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162149256', '0', '销售部', '一级部门', '', '', '', '1', '1', '50', '1', '2019-09-16 20:38:21', '2019-09-26 05:10:37', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162153215', '372655422162149256', '销售1部', '销售部', '', '', '', '0', '1', '50', '1', '2019-09-16 20:38:37', '2019-09-26 05:41:28', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162153216', '372655422162149256', '销售2部', '销售部', '', '', '', '0', '1', '50', '1', '2019-09-16 20:38:42', '2019-09-26 05:41:28', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162156722', '0', '行政部', '一级部门', '', '', '', '0', '1', '50', '1', '2019-09-16 20:39:06', '2019-09-26 06:23:12', '', '');
INSERT INTO `tbl_department` VALUES ('372655422162156723', '0', '财务部', '一级部门', '', '', '', '0', '1', '50', '1', '2019-09-17 03:05:09', '2019-09-26 05:10:37', '', '');
