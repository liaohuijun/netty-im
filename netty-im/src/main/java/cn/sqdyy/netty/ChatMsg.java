package cn.sqdyy.netty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName ChatMsg
 * @Description websocket 用户消息结构
 * @Date: 2018/11/30 17:20
 * @Version 1.0.0
 */
@ApiModel(value = "websocket 用户消息结构", description = "websocket 用户消息结构")
@Data
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 3611169682695799175L;
    @ApiModelProperty(value = "发送方的用户id（小名片）", name = "sendId")
    private String sendId;
    @ApiModelProperty(value = "发送方的用户昵称", name = "sendNickname")
    private String sendNickname;
    @ApiModelProperty(value = "发送方的用户头像", name = "sendAvatar")
    private String sendAvatar;
    @ApiModelProperty(value = "接收方的用户id（小名片）", name = "acceptId")
    private String acceptId;
    @ApiModelProperty(value = "接收方的用户昵称", name = "acceptNickname")
    private String acceptNickname;
    @ApiModelProperty(value = "接收方的用户头像", name = "acceptAvatar")
    private String acceptAvatar;
    @ApiModelProperty(value = "聊天内容", name = "msg")
    private String msg;
    @ApiModelProperty(value = "用于消息的签收", name = "msgId")
    private String msgId;
}
