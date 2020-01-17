package com.buckvid.pojo.vo;

public class CreatorVideo {
    public BuckvidUsersVO creator;
    public boolean userLikesVideo;

    public void setCreator(BuckvidUsersVO creator) {
        this.creator = creator;
    }

    public void setUserLikesVideo(boolean userLikesVideo) {
        this.userLikesVideo = userLikesVideo;
    }

    public BuckvidUsersVO getCreator() {
        return creator;
    }

    public boolean isUserLikesVideo() {
        return userLikesVideo;
    }
}
