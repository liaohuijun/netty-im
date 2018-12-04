//index.js
const app = getApp()
const webSocket = require('../../utils/netty-im.js');

Page({
  data: {
  },
  onLoad: function () {
    // 创建连接
    webSocket.connectSocket();
    // 设置接收消息回调
    webSocket.onSocketMessageCallback = this.onSocketMessageCallback;
  },
  // socket收到的信息回调
  onSocketMessageCallback: function (msg) {
    console.log('收到消息回调', msg)
  },
  sendMsg: function() {
    var avater = "https://wx.qlogo.cn/mmopen/vi_32/vcFFe9Kg2Q0YfwQuaib7sHlSI65nKraL7ibuQvq1icbkrumuWDlbSM51PShPVoialzlpkiaKudLtLia0JLeWUmFprMjg/132";
    var chatMsg = new webSocket.ChatMsg('100001', '神奇的鸭鸭', avater, '100002', '神奇的二鸭', avater, '$0001$hello magic duck5', null);
    var dataContent = new webSocket.DataContent(webSocket.CHAT, chatMsg, null);
    webSocket.sendSocketMessage({
      msg: JSON.stringify(dataContent),
      success: res => console.log('发送成功'),
      fail: res => console.log('发送失败')
    });
  },
  dataContentSign: function() {
    // chatMsg.msgId: 18120388H50TPSW0  sendId 100002 acceptId 100001
    var dataContent = new webSocket.DataContent(webSocket.SIGNED, null, '18120388H50TPSW0,1812038H7KZ09MA8');
    webSocket.sendSocketMessage({
      msg: JSON.stringify(dataContent),
      success: res => console.log('消息签收上报成功'),
      fail: res => console.log('消息签收上报失败')
    });
  },
  getNewCharList: function() {
    wx.request({
      url: webSocket.serverUrl + 'char/getNewCharList?userId=100001',
      success: res => {
        console.log(res)
      }
    })
  },
  getCharList: function() {
    wx.request({
      url: webSocket.serverUrl + 'char/getHistoryMsgs?userId=100001&friendId=100002&lastMsgTime=1543825561',
      success: res => {
        console.log(res)
      }
    })
  },
  onUnload: function (options) {
    // 页面销毁时关闭连接
    webSocket.closeSocket();
  }
})
