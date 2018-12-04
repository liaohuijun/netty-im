package cn.sqdyy.enums;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName MsgActionEnum
 * @Description 消息签收状态 枚举
 * @Date: 2018/11/30 17:25
 * @Version 1.0.0
 */
public enum MsgSignFlagEnum {
    unsign(0, "未签收"),
    signed(1, "已签收");

    public final Integer type;
    public final String content;

    MsgSignFlagEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
