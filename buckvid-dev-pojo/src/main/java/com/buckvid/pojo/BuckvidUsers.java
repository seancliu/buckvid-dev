package com.buckvid.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "User Obj", description = "User Obj")
@Table(name = "buckvid_users")
public class BuckvidUsers {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "username", name = "username", example = "buckeye123", required = true)
    private String username;

    @ApiModelProperty(value = "password", name = "password", example = "123456", required = true)
    private String password;

    @ApiModelProperty(hidden = true)
    private String icon;

    private String nickname;

    @ApiModelProperty(hidden = true)
    @Column(name = "followers_count")
    private Integer followersCount;

    @ApiModelProperty(hidden = true)
    @Column(name = "following_count")
    private Integer followingCount;

    @ApiModelProperty(hidden = true)
    @Column(name = "like_received_count")
    private Integer likeReceivedCount;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return followers_count
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     * @param followersCount
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @return following_count
     */
    public Integer getFollowingCount() {
        return followingCount;
    }

    /**
     * @param followingCount
     */
    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * @return like_received_count
     */
    public Integer getLikeReceivedCount() {
        return likeReceivedCount;
    }

    /**
     * @param likeReceivedCount
     */
    public void setLikeReceivedCount(Integer likeReceivedCount) {
        this.likeReceivedCount = likeReceivedCount;
    }
}