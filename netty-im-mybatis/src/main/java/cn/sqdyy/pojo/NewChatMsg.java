package cn.sqdyy.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "new_chat_msg")
public class NewChatMsg {
    @Id
    private String id;

    /**
     * 发送方的用户id（小名片）
     */
    @Column(name = "send_id")
    private String sendId;

    /**
     * 发送方的用户昵称
     */
    @Column(name = "send_nickname")
    private String sendNickname;

    /**
     * 发送方的用户头像
     */
    @Column(name = "send_avatar")
    private String sendAvatar;

    /**
     * 接收方的用户id（小名片）
     */
    @Column(name = "accept_id")
    private String acceptId;

    /**
     * 接收方的用户昵称
     */
    @Column(name = "accept_nickname")
    private String acceptNickname;

    /**
     * 接收方的用户头像
     */
    @Column(name = "accept_avatar")
    private String acceptAvatar;

    /**
     * 用户消息
     */
    private String msg;

    /**
     * 消息是否签收状态：签收1：未签收0
     */
    @Column(name = "sign_flag")
    private Integer signFlag;

    /**
     * 发送请求的时间戳
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取发送方的用户id（小名片）
     *
     * @return send_id - 发送方的用户id（小名片）
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * 设置发送方的用户id（小名片）
     *
     * @param sendId 发送方的用户id（小名片）
     */
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    /**
     * 获取发送方的用户昵称
     *
     * @return send_nickname - 发送方的用户昵称
     */
    public String getSendNickname() {
        return sendNickname;
    }

    /**
     * 设置发送方的用户昵称
     *
     * @param sendNickname 发送方的用户昵称
     */
    public void setSendNickname(String sendNickname) {
        this.sendNickname = sendNickname;
    }

    /**
     * 获取发送方的用户头像
     *
     * @return send_avatar - 发送方的用户头像
     */
    public String getSendAvatar() {
        return sendAvatar;
    }

    /**
     * 设置发送方的用户头像
     *
     * @param sendAvatar 发送方的用户头像
     */
    public void setSendAvatar(String sendAvatar) {
        this.sendAvatar = sendAvatar;
    }

    /**
     * 获取接收方的用户id（小名片）
     *
     * @return accept_id - 接收方的用户id（小名片）
     */
    public String getAcceptId() {
        return acceptId;
    }

    /**
     * 设置接收方的用户id（小名片）
     *
     * @param acceptId 接收方的用户id（小名片）
     */
    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    /**
     * 获取接收方的用户昵称
     *
     * @return accept_nickname - 接收方的用户昵称
     */
    public String getAcceptNickname() {
        return acceptNickname;
    }

    /**
     * 设置接收方的用户昵称
     *
     * @param acceptNickname 接收方的用户昵称
     */
    public void setAcceptNickname(String acceptNickname) {
        this.acceptNickname = acceptNickname;
    }

    /**
     * 获取接收方的用户头像
     *
     * @return accept_avatar - 接收方的用户头像
     */
    public String getAcceptAvatar() {
        return acceptAvatar;
    }

    /**
     * 设置接收方的用户头像
     *
     * @param acceptAvatar 接收方的用户头像
     */
    public void setAcceptAvatar(String acceptAvatar) {
        this.acceptAvatar = acceptAvatar;
    }

    /**
     * 获取用户消息
     *
     * @return msg - 用户消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置用户消息
     *
     * @param msg 用户消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取消息是否签收状态：签收1：未签收0
     *
     * @return sign_flag - 消息是否签收状态：签收1：未签收0
     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * 设置消息是否签收状态：签收1：未签收0
     *
     * @param signFlag 消息是否签收状态：签收1：未签收0
     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * 获取发送请求的时间戳
     *
     * @return create_time - 发送请求的时间戳
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发送请求的时间戳
     *
     * @param createTime 发送请求的时间戳
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}