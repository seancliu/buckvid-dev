package com.buckvid.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Reports {
    @Id
    private String id;

    @Column(name = "reported_user_id")
    private String reportedUserId;

    @Column(name = "reported_video_id")
    private String reportedVideoId;

    /**
     * report reason selected by user
     */
    private String title;

    private String content;

    @Column(name = "reporter_id")
    private String reporterId;

    private Date timestamp;

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
     * @return reported_user_id
     */
    public String getReportedUserId() {
        return reportedUserId;
    }

    /**
     * @param reportedUserId
     */
    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    /**
     * @return reported_video_id
     */
    public String getReportedVideoId() {
        return reportedVideoId;
    }

    /**
     * @param reportedVideoId
     */
    public void setReportedVideoId(String reportedVideoId) {
        this.reportedVideoId = reportedVideoId;
    }

    /**
     * 获取report reason selected by user
     *
     * @return title - report reason selected by user
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置report reason selected by user
     *
     * @param title report reason selected by user
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * @return reporter_id
     */
    public String getReporterId() {
        return reporterId;
    }

    /**
     * @param reporterId
     */
    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
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
}