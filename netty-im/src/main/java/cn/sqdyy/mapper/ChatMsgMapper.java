package cn.sqdyy.mapper;

import cn.sqdyy.pojo.ChatMsg;
import cn.sqdyy.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ChatMsgMapper extends MyMapper<ChatMsg> {
    /**
     * 批量更新消息状态
     */
    void batchUpdateMsgSigned(List<String> msgIdList);

    /**
     * 获取历史消息，每次获取 10 条，根据时间向前
     */
    List<ChatMsg> getHistoryMsgs(@Param("userId") String userId, @Param("friendId") String friendId, @Param("lastMsgTime") Long lastMsgTime);
}
