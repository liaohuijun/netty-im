package cn.sqdyy.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "new_chat_msg")
@ApiModel(value = "最新消息存储", description = "最新消息存储")
public class NewChatMsg {
    @Id
    @ApiModelProperty(value = "分布式系统唯一ID", name = "id")
    private String id;
    @Column(name = "send_id")
    @ApiModelProperty(value = "发送方的用户id（小名片）", name = "sendId")
    private String sendId;
    @Column(name = "send_nickname")
    @ApiModelProperty(value = "发送方的用户昵称", name = "sendNickname")
    private String sendNickname;
    @Column(name = "send_avatar")
    @ApiModelProperty(value = "发送方的用户头像", name = "sendAvatar")
    private String sendAvatar;
    @Column(name = "accept_id")
    @ApiModelProperty(value = "接收方的用户id（小名片）", name = "acceptId")
    private String acceptId;
    @Column(name = "accept_nickname")
    @ApiModelProperty(value = "接收方的用户昵称", name = "acceptNickname")
    private String acceptNickname;
    @Column(name = "accept_avatar")
    @ApiModelProperty(value = "接收方的用户头像", name = "acceptAvatar")
    private String acceptAvatar;
    @ApiModelProperty(value = "用户消息", name = "msg")
    private String msg;
    @Column(name = "sign_flag")
    @ApiModelProperty(value = "消息是否签收状态：签收1：未签收0", name = "signFlag")
    private Integer signFlag;
    @Column(name = "create_time")
    @ApiModelProperty(value = "发送请求的时间", name = "createTime")
    private Long createTime;
}