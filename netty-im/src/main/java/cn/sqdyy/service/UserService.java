package cn.sqdyy.service;

import cn.sqdyy.netty.ChatMsg;
import cn.sqdyy.pojo.NewChatMsg;
import cn.sqdyy.pojo.vo.NewChatMsgVO;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName UserService
 * @Description UserService
 * @Date: 2018/11/30 12:54
 * @Version 1.0.0
 */
public interface UserService {
    /**
     * 保存聊天消息到数据库
     */
    String saveMsg(ChatMsg chatMsg);

    /**
     * 批量签收消息
     */
     void updateMsgSigned(List<String> msgIdList);

    /**
     * 获取最新会话消息列表
     */
    List<NewChatMsg> getNewCharList(String userId);

    /**
     * 获取历史消息记录，每次获取10条
     */
    List<cn.sqdyy.pojo.ChatMsg> getHistoryMsgs(String userId, String friendId, Long lastMsgTime);
}
