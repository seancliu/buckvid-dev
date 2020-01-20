package com.buckvid.pojo.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class CommentsVO {
    @Id
    private String id;


    @Column(name = "video_id")
    private String videoId;

    @Column(name = "from_user_id")
    private String fromUserId;

    private Date timestamp;

    private String content;

    private String icon;
    private String nickname;
    private String toNickname;
    private String timeAgoStr;

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
     * @return video_id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return from_user_id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToNickname() {
        return toNickname;
    }

    public String getTimeAgoStr() {
        return timeAgoStr;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public void setTimeAgoStr(String timeAgoStr) {
        this.timeAgoStr = timeAgoStr;
    }
}