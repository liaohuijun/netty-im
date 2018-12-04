package cn.sqdyy.mapper;

import cn.sqdyy.pojo.NewChatMsg;
import cn.sqdyy.utils.MyMapper;

import java.util.List;

public interface NewChatMsgMapper extends MyMapper<NewChatMsg> {
    /**
     * 查询新消息 50 条
     */
    List<NewChatMsg> selectNewCharList(String userId);
}