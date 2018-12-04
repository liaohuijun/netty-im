// socket已经连接成功
var socketOpen = false
// socket已经调用关闭function
var socketClose = false
// socket发送的消息队列
var socketMsgQueue = []
// 判断心跳变量
var heart = ''
// 心跳失败次数
var heartBeatFailCount = 0
// 终止心跳
var heartBeatTimeOut = null;
// 终止重新连接
var connectSocketTimeOut = null;

var webSocket = {
  /** netty服务后端发布的url地址 */
  nettyServerUrl: 'wss://47.107.33.156:8081/ws',
  /** 后端服务发布的url地址 */
  serverUrl: 'http://47.107.33.156:8080/',
  /**  图片服务器的url地址 */
  imgServerUrl: 'http://119.29.141.219/sqdyy/',

  /** 发送消息的动作 */
  CONNECT: 1, // 第一次(或重连)初始化连接
  CHAT: 2, // 聊天消息
  SIGNED: 3, // 消息签收
  KEEPALIVE: 4, // 客户端保持心跳

  /**
   * 构建消息 DataContent 模型对象
   * @param {Object} action
   * @param {Object} chatMsg
   * @param {Object} extand
   */
  DataContent: function (action, chatMsg, extand) {
    this.action = action;
    this.chatMsg = chatMsg;
    this.extand = extand;
  },

  /**
 * 和后端的 ChatMsg 聊天模型对象保持一致
 */
  ChatMsg: function (sendId, sendNickname, sendAvatar, acceptId, acceptNickname, acceptAvatar, msg, msgId) {
    this.sendId = sendId;
    this.sendNickname = sendNickname;
    this.sendAvatar = sendAvatar;
    this.acceptId = acceptId;
    this.acceptNickname = acceptNickname;
    this.acceptAvatar = acceptAvatar;
    this.msg = msg;
    this.msgId = msgId;
  },

  /**
   * 单个聊天记录的对象
   * @param {Object} myId
   * @param {Object} friendId
   * @param {Object} msg
   * @param {Object} flag
   */
  ChatHistory: function (myId, friendId, msg, flag) {
    this.myId = myId;
    this.friendId = friendId;
    this.msg = msg;
    this.flag = flag;
  },

  /**
   * 创建一个 WebSocket 连接
   * @param {options} 
   *   url          String    是    开发者服务器接口地址，必须是 wss 协议，且域名必须是后台配置的合法域名
   *   header        Object    否    HTTP Header , header 中不能设置 Referer
   *   method        String    否    默认是GET，有效值：OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
   *   protocols    StringArray    否    子协议数组    1.4.0
   *   success      Function    否    接口调用成功的回调函数
   *   fail         Function    否    接口调用失败的回调函数
   *   complete      Function    否    接口调用结束的回调函数（调用成功、失败都会执行）
   */
  connectSocket: function (options) {
    wx.showLoading({
      title: '',
      mask: true,
    })
    socketOpen = false
    socketClose = false
    socketMsgQueue = []
    wx.connectSocket({
      url: webSocket.nettyServerUrl,
      success: function (res) {
        if (options) {
          // 成功回调
          options.success && options.success(res);
        }
      },
      fail: function (res) {
        if (options) {
          // 失败回调
          options.fail && options.fail(res);
        }
      }
    })
  },

  /**
   * 通过 WebSocket 连接发送数据
   * @param {options} 
   *   data    String / ArrayBuffer    是    需要发送的内容
   *   success    Function    否    接口调用成功的回调函数
   *   fail    Function    否    接口调用失败的回调函数
   *   complete    Function    否    接口调用结束的回调函数（调用成功、失败都会执行）
   */
  sendSocketMessage: function (options) {
    // 如果当前websocket的状态是已打开，则直接发送， 否则重连
    if (socketOpen) {
      wx.sendSocketMessage({
        data: options!=null ? options.msg : options,
        success: function (res) {
          if (options) {
            options.success && options.success(res);
          }
        },
        fail: function (res) {
          if (options) {
            options.fail && options.fail(res);
          }
        }
      })
    } else {
      socketMsgQueue.push(options.msg)
    }
  },

  /**
   * 关闭 WebSocket 连接。
   * @param {options} 
   *   code    Number    否    一个数字值表示关闭连接的状态号，表示连接被关闭的原因。如果这个参数没有被指定，默认的取值是1000 （表示正常连接关闭）
   *   reason    String    否    一个可读的字符串，表示连接被关闭的原因。这个字符串必须是不长于123字节的UTF-8 文本（不是字符）
   *   fail    Function    否    接口调用失败的回调函数
   *   complete    Function    否    接口调用结束的回调函数（调用成功、失败都会执行）
   */
  closeSocket: function (options) {
    if (connectSocketTimeOut) {
      clearTimeout(connectSocketTimeOut);
      connectSocketTimeOut = null;
    }
    socketClose = true;
    var self = this;
    self.stopHeartBeat();
    wx.closeSocket({
      success: function (res) {
        console.log('WebSocket 已关闭！');
        if (options) {
          options.success && options.success(res);
        }
      },
      fail: function (res) {
        if (options) {
          options.fail && options.fail(res);
        }
      }
    })
  },

  // 收到消息回调
  onSocketMessageCallback: function (msg) {

  },

  // 开始心跳
  startHeartBeat: function () {
    console.log('socket 开始心跳')
    var self = this;
    heart = 'heart';
    self.heartBeat();
  },

  // 结束心跳
  stopHeartBeat: function () {
    console.log('socket 结束心跳')
    var self = this;
    heart = '';
    if (heartBeatTimeOut) {
      clearTimeout(heartBeatTimeOut);
      heartBeatTimeOut = null;
    }
    if (connectSocketTimeOut) {
      clearTimeout(connectSocketTimeOut);
      connectSocketTimeOut = null;
    }
  },

  // 心跳
  heartBeat: function () {
    var self = this;
    if (!heart) {
      return;
    }
    var dataContent = new self.DataContent(self.KEEPALIVE, null, null);
    self.sendSocketMessage({
      msg: JSON.stringify(dataContent),
      success: function (res) {
        console.log('socket心跳成功');
        if (heart) {
          heartBeatTimeOut = setTimeout(() => {
            self.heartBeat();
          }, 7000);
        }
      },
      fail: function (res) {
        console.log('socket心跳失败');
        if (heartBeatFailCount > 2) {
          // 重连
          self.connectSocket();
        }
        if (heart) {
          heartBeatTimeOut = setTimeout(() => {
            self.heartBeat();
          }, 7000);
        }
        heartBeatFailCount++;
      },
    });
  }
}

