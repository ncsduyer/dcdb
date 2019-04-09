/*
Navicat MySQL Data Transfer

Source Server         : 140.246.153.214
Source Server Version : 50724
Source Host           : 140.246.153.214:3306
Source Database       : dcdb

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-04-09 17:38:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code_dbinfo
-- ----------------------------
DROP TABLE IF EXISTS `code_dbinfo`;
CREATE TABLE `code_dbinfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '别名',
  `db_driver` varchar(100) NOT NULL COMMENT '数据库驱动',
  `db_url` varchar(200) NOT NULL COMMENT '数据库地址',
  `db_user_name` varchar(100) NOT NULL COMMENT '数据库账户',
  `db_password` varchar(100) NOT NULL COMMENT '连接密码',
  `db_type` varchar(10) DEFAULT NULL COMMENT '数据库类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='数据库链接信息';

-- ----------------------------
-- Records of code_dbinfo
-- ----------------------------

-- ----------------------------
-- Table structure for copy_record_notice
-- ----------------------------
DROP TABLE IF EXISTS `copy_record_notice`;
CREATE TABLE `copy_record_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `join_id` int(11) DEFAULT NULL COMMENT '关联主键',
  `json_content` text,
  `content` text COMMENT '内容',
  `now_status` int(11) DEFAULT NULL COMMENT '当前状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `tel` varchar(22) DEFAULT NULL COMMENT '电话',
  `sender` varchar(255) DEFAULT NULL COMMENT '接收人',
  `sender_id` int(11) DEFAULT NULL COMMENT '触发人id',
  `send_status` int(11) DEFAULT '0' COMMENT '接收状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抄送记录表';

-- ----------------------------
-- Records of copy_record_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '1', '0', '[0],', '区委', '区委', '', null);
INSERT INTO `sys_dept` VALUES ('2', '5', '1', '[0],[1],', '区委办公室', '区委办公室', '', '1');
INSERT INTO `sys_dept` VALUES ('3', '5', '1', '[0],[1],', '区城管理局', '区城管理局', '', '1');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `code` varchar(255) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '0', '0', '性别', null, 'sys_sex');
INSERT INTO `sys_dict` VALUES ('2', '1', '1', '男', null, '1');
INSERT INTO `sys_dict` VALUES ('3', '2', '1', '女', null, '2');
INSERT INTO `sys_dict` VALUES ('4', '0', '0', '状态', null, 'sys_state');
INSERT INTO `sys_dict` VALUES ('5', '1', '4', '启用', null, '1');
INSERT INTO `sys_dict` VALUES ('6', '2', '4', '禁用', null, '2');
INSERT INTO `sys_dict` VALUES ('7', '0', '0', '账号状态', null, 'account_state');
INSERT INTO `sys_dict` VALUES ('8', '1', '7', '启用', null, '1');
INSERT INTO `sys_dict` VALUES ('9', '2', '7', '冻结', null, '2');
INSERT INTO `sys_dict` VALUES ('10', '3', '7', '已删除', null, '3');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录记录';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  `menutype` int(10) DEFAULT '1' COMMENT '菜单所属端',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1101321546003185673 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '#', '4', '1', '1', null, '1', '1', '1');
INSERT INTO `sys_menu` VALUES ('106', 'mgr', '0', '[0],', '用户管理', '', '/mgr', '1', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('131', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('141', 'notice', 'system', '[0],[system],', '通知管理', null, '/notice', '9', '2', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('142', 'notice_add', 'notice', '[0],[system],[notice],', '添加通知', null, '/notice/add', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('143', 'notice_update', 'notice', '[0],[system],[notice],', '修改通知', null, '/notice/update', '2', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('144', 'notice_delete', 'notice', '[0],[system],[notice],', '删除通知', null, '/notice/delete', '3', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('145', 'hello', '0', '[0],', '通知', 'fa-rocket', '/notice/hello', '1', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('148', 'code', '0', '[0],', '代码生成', 'fa-code', '/code', '3', '1', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('149', 'api_mgr', '0', '[0],', '接口文档', 'fa-leaf', '/swagger-ui.html', '2', '1', '1', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', '5', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', '6', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1051749916533174273', 'workType', '0', '[0],', '督查类型管理', '', '/apiworkType', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051749916537368578', 'workType_list', 'workType', '[0],[workType],', '督查类型管理列表', '', '/apiworkType/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051749916537368579', 'workType_add', 'workType', '[0],[workType],', '督查类型管理添加', '', '/apiworkType/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051749916537368580', 'workType_update', 'workType', '[0],[workType],', '督查类型管理更新', '', '/apiworkType/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051749916537368581', 'workType_delete', 'workType', '[0],[workType],', '督查类型管理删除', '', '/apiworkType/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051749916537368582', 'workType_detail', 'workType', '[0],[workType],', '督查类型管理详情', '', '/apiworkType/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478594', 'leadership', '0', '[0],', '领导列表', '', '/apileadership', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478595', 'leadership_list', 'leadership', '[0],[leadership],', '领导列表列表', '', '/apileadership/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478596', 'leadership_add', 'leadership', '[0],[leadership],', '领导列表添加', '', '/apileadership/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478597', 'leadership_update', 'leadership', '[0],[leadership],', '领导列表更新', '', '/apileadership/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478598', 'leadership_delete', 'leadership', '[0],[leadership],', '领导列表删除', '', '/apileadership/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751238074478599', 'leadership_detail', 'leadership', '[0],[leadership],', '领导列表详情', '', '/apileadership/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968160198657', 'company', '0', '[0],', '督查责任单位管理', '', '/apicompany', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968168587266', 'company_list', 'company', '[0],[company],', '督查责任单位管理列表', '', '/apicompany/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968168587267', 'company_add', 'company', '[0],[company],', '督查责任单位管理添加', '', '/apicompany/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968168587268', 'company_update', 'company', '[0],[company],', '督查责任单位管理更新', '', '/apicompany/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968168587269', 'company_delete', 'company', '[0],[company],', '督查责任单位管理删除', '', '/apicompany/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1051751968168587270', 'company_detail', 'company', '[0],[company],', '督查责任单位管理详情', '', '/apicompany/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094722', 'meeting', '0', '[0],', '会议管理', '', '/meeting', '99', '1', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094723', 'meeting_list', 'meeting', '[0],[meeting],', '会议管理列表', '', '/meeting/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094724', 'meeting_add', 'meeting', '[0],[meeting],', '会议管理添加', '', '/meeting/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094725', 'meeting_update', 'meeting', '[0],[meeting],', '会议管理更新', '', '/meeting/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094726', 'meeting_delete', 'meeting', '[0],[meeting],', '会议管理删除', '', '/meeting/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662491512094727', 'meeting_detail', 'meeting', '[0],[meeting],', '会议管理详情', '', '/meeting/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280961', 'meetingSituation', '0', '[0],', '会议督查管理', '', '/meetingrec', '99', '1', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280962', 'meetingrec_list', 'meetingrec', '[0],[meetingrec],', '会议督查管理列表', '', '/meetingrec/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280963', 'meetingrec_add', 'meetingrec', '[0],[meetingrec],', '会议督查管理添加', '', '/meetingrec/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280964', 'meetingrec_update', 'meetingrec', '[0],[meetingrec],', '会议督查管理更新', '', '/apimeetingrec/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280965', 'meetingrec_delete', 'meetingrec', '[0],[meetingrec],', '会议督查管理删除', '', '/meetingrec/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1054662678410280966', 'meetingrec_detail', 'meetingrec', '[0],[meetingrec],', '会议督查管理详情', '', '/meetingrec/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626000658433', 'versionUpgrade', 'system', '[0],[system],', '版本控制', '', '/versionUpgrade', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626009047041', 'versionUpgrade_list', 'versionUpgrade', '[0],[system],[versionUpgrade],', '版本控制列表', '', '/versionUpgrade/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626009047042', 'versionUpgrade_add', 'versionUpgrade', '[0],[system],[versionUpgrade],', '版本控制添加', '', '/versionUpgrade/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626009047043', 'versionUpgrade_update', 'versionUpgrade', '[0],[system],[versionUpgrade],', '版本控制更新', '', '/versionUpgrade/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626009047044', 'versionUpgrade_delete', 'versionUpgrade', '[0],[system],[versionUpgrade],', '版本控制删除', '', '/versionUpgrade/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1055388626009047045', 'versionUpgrade_detail', 'versionUpgrade', '[0],[system],[versionUpgrade],', '版本控制详情', '', '/versionUpgrade/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1062589527937323015', 'report', '0', '[0],', '综合报表统计', '', '/report', '99', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1062589527937323016', 'fankui', 'assignWork', '[0],[assignWork],', '督查督办办结', '', '/apiassignWork/fankui', '99', '2', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436098', 'appMenu', 'system', '[0],[system],', 'app菜单管理', '', '/appMenu', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436099', 'appMenu_list', 'appMenu', '[0],[system],[appMenu],', 'app菜单管理列表', '', '/appMenu/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436100', 'appMenu_add', 'appMenu', '[0],[system],[appMenu],', 'app菜单管理添加', '', '/appMenu/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436101', 'appMenu_update', 'appMenu', '[0],[system],[appMenu],', 'app菜单管理更新', '', '/appMenu/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436102', 'appMenu_delete', 'appMenu', '[0],[system],[appMenu],', 'app菜单管理删除', '', '/appMenu/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065067021202436103', 'appMenu_detail', 'appMenu', '[0],[system],[appMenu],', 'app菜单管理详情', '', '/appMenu/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065176202181935112', 'banli', 'assignWork', '[0],[assignWork],', '督查督办办理', '', '/apiassignWork/banli', '1', '2', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635777', 'appNotice', 'system', '[0],[system],', 'app消息通知', '', '/appNotice', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635778', 'appNotice_list', 'appNotice', '[0],[system],[appNotice],', 'app消息通知列表', '', '/appNotice/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635779', 'appNotice_add', 'appNotice', '[0],[system],[appNotice],', 'app消息通知添加', '', '/appNotice/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635780', 'appNotice_update', 'appNotice', '[0],[system],[appNotice],', 'app消息通知更新', '', '/appNotice/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635781', 'appNotice_delete', 'appNotice', '[0],[system],[appNotice],', 'app消息通知删除', '', '/appNotice/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635782', 'appNotice_detail', 'appNotice', '[0],[system],[appNotice],', 'app消息通知详情', '', '/appNotice/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635783', 'qwhysearch', '0', '[0],', '会议查询', '', '/wqhy', '99', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635784', 'qwgwsearch', '0', '[0],', '公文查询', '', '/qwgw', '99', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635785', 'xxsbsearch', '0', '[0],', '信息查询', '', '/xxsb', '99', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635786', 'qwhyadd', '0', '[0],', '会议新建', '', '/zhcx', '99', '1', '0', null, '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635787', 'status1', '0', '[0],', 'APP督办事项-新建操作', '', '/apiassignWork/add', '99', '1', '0', null, '1', null, '3');
INSERT INTO `sys_menu` VALUES ('1065491422498635788', 'status2', '0', '[0],', 'APP督办事项-反馈操作', '', '/apiassignWork/update', '99', '1', '0', '', '1', null, '3');
INSERT INTO `sys_menu` VALUES ('1065491422498635789', 'status3', '0', '[0],', 'APP督办事项-督办操作', '', '/apiassignWork/update', '99', '1', '0', '', '1', null, '3');
INSERT INTO `sys_menu` VALUES ('1065491422498635790', 'status4', '0', '[0],', 'APP督办事项-归档操作', '', '/apiassignWork/update', '99', '1', '0', '', '1', null, '3');
INSERT INTO `sys_menu` VALUES ('1065491422498635791', 'taskadd', '0', '[0],', '督办新建', '', '/apiassignWork', '99', '1', '0', '', '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635792', 'taskdeal', '0', '[0],', '督办待办', '', '/apiassignWork', '99', '1', '0', '', '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1065491422498635793', 'tasksearch', '0', '[0],', '督办查询', '', '/apiassignWork', '99', '1', '0', '', '1', null, '1');
INSERT INTO `sys_menu` VALUES ('1069071520187330562', 'report_list', 'report', '[0],[report],', '报表统计列表', '', '/report/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1069071520187330563', 'report_add', 'report', '[0],[report],', '报表统计添加', '', '/report/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1069071520187330564', 'report_update', 'report', '[0],[report],', '报表统计更新', '', '/report/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1069071520187330565', 'report_delete', 'report', '[0],[report],', '报表统计删除', '', '/report/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1069071520187330566', 'report_detail', 'report', '[0],[report],', '报表统计详情', '', '/report/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499777', 'task', '0', '[0],', '交办事项', '', '/task', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499778', 'task_get_task_list', 'task', '[0],[task],', '交办事项列表', '', '/task/getTaskList', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499779', 'task_add', 'task', '[0],[task],', '交办事项添加', '', '/task/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499780', 'task_update', 'task', '[0],[task],', '交办事项更新', '', '/task/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499781', 'task_delete', 'task', '[0],[task],', '交办事项删除', '', '/task/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072037969671499782', 'task_detail', 'task', '[0],[task],', '交办事项详情', '', '/task/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140226', 'taskassign', '0', '[0],', '交办事项时间', '', '/taskassign', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140227', 'taskassign_list', 'taskassign', '[0],[taskassign],', '交办事项时间列表', '', '/taskassign/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140228', 'taskassign_add', 'taskassign', '[0],[taskassign],', '交办事项时间添加', '', '/taskassign/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140229', 'taskassign_update', 'taskassign', '[0],[taskassign],', '交办事项时间更新', '', '/taskassign/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140230', 'taskassign_delete', 'taskassign', '[0],[taskassign],', '交办事项时间删除', '', '/taskassign/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038663145140231', 'taskassign_detail', 'taskassign', '[0],[taskassign],', '交办事项时间详情', '', '/taskassign/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245697', 'taskassignLog', '0', '[0],', '督察督办运转记录', '', '/taskassignLog', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245698', 'taskassignLog_list', 'taskassignLog', '[0],[taskassignLog],', '督察督办运转记录列表', '', '/taskassignLog/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245699', 'taskassignLog_add', 'taskassignLog', '[0],[taskassignLog],', '督察督办运转记录添加', '', '/taskassignLog/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245700', 'taskassignLog_update', 'taskassignLog', '[0],[taskassignLog],', '督察督办运转记录更新', '', '/taskassignLog/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245701', 'taskassignLog_delete', 'taskassignLog', '[0],[taskassignLog],', '督察督办运转记录删除', '', '/taskassignLog/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072038933753245702', 'taskassignLog_detail', 'taskassignLog', '[0],[taskassignLog],', '督察督办运转记录详情', '', '/taskassignLog/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823106', 'taskassignUnit', '0', '[0],', '单位督办记录', '', '/taskassignUnit', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823107', 'taskassignUnit_list', 'taskassignUnit', '[0],[taskassignUnit],', '单位督办记录列表', '', '/taskassignUnit/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823108', 'taskassignUnit_add', 'taskassignUnit', '[0],[taskassignUnit],', '单位督办记录添加', '', '/taskassignUnit/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823109', 'taskassignUnit_update', 'taskassignUnit', '[0],[taskassignUnit],', '单位督办记录更新', '', '/taskassignUnit/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823110', 'taskassignUnit_delete', 'taskassignUnit', '[0],[taskassignUnit],', '单位督办记录删除', '', '/taskassignUnit/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039304747823111', 'taskassignUnit_detail', 'taskassignUnit', '[0],[taskassignUnit],', '单位督办记录详情', '', '/taskassignUnit/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598850', 'taskassignUnitdeal', '0', '[0],', '单位督办日志记录', '', '/taskassignUnitdeal', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598851', 'taskassignUnitdeal_list', 'taskassignUnitdeal', '[0],[taskassignUnitdeal],', '单位督办日志记录列表', '', '/taskassignUnitdeal/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598852', 'taskassignUnitdeal_add', 'taskassignUnitdeal', '[0],[taskassignUnitdeal],', '单位督办日志记录添加', '', '/taskassignUnitdeal/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598853', 'taskassignUnitdeal_update', 'taskassignUnitdeal', '[0],[taskassignUnitdeal],', '单位督办日志记录更新', '', '/taskassignUnitdeal/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598854', 'taskassignUnitdeal_delete', 'taskassignUnitdeal', '[0],[taskassignUnitdeal],', '单位督办日志记录删除', '', '/taskassignUnitdeal/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1072039418459598855', 'taskassignUnitdeal_detail', 'taskassignUnitdeal', '[0],[taskassignUnitdeal],', '单位督办日志记录详情', '', '/taskassignUnitdeal/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597121', 'docs', 'system', '[0],[system],', '公文运转', '', '/docs', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597122', 'docs_list', 'docs', '[0],[system],[docs],', '公文运转列表', '', '/docs/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597123', 'docs_add', 'docs', '[0],[system],[docs],', '公文运转添加', '', '/docs/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597124', 'docs_update', 'docs', '[0],[system],[docs],', '公文运转更新', '', '/docs/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597125', 'docs_delete', 'docs', '[0],[system],[docs],', '公文运转删除', '', '/docs/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707393246597126', 'docs_detail', 'docs', '[0],[system],[docs],', '公文运转详情', '', '/docs/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552834', 'docassignrec', 'system', '[0],[system],', '公文运转上报管理', '', '/docassignrec', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552835', 'docassignrec_list', 'docassignrec', '[0],[system],[docassignrec],', '公文运转上报管理列表', '', '/docassignrec/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552836', 'docassignrec_add', 'docassignrec', '[0],[system],[docassignrec],', '公文运转上报管理添加', '', '/docassignrec/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552837', 'docassignrec_update', 'docassignrec', '[0],[system],[docassignrec],', '公文运转上报管理更新', '', '/docassignrec/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552838', 'docassignrec_delete', 'docassignrec', '[0],[system],[docassignrec],', '公文运转上报管理删除', '', '/docassignrec/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707577074552839', 'docassignrec_detail', 'docassignrec', '[0],[system],[docassignrec],', '公文运转上报管理详情', '', '/docassignrec/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131073', 'infos', 'system', '[0],[system],', '区委信息', '', '/infos', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131074', 'infos_list', 'infos', '[0],[system],[infos],', '区委信息列表', '', '/infos/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131075', 'infos_add', 'infos', '[0],[system],[infos],', '区委信息添加', '', '/infos/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131076', 'infos_update', 'infos', '[0],[system],[infos],', '区委信息更新', '', '/infos/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131077', 'infos_delete', 'infos', '[0],[system],[infos],', '区委信息删除', '', '/infos/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707690048131078', 'infos_detail', 'infos', '[0],[system],[infos],', '区委信息详情', '', '/infos/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236162', 'infosrec', 'system', '[0],[system],', '区委信息上报管理', '', '/infosrec', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236163', 'infosrec_list', 'infosrec', '[0],[system],[infosrec],', '区委信息上报管理列表', '', '/infosrec/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236164', 'infosrec_add', 'infosrec', '[0],[system],[infosrec],', '区委信息上报管理添加', '', '/infosrec/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236165', 'infosrec_update', 'infosrec', '[0],[system],[infosrec],', '区委信息上报管理更新', '', '/infosrec/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236166', 'infosrec_delete', 'infosrec', '[0],[system],[infosrec],', '区委信息上报管理删除', '', '/infosrec/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1076707782310236167', 'infosrec_detail', 'infosrec', '[0],[system],[infosrec],', '区委信息上报管理详情', '', '/infosrec/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069594906626', 'unitType', '0', '[0],', '单位类型', '', '/unitType', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069603295234', 'unitType_list', 'unitType', '[0],[unitType],', '单位类型列表', '', '/unitType/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069603295235', 'unitType_add', 'unitType', '[0],[unitType],', '单位类型添加', '', '/unitType/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069603295236', 'unitType_update', 'unitType', '[0],[unitType],', '单位类型更新', '', '/unitType/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069603295237', 'unitType_delete', 'unitType', '[0],[unitType],', '单位类型删除', '', '/unitType/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1083263069603295238', 'unitType_detail', 'unitType', '[0],[unitType],', '单位类型详情', '', '/unitType/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128129', 'checkitem', '0', '[0],', '督查类型', '', '/checkitem', '99', '1', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128130', 'checkitem_list', 'checkitem', '[0],[checkitem],', '督查类型列表', '', '/checkitem/list', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128131', 'checkitem_add', 'checkitem', '[0],[checkitem],', '督查类型添加', '', '/checkitem/add', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128132', 'checkitem_update', 'checkitem', '[0],[checkitem],', '督查类型更新', '', '/checkitem/update', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128133', 'checkitem_delete', 'checkitem', '[0],[checkitem],', '督查类型删除', '', '/checkitem/delete', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1088978044414128134', 'checkitem_detail', 'checkitem', '[0],[checkitem],', '督查类型详情', '', '/checkitem/detail', '99', '2', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185666', 'asset', 'system', '[0],[system],', '资源管理', '', '/asset', '99', '2', '1', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185667', 'asset_list', 'asset', '[0],[system],[asset],', '资源管理列表', '', '/asset/list', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185668', 'asset_add', 'asset', '[0],[system],[asset],', '资源管理添加', '', '/asset/add', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185669', 'asset_update', 'asset', '[0],[system],[asset],', '资源管理更新', '', '/asset/update', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185670', 'asset_delete', 'asset', '[0],[system],[asset],', '资源管理删除', '', '/asset/delete', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185671', 'asset_detail', 'asset', '[0],[system],[asset],', '资源管理详情', '', '/asset/detail', '99', '3', '0', null, '1', '0', '1');
INSERT INTO `sys_menu` VALUES ('1101321546003185672', 'taskassignUnit_updateIsTimeLy', 'taskassignUnit', '[0],[taskassignUnit],', '标记单位延期', '', '/taskassignUnit/updateIsTimeLy', '99', '2', '0', null, '1', '0', '1');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5483 DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES ('4690', '1051751968160198657', '3');
INSERT INTO `sys_relation` VALUES ('4691', '1051751968168587268', '3');
INSERT INTO `sys_relation` VALUES ('4692', '1065176202181935112', '3');
INSERT INTO `sys_relation` VALUES ('4693', '1065491422498635788', '3');
INSERT INTO `sys_relation` VALUES ('4694', '1065491422498635789', '3');
INSERT INTO `sys_relation` VALUES ('4695', '1065491422498635792', '3');
INSERT INTO `sys_relation` VALUES ('4696', '1065491422498635793', '3');
INSERT INTO `sys_relation` VALUES ('4697', '1072037969671499777', '3');
INSERT INTO `sys_relation` VALUES ('4698', '1072037969671499779', '3');
INSERT INTO `sys_relation` VALUES ('4699', '1072037969671499780', '3');
INSERT INTO `sys_relation` VALUES ('4700', '1072037969671499781', '3');
INSERT INTO `sys_relation` VALUES ('4701', '1072038663145140226', '3');
INSERT INTO `sys_relation` VALUES ('4702', '1072038663145140228', '3');
INSERT INTO `sys_relation` VALUES ('4703', '1072038663145140229', '3');
INSERT INTO `sys_relation` VALUES ('4704', '1072038663145140230', '3');
INSERT INTO `sys_relation` VALUES ('4705', '1072038933753245697', '3');
INSERT INTO `sys_relation` VALUES ('4706', '1072038933753245699', '3');
INSERT INTO `sys_relation` VALUES ('4707', '1072038933753245700', '3');
INSERT INTO `sys_relation` VALUES ('4708', '1072038933753245701', '3');
INSERT INTO `sys_relation` VALUES ('4709', '1072039304747823106', '3');
INSERT INTO `sys_relation` VALUES ('4710', '1072039304747823108', '3');
INSERT INTO `sys_relation` VALUES ('4711', '1072039304747823109', '3');
INSERT INTO `sys_relation` VALUES ('4712', '1072039304747823110', '3');
INSERT INTO `sys_relation` VALUES ('4713', '1072039418459598850', '3');
INSERT INTO `sys_relation` VALUES ('4714', '1072039418459598852', '3');
INSERT INTO `sys_relation` VALUES ('4715', '1072039418459598853', '3');
INSERT INTO `sys_relation` VALUES ('4716', '1072039418459598854', '3');
INSERT INTO `sys_relation` VALUES ('4717', '1054662491512094722', '4');
INSERT INTO `sys_relation` VALUES ('4718', '1054662491512094723', '4');
INSERT INTO `sys_relation` VALUES ('4719', '1054662491512094724', '4');
INSERT INTO `sys_relation` VALUES ('4720', '1054662491512094725', '4');
INSERT INTO `sys_relation` VALUES ('4721', '1054662491512094726', '4');
INSERT INTO `sys_relation` VALUES ('4722', '1054662491512094727', '4');
INSERT INTO `sys_relation` VALUES ('4723', '1054662678410280961', '4');
INSERT INTO `sys_relation` VALUES ('4724', '1054662678410280962', '4');
INSERT INTO `sys_relation` VALUES ('4725', '1054662678410280963', '4');
INSERT INTO `sys_relation` VALUES ('4726', '1054662678410280964', '4');
INSERT INTO `sys_relation` VALUES ('4727', '1054662678410280965', '4');
INSERT INTO `sys_relation` VALUES ('4728', '1054662678410280966', '4');
INSERT INTO `sys_relation` VALUES ('4729', '1065491422498635783', '4');
INSERT INTO `sys_relation` VALUES ('4730', '105', '5');
INSERT INTO `sys_relation` VALUES ('4731', '1076707393246597121', '5');
INSERT INTO `sys_relation` VALUES ('4732', '1076707393246597122', '5');
INSERT INTO `sys_relation` VALUES ('4733', '1076707393246597123', '5');
INSERT INTO `sys_relation` VALUES ('4734', '1076707393246597124', '5');
INSERT INTO `sys_relation` VALUES ('4735', '1076707393246597125', '5');
INSERT INTO `sys_relation` VALUES ('4736', '1076707393246597126', '5');
INSERT INTO `sys_relation` VALUES ('4737', '1076707577074552834', '5');
INSERT INTO `sys_relation` VALUES ('4738', '1076707577074552835', '5');
INSERT INTO `sys_relation` VALUES ('4739', '1076707577074552836', '5');
INSERT INTO `sys_relation` VALUES ('4740', '1076707577074552837', '5');
INSERT INTO `sys_relation` VALUES ('4741', '1076707577074552838', '5');
INSERT INTO `sys_relation` VALUES ('4742', '1076707577074552839', '5');
INSERT INTO `sys_relation` VALUES ('4743', '1065491422498635784', '5');
INSERT INTO `sys_relation` VALUES ('4744', '105', '6');
INSERT INTO `sys_relation` VALUES ('4745', '1076707690048131073', '6');
INSERT INTO `sys_relation` VALUES ('4746', '1076707690048131074', '6');
INSERT INTO `sys_relation` VALUES ('4747', '1076707690048131075', '6');
INSERT INTO `sys_relation` VALUES ('4748', '1076707690048131076', '6');
INSERT INTO `sys_relation` VALUES ('4749', '1076707690048131077', '6');
INSERT INTO `sys_relation` VALUES ('4750', '1076707690048131078', '6');
INSERT INTO `sys_relation` VALUES ('4751', '1076707782310236162', '6');
INSERT INTO `sys_relation` VALUES ('4752', '1076707782310236163', '6');
INSERT INTO `sys_relation` VALUES ('4753', '1076707782310236164', '6');
INSERT INTO `sys_relation` VALUES ('4754', '1076707782310236165', '6');
INSERT INTO `sys_relation` VALUES ('4755', '1076707782310236166', '6');
INSERT INTO `sys_relation` VALUES ('4756', '1076707782310236167', '6');
INSERT INTO `sys_relation` VALUES ('4757', '1065491422498635785', '6');
INSERT INTO `sys_relation` VALUES ('4758', '105', '7');
INSERT INTO `sys_relation` VALUES ('4759', '1065067021202436098', '7');
INSERT INTO `sys_relation` VALUES ('4760', '1065067021202436099', '7');
INSERT INTO `sys_relation` VALUES ('4761', '1065067021202436103', '7');
INSERT INTO `sys_relation` VALUES ('4762', '1065491422498635777', '7');
INSERT INTO `sys_relation` VALUES ('4763', '1065491422498635778', '7');
INSERT INTO `sys_relation` VALUES ('4764', '1065491422498635782', '7');
INSERT INTO `sys_relation` VALUES ('4765', '1076707393246597121', '7');
INSERT INTO `sys_relation` VALUES ('4766', '1076707393246597122', '7');
INSERT INTO `sys_relation` VALUES ('4767', '1076707393246597126', '7');
INSERT INTO `sys_relation` VALUES ('4768', '1076707577074552834', '7');
INSERT INTO `sys_relation` VALUES ('4769', '1076707577074552835', '7');
INSERT INTO `sys_relation` VALUES ('4770', '1076707577074552839', '7');
INSERT INTO `sys_relation` VALUES ('4771', '1076707690048131073', '7');
INSERT INTO `sys_relation` VALUES ('4772', '1076707690048131074', '7');
INSERT INTO `sys_relation` VALUES ('4773', '1076707690048131078', '7');
INSERT INTO `sys_relation` VALUES ('4774', '1076707782310236162', '7');
INSERT INTO `sys_relation` VALUES ('4775', '1076707782310236163', '7');
INSERT INTO `sys_relation` VALUES ('4776', '1076707782310236167', '7');
INSERT INTO `sys_relation` VALUES ('4777', '1051749916533174273', '7');
INSERT INTO `sys_relation` VALUES ('4778', '1051749916537368578', '7');
INSERT INTO `sys_relation` VALUES ('4779', '1051749916537368582', '7');
INSERT INTO `sys_relation` VALUES ('4780', '1051751238074478594', '7');
INSERT INTO `sys_relation` VALUES ('4781', '1051751238074478595', '7');
INSERT INTO `sys_relation` VALUES ('4782', '1051751238074478599', '7');
INSERT INTO `sys_relation` VALUES ('4783', '1051751968160198657', '7');
INSERT INTO `sys_relation` VALUES ('4784', '1051751968168587266', '7');
INSERT INTO `sys_relation` VALUES ('4785', '1051751968168587270', '7');
INSERT INTO `sys_relation` VALUES ('4786', '1054662491512094722', '7');
INSERT INTO `sys_relation` VALUES ('4787', '1054662491512094723', '7');
INSERT INTO `sys_relation` VALUES ('4788', '1054662491512094727', '7');
INSERT INTO `sys_relation` VALUES ('4789', '1054662678410280961', '7');
INSERT INTO `sys_relation` VALUES ('4790', '1054662678410280962', '7');
INSERT INTO `sys_relation` VALUES ('4791', '1054662678410280966', '7');
INSERT INTO `sys_relation` VALUES ('4792', '1062589527937323015', '7');
INSERT INTO `sys_relation` VALUES ('4793', '1069071520187330562', '7');
INSERT INTO `sys_relation` VALUES ('4794', '1069071520187330563', '7');
INSERT INTO `sys_relation` VALUES ('4795', '1069071520187330564', '7');
INSERT INTO `sys_relation` VALUES ('4796', '1069071520187330565', '7');
INSERT INTO `sys_relation` VALUES ('4797', '1069071520187330566', '7');
INSERT INTO `sys_relation` VALUES ('4798', '1065491422498635783', '7');
INSERT INTO `sys_relation` VALUES ('4799', '1065491422498635784', '7');
INSERT INTO `sys_relation` VALUES ('4800', '1065491422498635785', '7');
INSERT INTO `sys_relation` VALUES ('4801', '1065491422498635793', '7');
INSERT INTO `sys_relation` VALUES ('4802', '1072037969671499777', '7');
INSERT INTO `sys_relation` VALUES ('4803', '1072037969671499778', '7');
INSERT INTO `sys_relation` VALUES ('4804', '1072037969671499782', '7');
INSERT INTO `sys_relation` VALUES ('4805', '1072038663145140226', '7');
INSERT INTO `sys_relation` VALUES ('4806', '1072038663145140227', '7');
INSERT INTO `sys_relation` VALUES ('4807', '1072038663145140231', '7');
INSERT INTO `sys_relation` VALUES ('4808', '1072038933753245697', '7');
INSERT INTO `sys_relation` VALUES ('4809', '1072038933753245698', '7');
INSERT INTO `sys_relation` VALUES ('4810', '1072038933753245702', '7');
INSERT INTO `sys_relation` VALUES ('4811', '1072039304747823106', '7');
INSERT INTO `sys_relation` VALUES ('4812', '1072039304747823107', '7');
INSERT INTO `sys_relation` VALUES ('4813', '1072039304747823111', '7');
INSERT INTO `sys_relation` VALUES ('4814', '1072039418459598850', '7');
INSERT INTO `sys_relation` VALUES ('4815', '1072039418459598851', '7');
INSERT INTO `sys_relation` VALUES ('4816', '1072039418459598855', '7');
INSERT INTO `sys_relation` VALUES ('4817', '105', '8');
INSERT INTO `sys_relation` VALUES ('4818', '1065067021202436098', '8');
INSERT INTO `sys_relation` VALUES ('4819', '1065067021202436099', '8');
INSERT INTO `sys_relation` VALUES ('4820', '1065067021202436103', '8');
INSERT INTO `sys_relation` VALUES ('4821', '1065491422498635777', '8');
INSERT INTO `sys_relation` VALUES ('4822', '1065491422498635778', '8');
INSERT INTO `sys_relation` VALUES ('4823', '1065491422498635782', '8');
INSERT INTO `sys_relation` VALUES ('4824', '1076707393246597121', '8');
INSERT INTO `sys_relation` VALUES ('4825', '1076707393246597122', '8');
INSERT INTO `sys_relation` VALUES ('4826', '1076707393246597126', '8');
INSERT INTO `sys_relation` VALUES ('4827', '1076707577074552834', '8');
INSERT INTO `sys_relation` VALUES ('4828', '1076707577074552835', '8');
INSERT INTO `sys_relation` VALUES ('4829', '1076707577074552839', '8');
INSERT INTO `sys_relation` VALUES ('4830', '1076707690048131073', '8');
INSERT INTO `sys_relation` VALUES ('4831', '1076707690048131074', '8');
INSERT INTO `sys_relation` VALUES ('4832', '1076707690048131078', '8');
INSERT INTO `sys_relation` VALUES ('4833', '1076707782310236162', '8');
INSERT INTO `sys_relation` VALUES ('4834', '1076707782310236163', '8');
INSERT INTO `sys_relation` VALUES ('4835', '1076707782310236167', '8');
INSERT INTO `sys_relation` VALUES ('4836', '1051749916533174273', '8');
INSERT INTO `sys_relation` VALUES ('4837', '1051749916537368578', '8');
INSERT INTO `sys_relation` VALUES ('4838', '1051749916537368582', '8');
INSERT INTO `sys_relation` VALUES ('4839', '1051751968160198657', '8');
INSERT INTO `sys_relation` VALUES ('4840', '1051751968168587266', '8');
INSERT INTO `sys_relation` VALUES ('4841', '1051751968168587270', '8');
INSERT INTO `sys_relation` VALUES ('4842', '1054662491512094722', '8');
INSERT INTO `sys_relation` VALUES ('4843', '1054662491512094723', '8');
INSERT INTO `sys_relation` VALUES ('4844', '1054662491512094727', '8');
INSERT INTO `sys_relation` VALUES ('4845', '1054662678410280961', '8');
INSERT INTO `sys_relation` VALUES ('4846', '1054662678410280962', '8');
INSERT INTO `sys_relation` VALUES ('4847', '1054662678410280966', '8');
INSERT INTO `sys_relation` VALUES ('4848', '1062589527937323015', '8');
INSERT INTO `sys_relation` VALUES ('4849', '1069071520187330562', '8');
INSERT INTO `sys_relation` VALUES ('4850', '1069071520187330563', '8');
INSERT INTO `sys_relation` VALUES ('4851', '1069071520187330564', '8');
INSERT INTO `sys_relation` VALUES ('4852', '1069071520187330565', '8');
INSERT INTO `sys_relation` VALUES ('4853', '1069071520187330566', '8');
INSERT INTO `sys_relation` VALUES ('4854', '1065491422498635783', '8');
INSERT INTO `sys_relation` VALUES ('4855', '1065491422498635784', '8');
INSERT INTO `sys_relation` VALUES ('4856', '1065491422498635785', '8');
INSERT INTO `sys_relation` VALUES ('4857', '1065491422498635793', '8');
INSERT INTO `sys_relation` VALUES ('4858', '1072037969671499777', '8');
INSERT INTO `sys_relation` VALUES ('4859', '1072037969671499778', '8');
INSERT INTO `sys_relation` VALUES ('4860', '1072037969671499782', '8');
INSERT INTO `sys_relation` VALUES ('4861', '1072038663145140226', '8');
INSERT INTO `sys_relation` VALUES ('4862', '1072038663145140227', '8');
INSERT INTO `sys_relation` VALUES ('4863', '1072038663145140231', '8');
INSERT INTO `sys_relation` VALUES ('4864', '1072038933753245697', '8');
INSERT INTO `sys_relation` VALUES ('4865', '1072038933753245698', '8');
INSERT INTO `sys_relation` VALUES ('4866', '1072038933753245702', '8');
INSERT INTO `sys_relation` VALUES ('4867', '1072039304747823106', '8');
INSERT INTO `sys_relation` VALUES ('4868', '1072039304747823107', '8');
INSERT INTO `sys_relation` VALUES ('4869', '1072039304747823111', '8');
INSERT INTO `sys_relation` VALUES ('4870', '1072039418459598850', '8');
INSERT INTO `sys_relation` VALUES ('4871', '1072039418459598851', '8');
INSERT INTO `sys_relation` VALUES ('4872', '1072039418459598855', '8');
INSERT INTO `sys_relation` VALUES ('5243', '105', '1');
INSERT INTO `sys_relation` VALUES ('5244', '114', '1');
INSERT INTO `sys_relation` VALUES ('5245', '115', '1');
INSERT INTO `sys_relation` VALUES ('5246', '116', '1');
INSERT INTO `sys_relation` VALUES ('5247', '117', '1');
INSERT INTO `sys_relation` VALUES ('5248', '118', '1');
INSERT INTO `sys_relation` VALUES ('5249', '162', '1');
INSERT INTO `sys_relation` VALUES ('5250', '163', '1');
INSERT INTO `sys_relation` VALUES ('5251', '164', '1');
INSERT INTO `sys_relation` VALUES ('5252', '119', '1');
INSERT INTO `sys_relation` VALUES ('5253', '120', '1');
INSERT INTO `sys_relation` VALUES ('5254', '121', '1');
INSERT INTO `sys_relation` VALUES ('5255', '122', '1');
INSERT INTO `sys_relation` VALUES ('5256', '150', '1');
INSERT INTO `sys_relation` VALUES ('5257', '151', '1');
INSERT INTO `sys_relation` VALUES ('5258', '128', '1');
INSERT INTO `sys_relation` VALUES ('5259', '134', '1');
INSERT INTO `sys_relation` VALUES ('5260', '158', '1');
INSERT INTO `sys_relation` VALUES ('5261', '159', '1');
INSERT INTO `sys_relation` VALUES ('5262', '130', '1');
INSERT INTO `sys_relation` VALUES ('5263', '131', '1');
INSERT INTO `sys_relation` VALUES ('5264', '135', '1');
INSERT INTO `sys_relation` VALUES ('5265', '136', '1');
INSERT INTO `sys_relation` VALUES ('5266', '137', '1');
INSERT INTO `sys_relation` VALUES ('5267', '152', '1');
INSERT INTO `sys_relation` VALUES ('5268', '153', '1');
INSERT INTO `sys_relation` VALUES ('5269', '154', '1');
INSERT INTO `sys_relation` VALUES ('5270', '132', '1');
INSERT INTO `sys_relation` VALUES ('5271', '138', '1');
INSERT INTO `sys_relation` VALUES ('5272', '139', '1');
INSERT INTO `sys_relation` VALUES ('5273', '140', '1');
INSERT INTO `sys_relation` VALUES ('5274', '155', '1');
INSERT INTO `sys_relation` VALUES ('5275', '156', '1');
INSERT INTO `sys_relation` VALUES ('5276', '157', '1');
INSERT INTO `sys_relation` VALUES ('5277', '133', '1');
INSERT INTO `sys_relation` VALUES ('5278', '160', '1');
INSERT INTO `sys_relation` VALUES ('5279', '161', '1');
INSERT INTO `sys_relation` VALUES ('5280', '141', '1');
INSERT INTO `sys_relation` VALUES ('5281', '142', '1');
INSERT INTO `sys_relation` VALUES ('5282', '143', '1');
INSERT INTO `sys_relation` VALUES ('5283', '144', '1');
INSERT INTO `sys_relation` VALUES ('5284', '1055388626000658433', '1');
INSERT INTO `sys_relation` VALUES ('5285', '1055388626009047041', '1');
INSERT INTO `sys_relation` VALUES ('5286', '1055388626009047042', '1');
INSERT INTO `sys_relation` VALUES ('5287', '1055388626009047043', '1');
INSERT INTO `sys_relation` VALUES ('5288', '1055388626009047044', '1');
INSERT INTO `sys_relation` VALUES ('5289', '1055388626009047045', '1');
INSERT INTO `sys_relation` VALUES ('5290', '1065067021202436098', '1');
INSERT INTO `sys_relation` VALUES ('5291', '1065067021202436099', '1');
INSERT INTO `sys_relation` VALUES ('5292', '1065067021202436100', '1');
INSERT INTO `sys_relation` VALUES ('5293', '1065067021202436101', '1');
INSERT INTO `sys_relation` VALUES ('5294', '1065067021202436102', '1');
INSERT INTO `sys_relation` VALUES ('5295', '1065067021202436103', '1');
INSERT INTO `sys_relation` VALUES ('5296', '1065491422498635777', '1');
INSERT INTO `sys_relation` VALUES ('5297', '1065491422498635778', '1');
INSERT INTO `sys_relation` VALUES ('5298', '1065491422498635779', '1');
INSERT INTO `sys_relation` VALUES ('5299', '1065491422498635780', '1');
INSERT INTO `sys_relation` VALUES ('5300', '1065491422498635781', '1');
INSERT INTO `sys_relation` VALUES ('5301', '1065491422498635782', '1');
INSERT INTO `sys_relation` VALUES ('5302', '1076707393246597121', '1');
INSERT INTO `sys_relation` VALUES ('5303', '1076707393246597122', '1');
INSERT INTO `sys_relation` VALUES ('5304', '1076707393246597123', '1');
INSERT INTO `sys_relation` VALUES ('5305', '1076707393246597124', '1');
INSERT INTO `sys_relation` VALUES ('5306', '1076707393246597125', '1');
INSERT INTO `sys_relation` VALUES ('5307', '1076707393246597126', '1');
INSERT INTO `sys_relation` VALUES ('5308', '1076707577074552834', '1');
INSERT INTO `sys_relation` VALUES ('5309', '1076707577074552835', '1');
INSERT INTO `sys_relation` VALUES ('5310', '1076707577074552836', '1');
INSERT INTO `sys_relation` VALUES ('5311', '1076707577074552837', '1');
INSERT INTO `sys_relation` VALUES ('5312', '1076707577074552838', '1');
INSERT INTO `sys_relation` VALUES ('5313', '1076707577074552839', '1');
INSERT INTO `sys_relation` VALUES ('5314', '1076707690048131073', '1');
INSERT INTO `sys_relation` VALUES ('5315', '1076707690048131074', '1');
INSERT INTO `sys_relation` VALUES ('5316', '1076707690048131075', '1');
INSERT INTO `sys_relation` VALUES ('5317', '1076707690048131076', '1');
INSERT INTO `sys_relation` VALUES ('5318', '1076707690048131077', '1');
INSERT INTO `sys_relation` VALUES ('5319', '1076707690048131078', '1');
INSERT INTO `sys_relation` VALUES ('5320', '1076707782310236162', '1');
INSERT INTO `sys_relation` VALUES ('5321', '1076707782310236163', '1');
INSERT INTO `sys_relation` VALUES ('5322', '1076707782310236164', '1');
INSERT INTO `sys_relation` VALUES ('5323', '1076707782310236165', '1');
INSERT INTO `sys_relation` VALUES ('5324', '1076707782310236166', '1');
INSERT INTO `sys_relation` VALUES ('5325', '1076707782310236167', '1');
INSERT INTO `sys_relation` VALUES ('5326', '1101321546003185666', '1');
INSERT INTO `sys_relation` VALUES ('5327', '1101321546003185667', '1');
INSERT INTO `sys_relation` VALUES ('5328', '1101321546003185668', '1');
INSERT INTO `sys_relation` VALUES ('5329', '1101321546003185669', '1');
INSERT INTO `sys_relation` VALUES ('5330', '1101321546003185670', '1');
INSERT INTO `sys_relation` VALUES ('5331', '1101321546003185671', '1');
INSERT INTO `sys_relation` VALUES ('5332', '106', '1');
INSERT INTO `sys_relation` VALUES ('5333', '107', '1');
INSERT INTO `sys_relation` VALUES ('5334', '108', '1');
INSERT INTO `sys_relation` VALUES ('5335', '109', '1');
INSERT INTO `sys_relation` VALUES ('5336', '110', '1');
INSERT INTO `sys_relation` VALUES ('5337', '111', '1');
INSERT INTO `sys_relation` VALUES ('5338', '112', '1');
INSERT INTO `sys_relation` VALUES ('5339', '113', '1');
INSERT INTO `sys_relation` VALUES ('5340', '165', '1');
INSERT INTO `sys_relation` VALUES ('5341', '166', '1');
INSERT INTO `sys_relation` VALUES ('5342', '167', '1');
INSERT INTO `sys_relation` VALUES ('5343', '145', '1');
INSERT INTO `sys_relation` VALUES ('5344', '148', '1');
INSERT INTO `sys_relation` VALUES ('5345', '149', '1');
INSERT INTO `sys_relation` VALUES ('5346', '1051749916533174273', '1');
INSERT INTO `sys_relation` VALUES ('5347', '1051749916537368578', '1');
INSERT INTO `sys_relation` VALUES ('5348', '1051749916537368579', '1');
INSERT INTO `sys_relation` VALUES ('5349', '1051749916537368580', '1');
INSERT INTO `sys_relation` VALUES ('5350', '1051749916537368581', '1');
INSERT INTO `sys_relation` VALUES ('5351', '1051749916537368582', '1');
INSERT INTO `sys_relation` VALUES ('5352', '1051751238074478594', '1');
INSERT INTO `sys_relation` VALUES ('5353', '1051751238074478595', '1');
INSERT INTO `sys_relation` VALUES ('5354', '1051751238074478596', '1');
INSERT INTO `sys_relation` VALUES ('5355', '1051751238074478597', '1');
INSERT INTO `sys_relation` VALUES ('5356', '1051751238074478598', '1');
INSERT INTO `sys_relation` VALUES ('5357', '1051751238074478599', '1');
INSERT INTO `sys_relation` VALUES ('5358', '1051751968160198657', '1');
INSERT INTO `sys_relation` VALUES ('5359', '1051751968168587266', '1');
INSERT INTO `sys_relation` VALUES ('5360', '1051751968168587267', '1');
INSERT INTO `sys_relation` VALUES ('5361', '1051751968168587268', '1');
INSERT INTO `sys_relation` VALUES ('5362', '1051751968168587269', '1');
INSERT INTO `sys_relation` VALUES ('5363', '1051751968168587270', '1');
INSERT INTO `sys_relation` VALUES ('5364', '1054662491512094722', '1');
INSERT INTO `sys_relation` VALUES ('5365', '1054662491512094723', '1');
INSERT INTO `sys_relation` VALUES ('5366', '1054662491512094724', '1');
INSERT INTO `sys_relation` VALUES ('5367', '1054662491512094725', '1');
INSERT INTO `sys_relation` VALUES ('5368', '1054662491512094726', '1');
INSERT INTO `sys_relation` VALUES ('5369', '1054662491512094727', '1');
INSERT INTO `sys_relation` VALUES ('5370', '1054662678410280961', '1');
INSERT INTO `sys_relation` VALUES ('5371', '1054662678410280962', '1');
INSERT INTO `sys_relation` VALUES ('5372', '1054662678410280963', '1');
INSERT INTO `sys_relation` VALUES ('5373', '1054662678410280964', '1');
INSERT INTO `sys_relation` VALUES ('5374', '1054662678410280965', '1');
INSERT INTO `sys_relation` VALUES ('5375', '1054662678410280966', '1');
INSERT INTO `sys_relation` VALUES ('5376', '1062589527937323015', '1');
INSERT INTO `sys_relation` VALUES ('5377', '1069071520187330562', '1');
INSERT INTO `sys_relation` VALUES ('5378', '1069071520187330563', '1');
INSERT INTO `sys_relation` VALUES ('5379', '1069071520187330564', '1');
INSERT INTO `sys_relation` VALUES ('5380', '1069071520187330565', '1');
INSERT INTO `sys_relation` VALUES ('5381', '1069071520187330566', '1');
INSERT INTO `sys_relation` VALUES ('5382', '1062589527937323016', '1');
INSERT INTO `sys_relation` VALUES ('5383', '1065176202181935112', '1');
INSERT INTO `sys_relation` VALUES ('5384', '1065491422498635783', '1');
INSERT INTO `sys_relation` VALUES ('5385', '1065491422498635784', '1');
INSERT INTO `sys_relation` VALUES ('5386', '1065491422498635785', '1');
INSERT INTO `sys_relation` VALUES ('5387', '1065491422498635786', '1');
INSERT INTO `sys_relation` VALUES ('5388', '1065491422498635787', '1');
INSERT INTO `sys_relation` VALUES ('5389', '1065491422498635788', '1');
INSERT INTO `sys_relation` VALUES ('5390', '1065491422498635789', '1');
INSERT INTO `sys_relation` VALUES ('5391', '1065491422498635790', '1');
INSERT INTO `sys_relation` VALUES ('5392', '1065491422498635791', '1');
INSERT INTO `sys_relation` VALUES ('5393', '1065491422498635792', '1');
INSERT INTO `sys_relation` VALUES ('5394', '1065491422498635793', '1');
INSERT INTO `sys_relation` VALUES ('5395', '1072037969671499777', '1');
INSERT INTO `sys_relation` VALUES ('5396', '1072037969671499778', '1');
INSERT INTO `sys_relation` VALUES ('5397', '1072037969671499779', '1');
INSERT INTO `sys_relation` VALUES ('5398', '1072037969671499780', '1');
INSERT INTO `sys_relation` VALUES ('5399', '1072037969671499781', '1');
INSERT INTO `sys_relation` VALUES ('5400', '1072037969671499782', '1');
INSERT INTO `sys_relation` VALUES ('5401', '1072038663145140226', '1');
INSERT INTO `sys_relation` VALUES ('5402', '1072038663145140227', '1');
INSERT INTO `sys_relation` VALUES ('5403', '1072038663145140228', '1');
INSERT INTO `sys_relation` VALUES ('5404', '1072038663145140229', '1');
INSERT INTO `sys_relation` VALUES ('5405', '1072038663145140230', '1');
INSERT INTO `sys_relation` VALUES ('5406', '1072038663145140231', '1');
INSERT INTO `sys_relation` VALUES ('5407', '1072038933753245697', '1');
INSERT INTO `sys_relation` VALUES ('5408', '1072038933753245698', '1');
INSERT INTO `sys_relation` VALUES ('5409', '1072038933753245699', '1');
INSERT INTO `sys_relation` VALUES ('5410', '1072038933753245700', '1');
INSERT INTO `sys_relation` VALUES ('5411', '1072038933753245701', '1');
INSERT INTO `sys_relation` VALUES ('5412', '1072038933753245702', '1');
INSERT INTO `sys_relation` VALUES ('5413', '1072039304747823106', '1');
INSERT INTO `sys_relation` VALUES ('5414', '1072039304747823107', '1');
INSERT INTO `sys_relation` VALUES ('5415', '1072039304747823108', '1');
INSERT INTO `sys_relation` VALUES ('5416', '1072039304747823109', '1');
INSERT INTO `sys_relation` VALUES ('5417', '1072039304747823110', '1');
INSERT INTO `sys_relation` VALUES ('5418', '1072039304747823111', '1');
INSERT INTO `sys_relation` VALUES ('5419', '1101321546003185672', '1');
INSERT INTO `sys_relation` VALUES ('5420', '1072039418459598850', '1');
INSERT INTO `sys_relation` VALUES ('5421', '1072039418459598851', '1');
INSERT INTO `sys_relation` VALUES ('5422', '1072039418459598852', '1');
INSERT INTO `sys_relation` VALUES ('5423', '1072039418459598853', '1');
INSERT INTO `sys_relation` VALUES ('5424', '1072039418459598854', '1');
INSERT INTO `sys_relation` VALUES ('5425', '1072039418459598855', '1');
INSERT INTO `sys_relation` VALUES ('5426', '1083263069594906626', '1');
INSERT INTO `sys_relation` VALUES ('5427', '1083263069603295234', '1');
INSERT INTO `sys_relation` VALUES ('5428', '1083263069603295235', '1');
INSERT INTO `sys_relation` VALUES ('5429', '1083263069603295236', '1');
INSERT INTO `sys_relation` VALUES ('5430', '1083263069603295237', '1');
INSERT INTO `sys_relation` VALUES ('5431', '1083263069603295238', '1');
INSERT INTO `sys_relation` VALUES ('5432', '1088978044414128129', '1');
INSERT INTO `sys_relation` VALUES ('5433', '1088978044414128130', '1');
INSERT INTO `sys_relation` VALUES ('5434', '1088978044414128131', '1');
INSERT INTO `sys_relation` VALUES ('5435', '1088978044414128132', '1');
INSERT INTO `sys_relation` VALUES ('5436', '1088978044414128133', '1');
INSERT INTO `sys_relation` VALUES ('5437', '1088978044414128134', '1');
INSERT INTO `sys_relation` VALUES ('5438', '1051749916533174273', '2');
INSERT INTO `sys_relation` VALUES ('5439', '1051749916537368578', '2');
INSERT INTO `sys_relation` VALUES ('5440', '1051749916537368579', '2');
INSERT INTO `sys_relation` VALUES ('5441', '1051749916537368580', '2');
INSERT INTO `sys_relation` VALUES ('5442', '1051749916537368581', '2');
INSERT INTO `sys_relation` VALUES ('5443', '1051749916537368582', '2');
INSERT INTO `sys_relation` VALUES ('5444', '1051751238074478594', '2');
INSERT INTO `sys_relation` VALUES ('5445', '1051751238074478595', '2');
INSERT INTO `sys_relation` VALUES ('5446', '1051751238074478596', '2');
INSERT INTO `sys_relation` VALUES ('5447', '1051751238074478597', '2');
INSERT INTO `sys_relation` VALUES ('5448', '1051751238074478598', '2');
INSERT INTO `sys_relation` VALUES ('5449', '1051751238074478599', '2');
INSERT INTO `sys_relation` VALUES ('5450', '1051751968160198657', '2');
INSERT INTO `sys_relation` VALUES ('5451', '1051751968168587266', '2');
INSERT INTO `sys_relation` VALUES ('5452', '1051751968168587267', '2');
INSERT INTO `sys_relation` VALUES ('5453', '1051751968168587268', '2');
INSERT INTO `sys_relation` VALUES ('5454', '1051751968168587269', '2');
INSERT INTO `sys_relation` VALUES ('5455', '1051751968168587270', '2');
INSERT INTO `sys_relation` VALUES ('5456', '1062589527937323015', '2');
INSERT INTO `sys_relation` VALUES ('5457', '1062589527937323016', '2');
INSERT INTO `sys_relation` VALUES ('5458', '1065176202181935112', '2');
INSERT INTO `sys_relation` VALUES ('5459', '1065491422498635787', '2');
INSERT INTO `sys_relation` VALUES ('5460', '1065491422498635790', '2');
INSERT INTO `sys_relation` VALUES ('5461', '1065491422498635791', '2');
INSERT INTO `sys_relation` VALUES ('5462', '1065491422498635792', '2');
INSERT INTO `sys_relation` VALUES ('5463', '1065491422498635793', '2');
INSERT INTO `sys_relation` VALUES ('5464', '1072037969671499777', '2');
INSERT INTO `sys_relation` VALUES ('5465', '1072037969671499778', '2');
INSERT INTO `sys_relation` VALUES ('5466', '1072037969671499780', '2');
INSERT INTO `sys_relation` VALUES ('5467', '1072037969671499782', '2');
INSERT INTO `sys_relation` VALUES ('5468', '1072038663145140226', '2');
INSERT INTO `sys_relation` VALUES ('5469', '1072038663145140227', '2');
INSERT INTO `sys_relation` VALUES ('5470', '1072038663145140228', '2');
INSERT INTO `sys_relation` VALUES ('5471', '1072038663145140229', '2');
INSERT INTO `sys_relation` VALUES ('5472', '1072038663145140231', '2');
INSERT INTO `sys_relation` VALUES ('5473', '1072038933753245697', '2');
INSERT INTO `sys_relation` VALUES ('5474', '1072038933753245698', '2');
INSERT INTO `sys_relation` VALUES ('5475', '1072038933753245702', '2');
INSERT INTO `sys_relation` VALUES ('5476', '1072039304747823106', '2');
INSERT INTO `sys_relation` VALUES ('5477', '1072039304747823107', '2');
INSERT INTO `sys_relation` VALUES ('5478', '1072039304747823111', '2');
INSERT INTO `sys_relation` VALUES ('5479', '1101321546003185672', '2');
INSERT INTO `sys_relation` VALUES ('5480', '1072039418459598850', '2');
INSERT INTO `sys_relation` VALUES ('5481', '1072039418459598851', '2');
INSERT INTO `sys_relation` VALUES ('5482', '1072039418459598855', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` int(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` int(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '0', '超级管理员', '1', 'administrator', '1');
INSERT INTO `sys_role` VALUES ('2', '2', '0', '督办中心', '1', 'center', '1');
INSERT INTO `sys_role` VALUES ('3', '3', '0', '督办责任人', '1', 'person', '1');
INSERT INTO `sys_role` VALUES ('4', '4', '0', '区委会议', '1', 'meeting', '1');
INSERT INTO `sys_role` VALUES ('5', '5', '0', '区委公文', '1', 'document', '1');
INSERT INTO `sys_role` VALUES ('6', '6', '0', '区委信息', '1', 'infos', '1');
INSERT INTO `sys_role` VALUES ('7', '7', '0', '查询人员', '1', 'search', '1');
INSERT INTO `sys_role` VALUES ('8', '8', '0', '领导', '1', 'leader', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `uorder` int(11) NOT NULL DEFAULT '0',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  `isagent` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '01a39a2a-797b-4d8a-a064-a5e99bddd75b.jpg', 'administrator', '99', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '超级管理员', '2018-10-18 00:00:00', '1', 'sn93@qq.com', '18048955061', '1', '1', '1', '2016-01-29 08:49:53', '25', '1');
INSERT INTO `sys_user` VALUES ('6', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13118288333', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '任利民', '2019-01-11 00:00:00', '1', null, '13118288333', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('7', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13696212676', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '卢河东', null, null, null, '13696212676', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('8', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13890781203', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '陈  威', null, null, null, '13890781203', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('9', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '10110000000', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '彭志成', null, null, null, '18048955061', '1', '1', '1', '2018-12-24 11:14:37', '22', '1');
INSERT INTO `sys_user` VALUES ('10', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15183566913', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '王晓林', null, null, null, '15183566913', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('11', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13228267753', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '王雪兰', null, null, null, '13228267753', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('12', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13980315177', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '伍海波', null, null, null, '13980315177', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('13', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15983754356', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '罗喜郎', null, null, null, '15983754356', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('14', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15228111067', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '宋智博', null, null, null, '15228111067', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('15', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13350641307', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '蒲  忠', null, null, null, '13350641307', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('16', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13550596371', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '刘  明', null, null, null, '13550596371', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('17', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15882690875', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '曾坤林', null, null, null, '15882690875', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('18', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15082773633', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '姚诗怡', null, null, null, '15082773633', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('19', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18990731412', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '陈  东', null, null, null, '18990731412', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('20', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '10150000000', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '王海朋', null, null, null, '18048955061', '1', '1', '1', '2018-12-24 11:14:37', '22', '1');
INSERT INTO `sys_user` VALUES ('21', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13551688845', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '张泸瑞', null, null, null, '13551688845', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('22', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13890778216', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '郭  靖', null, null, null, '13890778216', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('23', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15281746041', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '李小冲', null, null, null, '15281746041', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('24', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13438758899', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '杨忠斌', null, null, null, '13438758899', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('25', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18284173038', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '曹荣春', null, null, null, '18284173038', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('26', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13990801913', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '赵文基', null, null, null, '13990801913', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('27', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15298204304', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '杨  波', null, null, null, '15298204304', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('28', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1023', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '刘  彦', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('29', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '17790518578', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '张曼菱', null, null, null, '17790518578', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('30', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13990707055', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '杨有敏', null, null, null, '13990707055', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('31', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13696206183', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '宋欣然', null, null, null, '13696206183', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('32', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18280837159', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '祝杨丁', null, null, null, '18280837159', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('33', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1028', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '景红军', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('34', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15082783478', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '董  娟', null, null, null, '15082783478', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('35', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18227396122', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '曹  威', null, null, null, '18227396122', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('36', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18181115791', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '何雅馨', null, null, null, '18181115791', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('37', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1032', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '李晓君', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('38', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1033', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '徐  瑞', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('39', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1034', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '任顺年', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('40', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1035', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '杨  勇', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('41', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1036', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '贾庆伟', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('42', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1037', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '秦吉伟', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('43', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '1038', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '任  俊', null, null, null, null, '1', '1', '0', '2018-12-24 11:14:37', null, '0');
INSERT INTO `sys_user` VALUES ('44', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '15281767089', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '周刚平', null, null, null, '15281767089', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('45', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18380200723', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '沈  婷', null, null, null, '18380200723', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('46', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18780334390', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '张艳红', null, null, null, '18780334390', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('47', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '13668037860', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '杨  颖', null, null, null, '13668037860', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('48', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18314493639', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '罗亚平', null, null, null, '18314493639', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('49', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18782948296', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '陈波羽', null, null, null, '18782948296', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('50', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18381663220', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '陈一帆', null, null, null, '18381663220', '1', '1', '1', '2018-12-24 11:14:37', null, '1');
INSERT INTO `sys_user` VALUES ('51', '6af28819-e925-4b76-9fda-79adf0c21e5b.file', '18882314036', '0', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '林思潞', null, null, null, '18882314036', '1', '1', '1', '2018-12-24 11:14:37', null, '1');

-- ----------------------------
-- Table structure for td_checkitem
-- ----------------------------
DROP TABLE IF EXISTS `td_checkitem`;
CREATE TABLE `td_checkitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemclass` smallint(255) DEFAULT NULL COMMENT '督查项类别（1-事项；2-会议；3-公文；4-信息）',
  `itemdesc` varchar(255) DEFAULT NULL COMMENT '项描述',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态（0-停用；1-启用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='督查类型';

-- ----------------------------
-- Records of td_checkitem
-- ----------------------------
INSERT INTO `td_checkitem` VALUES ('1', '1', '交办事项总数', '1');
INSERT INTO `td_checkitem` VALUES ('2', '1', '交办事项办结率(%)', '1');
INSERT INTO `td_checkitem` VALUES ('3', '1', '未办结数', '1');
INSERT INTO `td_checkitem` VALUES ('4', '1', '办结平均时长(天数)', '1');
INSERT INTO `td_checkitem` VALUES ('5', '1', '未及时上报', '1');
INSERT INTO `td_checkitem` VALUES ('6', '2', '无故缺席', '1');
INSERT INTO `td_checkitem` VALUES ('7', '2', '迟到早退', '1');
INSERT INTO `td_checkitem` VALUES ('8', '2', '交头接耳', '1');
INSERT INTO `td_checkitem` VALUES ('9', '2', '玩手机', '1');
INSERT INTO `td_checkitem` VALUES ('10', '2', '打瞌睡', '1');
INSERT INTO `td_checkitem` VALUES ('11', '2', '未按要求着装', '1');
INSERT INTO `td_checkitem` VALUES ('12', '2', '发言超时', '1');
INSERT INTO `td_checkitem` VALUES ('13', '3', '错别字', '1');
INSERT INTO `td_checkitem` VALUES ('14', '3', '文体格式有误', '1');
INSERT INTO `td_checkitem` VALUES ('15', '3', '体外循环', '1');
INSERT INTO `td_checkitem` VALUES ('16', '3', '一文多送', '1');
INSERT INTO `td_checkitem` VALUES ('17', '3', '总数', '1');
INSERT INTO `td_checkitem` VALUES ('18', '3', '平均流转时长', '1');
INSERT INTO `td_checkitem` VALUES ('19', '3', '逾期上报数(次)', '1');
INSERT INTO `td_checkitem` VALUES ('20', '3', '未上报数(次)', '1');
INSERT INTO `td_checkitem` VALUES ('21', '4', '上报数(篇)', '1');
INSERT INTO `td_checkitem` VALUES ('22', '4', '区委要情采用数(篇)', '1');
INSERT INTO `td_checkitem` VALUES ('23', '4', '区委要情采纳率(%)', '1');
INSERT INTO `td_checkitem` VALUES ('24', '4', '逾期上报数(次)', '1');
INSERT INTO `td_checkitem` VALUES ('25', '4', '未上报数(次)', '1');
INSERT INTO `td_checkitem` VALUES ('26', '4', '得分数(分)', '1');

-- ----------------------------
-- Table structure for td_doc
-- ----------------------------
DROP TABLE IF EXISTS `td_doc`;
CREATE TABLE `td_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL COMMENT '交办公文名称',
  `unitid` int(11) DEFAULT NULL COMMENT '部门id',
  `doctype` smallint(4) DEFAULT NULL COMMENT '公文类型1-请示；2-报告；3-代拟稿类',
  `assignmemo` varchar(255) NOT NULL DEFAULT '' COMMENT '流转说明',
  `currecvid` int(11) DEFAULT NULL COMMENT '当前接收人员ID',
  `currecv` varchar(50) DEFAULT NULL COMMENT '当前接收人员',
  `creatorid` int(255) NOT NULL COMMENT '创建人id',
  `closememo` varchar(255) NOT NULL DEFAULT '' COMMENT '归档关闭说明',
  `assign_time` datetime DEFAULT NULL COMMENT '来文时间',
  `end_time` datetime DEFAULT NULL COMMENT '完结时间',
  `status` smallint(6) NOT NULL COMMENT '状态(启动停用)',
  PRIMARY KEY (`id`),
  KEY `unitid` (`unitid`),
  KEY `assign_time` (`assign_time`),
  KEY `end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办公文表';

-- ----------------------------
-- Records of td_doc
-- ----------------------------

-- ----------------------------
-- Table structure for td_doc_attr
-- ----------------------------
DROP TABLE IF EXISTS `td_doc_attr`;
CREATE TABLE `td_doc_attr` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dassignid` int(11) DEFAULT NULL COMMENT '公文id',
  `attr_asset_id` varchar(20) DEFAULT NULL COMMENT '公文附件资源id',
  `createdtime` datetime DEFAULT NULL COMMENT '公文附件时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办公文附件表';

-- ----------------------------
-- Records of td_doc_attr
-- ----------------------------

-- ----------------------------
-- Table structure for td_doc_rec
-- ----------------------------
DROP TABLE IF EXISTS `td_doc_rec`;
CREATE TABLE `td_doc_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `docassignid` int(11) DEFAULT NULL COMMENT '公文ID',
  `checkitemid` int(11) DEFAULT NULL COMMENT '检查项ID',
  `checkvalue` int(11) DEFAULT '1' COMMENT '检查项值',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `unitid` int(11) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  KEY `unitid` (`unitid`),
  KEY `checkitemid` (`checkitemid`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公文督查记录';

-- ----------------------------
-- Records of td_doc_rec
-- ----------------------------

-- ----------------------------
-- Table structure for td_infos
-- ----------------------------
DROP TABLE IF EXISTS `td_infos`;
CREATE TABLE `td_infos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mtime` datetime DEFAULT NULL COMMENT '信息时间',
  `title` varchar(255) DEFAULT NULL COMMENT '信息名称',
  `creatorid` int(11) DEFAULT NULL COMMENT '起草人ID',
  `status` smallint(6) DEFAULT '1' COMMENT '信息状态(0-停用；1-启用)',
  `memo` varchar(255) DEFAULT NULL COMMENT '信息备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区委信息';

-- ----------------------------
-- Records of td_infos
-- ----------------------------

-- ----------------------------
-- Table structure for td_infosrec
-- ----------------------------
DROP TABLE IF EXISTS `td_infosrec`;
CREATE TABLE `td_infosrec` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `infosid` int(11) DEFAULT NULL COMMENT '信息ID',
  `checkitemid` int(11) DEFAULT NULL COMMENT '检查项ID',
  `checkvalue` varchar(255) DEFAULT NULL COMMENT '检查项值',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `unitid` int(11) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  KEY `unitid` (`unitid`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区委信息督查表';

-- ----------------------------
-- Records of td_infosrec
-- ----------------------------

-- ----------------------------
-- Table structure for td_meeting
-- ----------------------------
DROP TABLE IF EXISTS `td_meeting`;
CREATE TABLE `td_meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mtime` datetime DEFAULT NULL COMMENT '会议时间',
  `title` varchar(255) DEFAULT NULL COMMENT '会议名称',
  `host` varchar(255) DEFAULT NULL COMMENT '会议主持人名称',
  `creatorid` int(11) DEFAULT NULL COMMENT '督查人ID',
  `status` smallint(6) DEFAULT '1' COMMENT '会议状态(0-停用；1-启用)',
  `memo` varchar(255) DEFAULT NULL COMMENT '会议备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区委会议';

-- ----------------------------
-- Records of td_meeting
-- ----------------------------

-- ----------------------------
-- Table structure for td_meetingrec
-- ----------------------------
DROP TABLE IF EXISTS `td_meetingrec`;
CREATE TABLE `td_meetingrec` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `meetingid` int(11) DEFAULT NULL COMMENT '会议ID',
  `checkitemid` int(11) DEFAULT NULL COMMENT '检查项ID',
  `checkvalue` int(11) DEFAULT '1' COMMENT '检查项值',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `unitid` int(11) DEFAULT NULL COMMENT '部门id',
  `name` varchar(255) DEFAULT NULL COMMENT '该部门违纪人员名称',
  PRIMARY KEY (`id`),
  KEY `unitid` (`unitid`),
  KEY `name` (`name`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议督查记录';

-- ----------------------------
-- Records of td_meetingrec
-- ----------------------------

-- ----------------------------
-- Table structure for td_task
-- ----------------------------
DROP TABLE IF EXISTS `td_task`;
CREATE TABLE `td_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `title` varchar(255) NOT NULL COMMENT '交办事项名称',
  `endstatus` smallint(255) NOT NULL DEFAULT '1' COMMENT '状态(启动停用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办事项表';

-- ----------------------------
-- Records of td_task
-- ----------------------------

-- ----------------------------
-- Table structure for td_taskassign
-- ----------------------------
DROP TABLE IF EXISTS `td_taskassign`;
CREATE TABLE `td_taskassign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskid` int(11) NOT NULL COMMENT '交办事项表ID',
  `worktype` int(255) DEFAULT NULL,
  `assigntime` datetime DEFAULT NULL COMMENT '交办限期时间',
  `assignmemo` varchar(255) NOT NULL DEFAULT '' COMMENT '分派说明',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1-未反馈；2-已反馈办理中；3-部分完成；4-全部完成;5-事项归档；6-人为关闭；）',
  `creatorid` int(255) NOT NULL COMMENT '创建人id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `closememo` varchar(255) NOT NULL DEFAULT '' COMMENT '归档说明',
  `endtime` datetime DEFAULT NULL COMMENT '完成时间',
  `phone` varchar(255) DEFAULT NULL COMMENT '总负责人电话',
  `charge` varchar(255) DEFAULT NULL COMMENT '总负责人',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办事项时间表';

-- ----------------------------
-- Records of td_taskassign
-- ----------------------------

-- ----------------------------
-- Table structure for td_taskassign_log
-- ----------------------------
DROP TABLE IF EXISTS `td_taskassign_log`;
CREATE TABLE `td_taskassign_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `taskid` int(11) DEFAULT NULL,
  `tassignid` int(11) DEFAULT NULL COMMENT '事务id',
  `logcontent` varchar(300) DEFAULT NULL COMMENT '流程事务描述',
  `createtime` datetime DEFAULT NULL COMMENT '流程流转时间',
  `status` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='督察督办流程记录';

-- ----------------------------
-- Records of td_taskassign_log
-- ----------------------------

-- ----------------------------
-- Table structure for td_taskassign_unit
-- ----------------------------
DROP TABLE IF EXISTS `td_taskassign_unit`;
CREATE TABLE `td_taskassign_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tassignid` int(11) NOT NULL COMMENT '交办事项时间表ID',
  `unitid` int(11) NOT NULL COMMENT '责任单位ID',
  `personid` int(11) NOT NULL COMMENT '督办责任人ID',
  `endtime` datetime DEFAULT NULL COMMENT '办结时限',
  `requirements` varchar(255) DEFAULT NULL COMMENT '办理要求',
  `status` smallint(6) DEFAULT '1' COMMENT '状态（1-新建未反馈；2-已反馈限期办理中；3-已反馈超期办理中；4-办理完成；）',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `createtime` datetime DEFAULT NULL,
  `istimely` smallint(4) DEFAULT NULL COMMENT '是否及时上报',
  PRIMARY KEY (`id`),
  KEY `unitid` (`unitid`),
  KEY `tassignid` (`tassignid`),
  KEY `personid` (`personid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办事项时间-责任单位责任人表';

-- ----------------------------
-- Records of td_taskassign_unit
-- ----------------------------

-- ----------------------------
-- Table structure for td_taskassign_unitdeal
-- ----------------------------
DROP TABLE IF EXISTS `td_taskassign_unitdeal`;
CREATE TABLE `td_taskassign_unitdeal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taunitid` int(11) DEFAULT NULL COMMENT '交办责任单位ID',
  `dealdesc` varchar(1000) DEFAULT NULL COMMENT '处理情况描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（0-未完成；1-完成）',
  `isdelay` tinyint(4) DEFAULT NULL COMMENT '是否延期（0-未延期；1-延期）',
  `pretime` datetime DEFAULT NULL COMMENT '延期前办结时限',
  `delaytime` datetime DEFAULT NULL COMMENT '延期时间做为新的办结时限',
  `pictures` varchar(255) DEFAULT NULL COMMENT '图片列表 包含原图，缩略图',
  `files` varchar(255) DEFAULT NULL COMMENT '文件列表',
  `creatorid` int(11) DEFAULT NULL COMMENT '创建人ID',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `finishtime` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`),
  KEY `taunitid` (`taunitid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交办事项时间-责任单位责任人-处理登记表';

-- ----------------------------
-- Records of td_taskassign_unitdeal
-- ----------------------------

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `aaa` int(11) NOT NULL AUTO_INCREMENT,
  `bbb` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aaa`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'gunsTest');
INSERT INTO `test` VALUES ('2', 'bizTest');
INSERT INTO `test` VALUES ('3', 'gunsTest');
INSERT INTO `test` VALUES ('4', 'bizTest');

-- ----------------------------
-- Table structure for t_tb_app_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_app_menu`;
CREATE TABLE `t_tb_app_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'appmenu类型',
  `statue` tinyint(4) NOT NULL DEFAULT '1',
  `pc_url` varchar(255) DEFAULT NULL COMMENT 'pc端html路径',
  `wap_url` varchar(255) DEFAULT NULL COMMENT '移动端html路径',
  `pc_icon` varchar(255) DEFAULT NULL,
  `wap_icon` varchar(255) DEFAULT NULL,
  `order` int(255) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='app菜单表';

-- ----------------------------
-- Records of t_tb_app_menu
-- ----------------------------
INSERT INTO `t_tb_app_menu` VALUES ('1', '1065491422498635793', '1', '1', 'http://do.scncry.com:888/Supervision_list.html', 'http://do.scncry.com:888/Supervision_list.html', 'http://do.scncry.com:888/img/icon/Group10@3x.png', 'http://do.scncry.com:888/img/icon/Group10@3x.png', '9');
INSERT INTO `t_tb_app_menu` VALUES ('2', '1065491422498635783', '1', '1', 'http://do.scncry.com:888/meet_index.html', 'http://do.scncry.com:888/meet_index.html', 'http://do.scncry.com:888/img/icon/Group11@3x.png', 'http://do.scncry.com:888/img/icon/Group11@3x.png', '8');
INSERT INTO `t_tb_app_menu` VALUES ('4', '1062589527937323015', '2', '1', 'http://do.scncry.com:888/tongji.html', 'http://do.scncry.com:888/tongji.html', 'http://do.scncry.com:888/img/icon/Group9@3x.png', 'http://do.scncry.com:888/img/icon/Group9@3x.png', '7');
INSERT INTO `t_tb_app_menu` VALUES ('5', '1065491422498635777', '2', '1', 'http://do.scncry.com:888/news.html', 'http://do.scncry.com:888/news.html', 'http://do.scncry.com:888/img/dbsx.png', 'http://do.scncry.com:888/img/report.png', '6');
INSERT INTO `t_tb_app_menu` VALUES ('6', '1065491422498635784', '1', '1', 'http://do.scncry.com:888/doc_index.html', 'http://do.scncry.com:888/doc_index.html', 'http://do.scncry.com:888/img/icon/Group12@3x.png', 'http://do.scncry.com:888/img/icon/Group12@3x.png', '5');
INSERT INTO `t_tb_app_menu` VALUES ('7', '1065491422498635785', '1', '1', 'http://do.scncry.com:888/infos_index.html', 'http://do.scncry.com:888/infos_index.html', 'http://do.scncry.com:888/img/icon/Group14@3x.png', 'http://do.scncry.com:888/img/icon/Group14@3x.png', '4');
INSERT INTO `t_tb_app_menu` VALUES ('8', '1065491422498635786', '1', '1', 'http://do.scncry.com:888/meet_add.html', 'http://do.scncry.com:888/404.html', 'http://do.scncry.com:888/img/icon/Group13@3x.png', 'http://do.scncry.com:888/img/icon/Group13@3x.png', '3');
INSERT INTO `t_tb_app_menu` VALUES ('9', '1065491422498635791', '1', '1', 'http://do.scncry.com:888/Supervision_addhis.html', 'http://do.scncry.com:888/Supervision_addhis.html', 'http://do.scncry.com:888/img/icon/Group15@3x.png', 'http://do.scncry.com:888/img/icon/Group15@3x.png', '11');
INSERT INTO `t_tb_app_menu` VALUES ('10', '1065491422498635792', '1', '1', 'http://do.scncry.com:888/Supervision_undeal.html', 'http://do.scncry.com:888/Supervision_undeal.html', 'http://do.scncry.com:888/img/icon/Group16@3x.png', 'http://do.scncry.com:888/img/icon/Group16@3x.png', '10');

-- ----------------------------
-- Table structure for t_tb_app_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_app_notice`;
CREATE TABLE `t_tb_app_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `now_status` int(11) DEFAULT NULL COMMENT '当前状态',
  `step` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `tel` varchar(22) DEFAULT NULL COMMENT '电话',
  `sendee` varchar(255) DEFAULT NULL COMMENT '接收人',
  `sender_id` int(11) DEFAULT NULL,
  `send_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

-- ----------------------------
-- Records of t_tb_app_notice
-- ----------------------------

-- ----------------------------
-- Table structure for t_tb_asset
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_asset`;
CREATE TABLE `t_tb_asset` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `file_size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '文件大小,单位B',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态;1:可用,0:不可用',
  `download_times` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '下载次数',
  `file_key` varchar(64) NOT NULL DEFAULT '' COMMENT '文件惟一码',
  `filename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `file_path` varchar(100) NOT NULL DEFAULT '' COMMENT '文件路径,相对于根目录,可以为url',
  `file_md5` varchar(32) NOT NULL DEFAULT '' COMMENT '文件md5值',
  `file_sha1` varchar(40) NOT NULL DEFAULT '',
  `suffix` varchar(10) NOT NULL DEFAULT '' COMMENT '文件后缀名,不包括点',
  `more` text COMMENT '其它详细信息,JSON格式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='资源表';

-- ----------------------------
-- Records of t_tb_asset
-- ----------------------------
INSERT INTO `t_tb_asset` VALUES ('1', '1', '10916680', '2019-04-08 14:59:57', '1', '0', '20190408/605b6f26-cd90-406c-b760-da1aeb899802.apk', 'app-release.apk', '20190408/605b6f26-cd90-406c-b760-da1aeb899802.apk', '', '', 'apk', null);

-- ----------------------------
-- Table structure for t_tb_company
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_company`;
CREATE TABLE `t_tb_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(150) DEFAULT NULL COMMENT '责任单位名称',
  `abb_title` varchar(50) DEFAULT NULL COMMENT '简称',
  `order` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '1',
  `adress` varchar(255) DEFAULT NULL COMMENT '地址',
  `tel` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `contact` varchar(50) DEFAULT NULL COMMENT '负责人',
  `contact_phone` varchar(50) DEFAULT NULL COMMENT '负责人电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '详细信息',
  `status` smallint(255) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COMMENT='责任单位';

-- ----------------------------
-- Records of t_tb_company
-- ----------------------------
INSERT INTO `t_tb_company` VALUES ('1', '区委办公室', '区委办公室', '0', '1', '', '', '', '', '', '1');
INSERT INTO `t_tb_company` VALUES ('2', '区纪委监委', '区纪委监委', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('3', '区委巡察办 ', '区委巡察办 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('4', '区委组织部', '区委组织部', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('5', '区委宣传部', '区委宣传部', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('6', '区委政法委', '区委政法委', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('7', '区委统战部', '区委统战部', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('8', '区委编办', '区委编办', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('9', '区直机关工委 ', '区直机关工委 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('10', '区委群众工作部', '区委群众工作部', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('11', '区人大常委会办公室', '区人大常委会办公室', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('12', '区政协办公室', '区政协办公室', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('13', '区人民法院', '区人民法院', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('14', '区人民检察院', '区人民检察院', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('15', '区人武部', '区人武部', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('16', '区政府办公室', '区政府办公室', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('17', '区发展改革局 ', '区发展改革局 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('18', '区教育局', '区教育局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('19', '区经济科技局', '区经济科技局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('20', '区民政局', '区民政局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('21', '区财政局', '区财政局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('22', ' 区人力资源社会保障局', ' 区人力资源社会保障局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('23', '区环境保护局', '区环境保护局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('24', '区城乡规划建设局', '区城乡规划建设局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('25', '区交通运输局', '区交通运输局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('26', '区水务局', '区水务局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('27', '区农牧业局', '区农牧业局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('28', '区商务粮食局', '区商务粮食局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('29', '区文化旅游广播影视局', '区文化旅游广播影视局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('30', '区卫生计生局', '区卫生计生局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('31', '区审计局', '区审计局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('32', '区城市管理局', '区城市管理局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('33', '区安全监管局', '区安全监管局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('34', '区统计局', '区统计局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('35', '区扶贫移民局 ', '区扶贫移民局 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('36', '区食品药品监管局', '区食品药品监管局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('37', '区工商和质量技术监督局', '区工商和质量技术监督局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('38', '区司法局', '区司法局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('39', '市公安局顺庆区分局', '市公安局顺庆区分局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('40', '区工业集中区管委会', '区工业集中区管委会', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('41', '区政务服务中心', '区政务服务中心', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('42', '区委党校', '区委党校', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('43', '区体育局', '区体育局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('44', ' 区供销合作社联合社', ' 区供销合作社联合社', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('45', '区机关事务管理局', '区机关事务管理局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('46', '区林业局', '区林业局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('47', '区档案局 ', '区档案局 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('48', '区地方志办公室', '区地方志办公室', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('49', '区投资促进合作局', '区投资促进合作局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('50', '西山风景区管理局', '西山风景区管理局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('51', '区西门市场管理办公室', '区西门市场管理办公室', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('52', '区公安消防大队 ', '区公安消防大队 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('53', '区公安消防中队', '区公安消防中队', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('54', '区税务局', '区税务局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('55', '区气象局', '区气象局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('56', '开发区税务局', '开发区税务局', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('57', '市房管局顺庆办事处', '市房管局顺庆办事处', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('58', '市国土资源局顺庆分局 ', '市国土资源局顺庆分局 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('59', '顺庆投资集团有限公司', '顺庆投资集团有限公司', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('60', '区总工会', '区总工会', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('61', '团区委 ', '团区委 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('62', '南充市职工大学', '南充市职工大学', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('63', '区妇联', '区妇联', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('64', '区工商联', '区工商联', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('65', '区科协 ', '区科协 ', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('66', ' 区残联', ' 区残联', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('67', '区红十字会', '区红十字会', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('68', '北城街道', '北城街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('69', '中城街道', '中城街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('70', '西城街道', '西城街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('71', '东南街道', '东南街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('72', '和平路街道', '和平路街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('73', '新建街道', '新建街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('74', '舞凤街道', '舞凤街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('75', '华凤街道', '华凤街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('76', '潆溪街道', '潆溪街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('77', '荆溪街道', '荆溪街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('78', '西山街道', '西山街道', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('79', '共兴镇', '共兴镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('80', '新复乡', '新复乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('81', '同仁乡', '同仁乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('82', '芦溪镇', '芦溪镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('83', '金台镇', '金台镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('84', '大林镇', '大林镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('85', '灯台乡', '灯台乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('86', '梵殿乡', '梵殿乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('87', '顺河乡', '顺河乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('88', '李家镇', '李家镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('89', '辉景镇', '辉景镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('90', '桂花乡', '桂花乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('91', '双桥镇', '双桥镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('92', '龙桂乡', '龙桂乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('93', '永丰镇', '永丰镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('94', '搬罾镇', '搬罾镇', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('95', '凤山乡', '凤山乡', '0', '0', null, null, null, null, null, '1');
INSERT INTO `t_tb_company` VALUES ('96', '渔溪乡', '渔溪乡', '0', '0', null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for t_tb_event_step
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_event_step`;
CREATE TABLE `t_tb_event_step` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` int(10) DEFAULT '0' COMMENT '上级id',
  `status` int(10) DEFAULT NULL COMMENT '状态',
  `event_type` int(10) DEFAULT NULL COMMENT '事务类型id',
  `step` varchar(255) DEFAULT NULL COMMENT '步骤说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='事务类型步骤管理';

-- ----------------------------
-- Records of t_tb_event_step
-- ----------------------------
INSERT INTO `t_tb_event_step` VALUES ('1', '0', '1', '1', '未反馈');
INSERT INTO `t_tb_event_step` VALUES ('2', '0', '2', '1', '已反馈办理中');
INSERT INTO `t_tb_event_step` VALUES ('3', '0', '3', '1', '部分完成');
INSERT INTO `t_tb_event_step` VALUES ('4', '0', '4', '1', '全部完成');
INSERT INTO `t_tb_event_step` VALUES ('5', '0', '5', '1', '事项归档');
INSERT INTO `t_tb_event_step` VALUES ('6', '0', '6', '1', '人为关闭');
INSERT INTO `t_tb_event_step` VALUES ('7', '0', '1', '2', '启用');
INSERT INTO `t_tb_event_step` VALUES ('8', '0', '0', '2', '停用');
INSERT INTO `t_tb_event_step` VALUES ('9', '0', '1', '3', '启用');
INSERT INTO `t_tb_event_step` VALUES ('10', '0', '0', '3', '停用');
INSERT INTO `t_tb_event_step` VALUES ('11', '0', '1', '4', '启用');
INSERT INTO `t_tb_event_step` VALUES ('12', '0', '0', '4', '停用');

-- ----------------------------
-- Table structure for t_tb_event_type
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_event_type`;
CREATE TABLE `t_tb_event_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `event_type` varchar(255) DEFAULT NULL COMMENT '事务类型',
  `report_alias` varchar(255) DEFAULT NULL COMMENT '报表别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='事件类型';

-- ----------------------------
-- Records of t_tb_event_type
-- ----------------------------
INSERT INTO `t_tb_event_type` VALUES ('1', '督察督办', '区委主要领导督办事项落实情况');
INSERT INTO `t_tb_event_type` VALUES ('2', '会议督察', '区委会议召开情况');
INSERT INTO `t_tb_event_type` VALUES ('3', '公文督察', '区委公文运转情况');
INSERT INTO `t_tb_event_type` VALUES ('4', '信息督察', '	区委信息工作情况');

-- ----------------------------
-- Table structure for t_tb_leadership
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_leadership`;
CREATE TABLE `t_tb_leadership` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) DEFAULT NULL COMMENT '领导姓名',
  `department` varchar(255) DEFAULT NULL COMMENT '部门',
  `title` varchar(255) DEFAULT NULL COMMENT '职务',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领导列表';

-- ----------------------------
-- Records of t_tb_leadership
-- ----------------------------

-- ----------------------------
-- Table structure for t_tb_unit_type
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_unit_type`;
CREATE TABLE `t_tb_unit_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='单位类型';

-- ----------------------------
-- Records of t_tb_unit_type
-- ----------------------------
INSERT INTO `t_tb_unit_type` VALUES ('1', '党委', '1');
INSERT INTO `t_tb_unit_type` VALUES ('2', '政府', '1');
INSERT INTO `t_tb_unit_type` VALUES ('3', '区委办', '1');
INSERT INTO `t_tb_unit_type` VALUES ('4', '人大', '1');
INSERT INTO `t_tb_unit_type` VALUES ('5', '政协', '1');
INSERT INTO `t_tb_unit_type` VALUES ('6', '街道办', '1');
INSERT INTO `t_tb_unit_type` VALUES ('7', '乡镇', '1');
INSERT INTO `t_tb_unit_type` VALUES ('8', '其他', '1');

-- ----------------------------
-- Table structure for t_tb_version_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_version_upgrade`;
CREATE TABLE `t_tb_version_upgrade` (
  `id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
  `app_type` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT '客户端设备id 1安卓pad 2安卓手机 3ios手机 4iospad',
  `version_id` smallint(4) unsigned DEFAULT '0' COMMENT '大版本号id',
  `version_mini` mediumint(8) unsigned DEFAULT '0' COMMENT '小版本号',
  `version_code` varchar(10) DEFAULT NULL COMMENT '版本标识 1.2',
  `type` tinyint(2) unsigned DEFAULT NULL COMMENT '是否升级  1升级，0不升级，2强制升级',
  `apk_url` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL COMMENT 'app更新内容',
  `upgrade_point` varchar(255) DEFAULT NULL COMMENT '升级提示',
  `status` tinyint(2) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='app版本信息';

-- ----------------------------
-- Records of t_tb_version_upgrade
-- ----------------------------
INSERT INTO `t_tb_version_upgrade` VALUES ('1', '2', '0', '0', '1.0.2', '2', '1', '修复bug', '请务必升级到最新版本', '1', null, null);

-- ----------------------------
-- Table structure for t_tb_work_type
-- ----------------------------
DROP TABLE IF EXISTS `t_tb_work_type`;
CREATE TABLE `t_tb_work_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(255) DEFAULT NULL COMMENT '交办事件类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='交办事件类型名称管理';

-- ----------------------------
-- Records of t_tb_work_type
-- ----------------------------
INSERT INTO `t_tb_work_type` VALUES ('1', '区委主要领导交办事项');
INSERT INTO `t_tb_work_type` VALUES ('2', '常委会议定事项');
INSERT INTO `t_tb_work_type` VALUES ('3', '全区重大项目');
INSERT INTO `t_tb_work_type` VALUES ('4', '重要工作');
INSERT INTO `t_tb_work_type` VALUES ('5', '临时工作限时办结');

-- ----------------------------
-- Procedure structure for AnalysisReport
-- ----------------------------
DROP PROCEDURE IF EXISTS `AnalysisReport`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `AnalysisReport`(beforeTime varchar(32),afterTime varchar(32))
begin
	declare s_str varchar(2000);-- 题集合字符
	declare var_sql varchar(8000);-- 动态SQL语句
	declare var_param varchar(8000);-- 动态SQL语句
	declare var_col varchar(8000);
  -- 获取动态列
	SELECT 
    GROUP_CONCAT('Max(case itemdesc when \'',
        itemdesc,
        '\' then num else null end )as \'$',
        itemclass,
        itemdesc,
        '\''
        ORDER BY itemclass , id)
INTO var_param FROM
    td_checkitem
WHERE
    itemclass IN (2 , 3, 4); 
	
	--  SQL语句
	set var_sql=CONCAT("select comp.title as '$0单位',Totalnum as '$1交办事项总数',concat(case when Totalnum>0 then cast(DealSuccNum*100.0/Totalnum as decimal(18,2)) else 0.00 end,'%') '$1交办事项办结率(%)'
		,Totalnum-DealSuccNum as '$1未办结数',wjssb as '$1未及时上报数',case when DealSuccNum>0 then cast(DealDate*1.0/DealSuccNum as signed) else 0 end '$1办结平均时长(天数)',tab.*
	from 
    ( 
        select com.title,com.id,sum(case when taskunit.status=4 then 1 else 0 end) as DealSuccNum,sum(case when taskunit.id is
        not null then 1 else 0 end)Totalnum
        ,sum(case when taskunit.status=4 then cast(timestampdiff(SECOND,assign.assigntime,deal.finishtime)/86400 as
        signed) else 0 end)as DealDate
				,sum(case when taskunit.istimely=1 then 1 else 0 end)as wjssb
        from t_tb_company com
        left join td_taskassign_unit taskunit on taskunit.unitid=com.id  and taskunit.createtime between '", beforeTime,"' and '", afterTime,"'
				left join td_taskassign assign on  assign.id=taskunit.tassignid
				left join td_taskassign_unitdeal deal on deal.taunitid=taskunit.id and deal.status=1
        group by com.id,com.title
	 ) comp 
    left join 
	(
	select unitid,",var_param,"
		from (
		-- 会议
		select itemdesc,itemclass,meeting.unitid,ifnull(sum(meeting.checkvalue),0) as num
			from td_checkitem   item
			inner join td_meetingrec meeting on meeting.checkitemid=item.id
		 where meeting.createtime between '", beforeTime,"' and '", afterTime,"'
		 group by meeting.unitid,itemdesc,itemclass
		union all       
		-- 公文运转
		select item.itemdesc,itemclass,dorec.unitid,ifnull(sum(dorec.checkvalue),0) as num
			from td_docassignrec dorec 
			inner join  td_checkitem  item  on dorec.checkitemid=item.id
		 where dorec.createtime between '", beforeTime,"' and '", afterTime,"'
     group by item.itemdesc,dorec.unitid,itemclass
		union all            
		-- 信息
		select  item.itemdesc,itemclass,rec.unitid,ifnull(sum(rec.checkvalue),0) as num
			from td_infosrec rec 
			inner join  td_checkitem  item  on rec.checkitemid=item.id
		 where rec.createtime between '", beforeTime,"' and '", afterTime,"'
		 group by item.itemdesc,rec.unitid,itemclass
        )A
        group by unitid
	)tab 
    on tab.unitid=comp.id order by comp.id");

     


--  select var_sql;
	set @sql = var_sql;
	PREPARE s1 from @sql; 
EXECUTE s1; 
	deallocate prepare s1;


end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for fn_Gettimediff
-- ----------------------------
DROP FUNCTION IF EXISTS `fn_Gettimediff`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `fn_Gettimediff`(`beginTime` datetime,`endTime` datetime) RETURNS varchar(50) CHARSET utf8mb4
BEGIN
	declare days int;
	declare SumSeconds  int;
	DECLARE hours int;
	declare minuts int;
	declare seconds int;
	declare leave1 int;
	declare leave2 int;
	declare leave3 int;
	select TIMESTAMPDIFF(SECOND ,beginTime,endTime) into SumSeconds;
	select TIMESTAMPDIFF(day ,beginTime,endTime) into days;
	select  SumSeconds%(24*60*60)  into leave1;
	select  leave1/60/60  into hours;
	select  leave1%(60*60)  into leave2;
	select  leave2/60 into minuts;
	select  leave2%60 into seconds;

	select CAST((SumSeconds-minuts*60)/ 60 as SIGNED) into minuts;
	RETURN CONCAT(days,'天',hours,'时',minuts,'分',seconds,'秒');
END
;;
DELIMITER ;
