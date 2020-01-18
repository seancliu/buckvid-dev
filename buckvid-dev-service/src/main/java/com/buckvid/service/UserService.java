package com.buckvid.service;

import com.buckvid.pojo.BuckvidUsers;

public interface UserService {


    /**
     * @param username
     * @return if username is existent
     */
    public boolean isUsernameExistent(String username);

    /**
     * @param user
     *  save user's info
     */
    public void saveUser(BuckvidUsers user);

    /**
     * @param username
     * @param password
     * @return matched user
     */
    public BuckvidUsers queryForSignIn(String username, String password);

    public void updateUserInfo(BuckvidUsers user);

    public BuckvidUsers queryUserInfo(String userId);

    /**
     *
     * @param userId
     * @param videoId
     * @return if the user has liked the video
     */
    public boolean isUserLikedVideo(String userId, String videoId);

    /**
     * @param userId
     * @param followerId
     *
     * when a user(follower) follows another user(user)
     */
    public void saveUserFollowerRelationship(String userId, String followerId);

    /**
     * @param userId
     * @param followerId
     *
     * when a user(follower) unfollows another user(user)
     */
    public void deleteUserFollowerRelationship(String userId, String followerId);

    /**
     * @param userId
     * @param followerId
     *
     * query if followerId follows userId
     */
    public boolean queryIfFollowed(String userId, String followerId);
}
