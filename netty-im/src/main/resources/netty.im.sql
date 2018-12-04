-- 消息表
CREATE TABLE `chat_msg` (
  `id` varchar(64) NOT NULL,
  `send_id` varchar(64) NOT NULL  NOT NULL COMMENT '发送方的用户id（小名片）',
  `send_nickname` varchar(64) NOT NULL  NOT NULL COMMENT '发送方的用户昵称',
  `send_avatar` varchar(255) NOT NULL  NOT NULL COMMENT '发送方的用户头像',
  `accept_id` varchar(64) NOT NULL  NOT NULL COMMENT '接收方的用户id（小名片）',
  `accept_nickname` varchar(64) NOT NULL  NOT NULL COMMENT '接收方的用户昵称',
  `accept_avatar` varchar(255) NOT NULL  NOT NULL COMMENT '接收方的用户头像',
  `msg` varchar(400) NOT NULL COMMENT '用户消息',
  `sign_flag` int(1) NOT NULL COMMENT '消息是否签收状态：签收1：未签收0',
  `create_time` int(11) NOT NULL COMMENT '发送请求的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 最新消息表
CREATE TABLE `new_chat_msg` (
  `id` varchar(64) NOT NULL,
  `send_id` varchar(64) NOT NULL  NOT NULL COMMENT '发送方的用户id（小名片）',
  `send_nickname` varchar(64) NOT NULL  NOT NULL COMMENT '发送方的用户昵称',
  `send_avatar` varchar(255) NOT NULL  NOT NULL COMMENT '发送方的用户头像',
  `accept_id` varchar(64) NOT NULL  NOT NULL COMMENT '接收方的用户id（小名片）',
  `accept_nickname` varchar(64) NOT NULL  NOT NULL COMMENT '接收方的用户昵称',
  `accept_avatar` varchar(255) NOT NULL  NOT NULL COMMENT '接收方的用户头像',
  `msg` varchar(400) NOT NULL COMMENT '用户消息',
  `sign_flag` int(1) NOT NULL COMMENT '消息是否签收状态：签收1：未签收0',
  `create_time` int(11) NOT NULL COMMENT '发送请求的时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
