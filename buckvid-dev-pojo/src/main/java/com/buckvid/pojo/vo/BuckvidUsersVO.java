package com.buckvid.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "User Obj", description = "User Obj")
@Table(name = "buckvid_users")
public class BuckvidUsersVO {
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    private String userToken;

    private boolean isFollowed;



    @ApiModelProperty(value = "username", name = "username", example = "buckeye123", required = true)
    private String username;

    @ApiModelProperty(value = "password", name = "password", example = "123456", required = true)
    @JsonIgnore
    private String password;

    @ApiModelProperty(hidden = true)
    private String icon;

    private String nickname;

    @ApiModelProperty(hidden = true)
    private Integer followersCount;

    @ApiModelProperty(hidden = true)
    private Integer followingCount;

    @ApiModelProperty(hidden = true)
    private Integer likeReceivedCount;

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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