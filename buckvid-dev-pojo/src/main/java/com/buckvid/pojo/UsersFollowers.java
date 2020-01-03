package com.buckvid.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users_followers")
public class UsersFollowers {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "follower_id")
    private String followerId;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return follower_id
     */
    public String getFollowerId() {
        return followerId;
    }

    /**
     * @param followerId
     */
    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}