-- 创建库
create database if not exists bi;

use bi;

# 创表语句
CREATE TABLE t_di_bind_device (
                                  id INT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一id',
                                  user_id VARCHAR(20) NOT NULL COMMENT '用户手机号，一个用户可绑定多个设备',
                                  mac_id CHAR(16) NOT NULL UNIQUE COMMENT '设备id，设备id长度一般为16位，每个设备都有唯一id',
                                  mac_type VARCHAR(50) COMMENT '设备型号',
                                  channel_id VARCHAR(50) COMMENT '设备渠道',
                                  bind TINYINT(1) NOT NULL COMMENT '设备绑定状态，设备绑定：1；设备解绑：0。一个设备仅有一个主绑定用户',
                                  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间'
);

CREATE TABLE t_device_attributes (
                                     mac_id CHAR(16) NOT NULL UNIQUE COMMENT '设备id，设备id长度一般为16位，每个设备都有唯一id',
                                     mac_type VARCHAR(50) COMMENT '设备型号',
                                     supplier_id VARCHAR(50) COMMENT '厂商id',
                                     supplier_name VARCHAR(100) COMMENT '厂商名称',
                                     channel_name VARCHAR(100) COMMENT '渠道名称',
                                     channel_id VARCHAR(50) COMMENT '渠道id',
                                     province_code VARCHAR(10) COMMENT '省码',
                                     province_name VARCHAR(100) COMMENT '省份名称',
                                     PRIMARY KEY (mac_id)
);

CREATE TABLE t_user_packages (
                                 order_id CHAR(32) NOT NULL PRIMARY KEY COMMENT '订单id，每个订单都有唯一id',
                                 mac_id CHAR(16) NOT NULL COMMENT '设备id，设备id长度一般为16位，每个设备都有唯一id',
                                 order_user VARCHAR(20) NOT NULL COMMENT '订购手机号，一个设备可绑定一个手机号、开通一种套餐',
                                 province_code VARCHAR(10) COMMENT '省码',
                                 city_code VARCHAR(10) COMMENT '市码',
                                 created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 package_id CHAR(32) NOT NULL COMMENT '套餐id，每一类套餐具有唯一id，长度一般为32位',
                                 storage_type TINYINT(1) NOT NULL COMMENT '套餐类型，全天套餐：1，事件套餐：2',
                                 state TINYINT(1) NOT NULL COMMENT '套餐状态，当前套餐处于生效状态：1，当前套餐处于失效状态：0'
);

CREATE TABLE t_device_online_status (
                                        id INT PRIMARY KEY AUTO_INCREMENT COMMENT '序号',
                                        mac_id CHAR(16) NOT NULL COMMENT '设备id',
                                        login TINYINT(1) NOT NULL COMMENT '设备上下线状态，设备上线：1，设备下线：0',
                                        created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
);


CREATE TABLE t_mobile_surveillance_events (
                                              event_serial_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '埋点记录id',
                                              event_id VARCHAR(100) NOT NULL COMMENT '埋点名称，包括Live_watch_time(直播观看时长),icloud_watch_time(云回放观看时长云回放观看时长),Live_play_success(直播成功),Live_play_fail(直播失败),live_play_loading_time(直播加载时间)',
                                              event_duration BIGINT COMMENT '事件持续时长，单位：毫秒；根据event_id的不同含义，可能是用户观看时长、直播加载时长或为0',
                                              event_created TIMESTAMP NOT NULL COMMENT '事件创建时间',
                                              user_id VARCHAR(20) COMMENT '用户手机号',
                                              phone_type VARCHAR(50) COMMENT '手机类型',
                                              app_version VARCHAR(20) COMMENT '和家亲app版本号',
                                              network_type VARCHAR(10) COMMENT '网络，分为WIFI、4G等',
                                              mac_id CHAR(16) NOT NULL COMMENT '设备id',
                                              extra TEXT COMMENT '其他',
                                              created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录的创建时间',
                                              modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '该条记录的修改时间'
);

CREATE TABLE user_query (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT NOT NULL,
                            prompt VARCHAR(255) NOT NULL,
                            generate_sql DATETIME NOT NULL,
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                            update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_account (
                              user_id INT AUTO_INCREMENT PRIMARY KEY,
                              username VARCHAR(50) NOT NULL UNIQUE,
                              password VARCHAR(255) NOT NULL,
                              email VARCHAR(100) UNIQUE,
                              register_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                              last_login_time DATETIME DEFAULT NULL,
                              is_active BOOLEAN DEFAULT TRUE
);