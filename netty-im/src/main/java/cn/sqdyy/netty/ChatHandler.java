package cn.sqdyy.netty;

import cn.sqdyy.SpringUtil;
import cn.sqdyy.enums.MsgActionEnum;
import cn.sqdyy.service.UserService;
import cn.sqdyy.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName ChatHandler
 * @Description 处理消息的 handler
 * TextWebSocketFrame： 在 netty 中，是用于为 websocket 专门处理文本的对象，frame 是消息的载体
 * @Date: 2018/11/30 11:01
 * @Version 1.0.0
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的 channle
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel currentChannel = ctx.channel();
        // 1 获取客户端发来的消息
        String content = msg.text();
         DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        // 2 判断消息类型，根据不同的类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {
            // 2.1 当 websocket 第一次 open 的时候，初始化 channel，把 channel 和 userid 关联起来
            String senderId = dataContent.getChatMsg().getSendId();
            UserChannelRel.put(senderId, currentChannel);
        } else if (action == MsgActionEnum.CHAT.type) {
            // 2.2 聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态 [未签收]
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            String receiverId = chatMsg.getAcceptId();
            String senderId = chatMsg.getSendId();

            // 保存消息到数据库，并且标记为 未签收
            UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
            String msgId = userService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);
            // 发送消息
            // 从全局用户 Channel 关系中获取接收方的 channel
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel != null) {
                // 当 receiverChannel 不为空的时候，从 ChannelGroup 去查找对应的 channel 是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    // 用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                }
            }
        } else if (action == MsgActionEnum.SIGNED.type) {
            //  2.3  签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态 [已签收]
            UserService userService = (UserService)SpringUtil.getBean("userServiceImpl");
            // 扩展字段在 signed 类型的消息中，代表需要去签收的消息 id，逗号间隔
            String msgIdsStr = dataContent.getExtand();
            String msgIds[] = msgIdsStr.split(",");
            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgIds) {
                if (StringUtils.isNotBlank(mid)) {
                    msgIdList.add(mid);
                }
            }
            // 批量签收
            if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {
                userService.updateMsgSigned(msgIdList);
            }
        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            //  2.4  心跳类型的消息
            log.info("收到来自 channel 为 [" + currentChannel + "] 的心跳包...");
        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的 channle，并且放到 ChannelGroup 中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        log.info("客户端被移除，channel Id 为：" + channelId);
        // 当触发 handlerRemoved，ChannelGroup 会自动移除对应客户端的 channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭 channel），随后从 ChannelGroup 中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
