package cn.sqdyy.pojo;

import javax.persistence.*;

public class Users {
    @Id
    private String id;

    /**
     * 用户id（小名片）
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

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
     * 获取用户id（小名片）
     *
     * @return user_id - 用户id（小名片）
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id（小名片）
     *
     * @param userId 用户id（小名片）
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取用户头像
     *
     * @return avatar_url - 用户头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置用户头像
     *
     * @param avatarUrl 用户头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}