package cn.sqdyy.controller;

import cn.sqdyy.netty.ChatMsg;
import cn.sqdyy.netty.DataContent;
import cn.sqdyy.netty.UserChannelRel;
import cn.sqdyy.pojo.NewChatMsg;
import cn.sqdyy.pojo.vo.NewChatMsgListVO;
import cn.sqdyy.service.UserService;
import cn.sqdyy.utils.JSONResult;
import cn.sqdyy.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static cn.sqdyy.netty.ChatHandler.users;

/**
 * description: TODO
 * date: 2018/12/1 9:52
 *
 * @author <a href="https://blog.sqdyy.cn">神奇的鸭鸭</a>
 */
@RestController
@RequestMapping("char")
@Api(value = "会话处理接口", tags = {"会话处理接口"})
public class CharController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    @ApiOperation("通过接口发送消息,用于调试")
    public JSONResult sendMsg(@RequestBody ChatMsg chatMsg) throws Exception {
        // 保存发送的消息
        String msgId = userService.saveMsg(chatMsg);
        chatMsg.setMsgId(msgId);

        DataContent dataContentMsg = new DataContent();
        dataContentMsg.setChatMsg(chatMsg);

        // 发送消息
        // 从全局用户 Channel 关系中获取接收方的 channel
        String receiverId = chatMsg.getAcceptId();
        Channel receiverChannel = UserChannelRel.get(receiverId);
        if (receiverChannel != null) {
            // 当 receiverChannel 不为空的时候，从 ChannelGroup 去查找对应的 channel 是否存在
            Channel findChannel = users.find(receiverChannel.id());
            if (findChannel != null) {
                // 用户在线 -> 推送给用户
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
            }
        }
        return JSONResult.ok();
    }

    @GetMapping("/getNewCharList")
    @ApiOperation("获取查询最近会话列表. 只拿50条记录")
    public JSONResult getNewCharList(@RequestParam String userId) throws Exception {
        if(StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("userId 不能为空");
        }
        // 查询最近会话列表
        List<NewChatMsg> newCharList = userService.getNewCharList(userId);
        NewChatMsgListVO newChatMsgListVO = new NewChatMsgListVO();
        newChatMsgListVO.setNewCharList(newCharList);
        newChatMsgListVO.setUnread(false);
        newCharList.forEach((charMsg)-> {
            if(charMsg.getSignFlag() == 0) {
                newChatMsgListVO.setUnread(true);
            }
        });
        return JSONResult.ok(newChatMsgListVO);
    }

    @GetMapping("/getHistoryMsgs")
    @ApiOperation("获取用户历史消息")
    public JSONResult getHistoryMsgs(@RequestParam String userId, @RequestParam String friendId,@RequestParam Long lastMsgTime) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(friendId)) {
            return JSONResult.errorMsg("userId friendId 不能为空");
        }
        List<cn.sqdyy.pojo.ChatMsg> historyMsgs = userService.getHistoryMsgs(userId, friendId, lastMsgTime);
        return JSONResult.ok(historyMsgs);
    }
}
