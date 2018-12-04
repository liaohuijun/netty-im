package cn.sqdyy.enums;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName MsgActionEnum
 * @Description 发送消息的动作 枚举
 * @Date: 2018/11/30 17:25
 * @Version 1.0.0
 */
public enum MsgActionEnum {
    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "发送消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