// 监听WebSocket连接打开事件。callback 回调函数
wx.onSocketOpen(function (res) {
  console.log('WebSocket连接已打开！')
  wx.hideLoading();
  // 如果已经调用过关闭 function
  if (socketClose) {
    webSocket.closeSocket();
  } else {
    socketOpen = true
    for (var i = 0; i < socketMsgQueue.length; i++) {
      webSocket.sendSocketMessage(socketMsgQueue[i])
    }
    socketMsgQueue = []
    webSocket.startHeartBeat();
  }
  var chatMsg = new webSocket.ChatMsg('100001', null, null, null, null, null, null, null);
  var dataContent = new webSocket.DataContent(webSocket.CONNECT, chatMsg, null);
  webSocket.sendSocketMessage({msg: JSON.stringify(dataContent),
    success: res=>console.log('登录成功'),
    fail: res=>console.log('登录失败')
  });
})

// 监听WebSocket错误。
wx.onSocketError(function (res) {
  console.log('WebSocket连接打开失败，请检查！', res)
})

// 监听WebSocket接受到服务器的消息事件。
wx.onSocketMessage(function (res) {
  console.log('收到服务器内容：' + res.data)
  webSocket.onSocketMessageCallback(res.data)
})

// 监听WebSocket关闭。
wx.onSocketClose(function (res) {
  console.log('WebSocket 已关闭！')
  if (!socketClose) {
    clearTimeout(connectSocketTimeOut)
    connectSocketTimeOut = setTimeout(() => {
      webSocket.connectSocket();
    }, 3000);
  }
})

module.exports = webSocket;