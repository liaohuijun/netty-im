package cn.sqdyy.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * description: TODO
 * date: 2018/12/1 16:01
 *
 * @author <a href="https://blog.sqdyy.cn">神奇的鸭鸭</a>
 */
public class NewChatMsgVO {
    @ApiModelProperty(value = "分布式系统唯一ID", name = "id")
    private String id;
    @ApiModelProperty(value = "发送方的用户id（小名片）", name = "sendUserId")
    private String sendUserId;
    @ApiModelProperty(value = "接收方的用户id（小名片）", name = "acceptUserId")
    private String acceptUserId;
    @ApiModelProperty(value = "用户消息", name = "msg")
    private String msg;
    @ApiModelProperty(value = "消息是否签收状态：签收1：未签收0", name = "signFlag")
    private Integer signFlag;
    @ApiModelProperty(value = "发送请求的时间", name = "createTime")
    private Long createTime;

    private String friendId;
    private String friendName;
    private String friendAvatarUrl;
}
