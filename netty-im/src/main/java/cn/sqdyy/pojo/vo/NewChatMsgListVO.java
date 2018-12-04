package cn.sqdyy.pojo.vo;

import cn.sqdyy.pojo.NewChatMsg;
import lombok.Data;

import java.util.List;

/**
 * description: TODO
 * date: 2018/12/1 12:59
 *
 * @author <a href="https://blog.sqdyy.cn">神奇的鸭鸭</a>
 */
@Data
public class NewChatMsgListVO {
    List<NewChatMsg> newCharList;
    boolean unread;
}
