package cn.sqdyy.service.impl;

import cn.sqdyy.enums.MsgSignFlagEnum;
import cn.sqdyy.mapper.ChatMsgMapper;
import cn.sqdyy.mapper.NewChatMsgMapper;
import cn.sqdyy.netty.ChatMsg;
import cn.sqdyy.pojo.NewChatMsg;
import cn.sqdyy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName UserServiceImpl
 * @Description UserServiceImpl
 * @Date: 2018/11/30 12:55
 * @Version 1.0.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private ChatMsgMapper chatMsgMapper;
    @Autowired
    private NewChatMsgMapper newChatMsgMapper;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(ChatMsg chatMsg) {
        // 1 保存到消息表中，设置状态为未读
        cn.sqdyy.pojo.ChatMsg msgDB = new cn.sqdyy.pojo.ChatMsg();
        String msgId = sid.nextShort();
        msgDB.setId(msgId);
        msgDB.setAcceptId(chatMsg.getAcceptId());
        msgDB.setAcceptNickname(chatMsg.getAcceptNickname());
        msgDB.setAcceptAvatar(chatMsg.getAcceptAvatar());
        msgDB.setSendId(chatMsg.getSendId());
        msgDB.setSendNickname(chatMsg.getSendNickname());
        msgDB.setSendAvatar(chatMsg.getSendAvatar());
        msgDB.setCreateTime(Instant.now().getEpochSecond());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDB.setMsg(chatMsg.getMsg());
        chatMsgMapper.insert(msgDB);
        // 2 更新到最新消息表中，设置状态为未读 查找消息表中双方有无旧记录
        Example example = new Example(NewChatMsg.class);
        example.createCriteria()
                .andEqualTo("sendId",chatMsg.getSendId())
                .andEqualTo("acceptId", chatMsg.getAcceptId());
        example.or(example.createCriteria()
                .andEqualTo("acceptId",chatMsg.getSendId())
                .andEqualTo("sendId", chatMsg.getAcceptId())
        );
        NewChatMsg oldChatMsg = newChatMsgMapper.selectOneByExample(example);
        if(oldChatMsg == null) {
            NewChatMsg newChatMsg = new NewChatMsg();
            BeanUtils.copyProperties(msgDB, newChatMsg);
            newChatMsgMapper.insert(newChatMsg);
        }else {
            oldChatMsg.setAcceptId(chatMsg.getAcceptId());
            oldChatMsg.setAcceptNickname(chatMsg.getAcceptNickname());
            oldChatMsg.setAcceptAvatar(chatMsg.getAcceptAvatar());
            oldChatMsg.setSendId(chatMsg.getSendId());
            oldChatMsg.setSendNickname(chatMsg.getSendNickname());
            oldChatMsg.setSendAvatar(chatMsg.getSendAvatar());
            oldChatMsg.setCreateTime(Instant.now().getEpochSecond());
            oldChatMsg.setSignFlag(MsgSignFlagEnum.unsign.type);
            oldChatMsg.setMsg(chatMsg.getMsg());
            newChatMsgMapper.updateByPrimaryKeySelective(oldChatMsg);
        }
        return msgId;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.batchUpdateMsgSigned(msgIdList);
    }

    @Override
    public List<NewChatMsg> getNewCharList(String userId) {
        return newChatMsgMapper.selectNewCharList(userId);
    }

    @Override
    public List<cn.sqdyy.pojo.ChatMsg> getHistoryMsgs(String userId, String friendId, Long lastMsgTime) {
        return chatMsgMapper.getHistoryMsgs(userId, friendId, lastMsgTime);
    }
}
