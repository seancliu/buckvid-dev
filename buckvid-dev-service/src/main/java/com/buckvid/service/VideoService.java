package com.buckvid.service;

import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.Videos;
import com.buckvid.utils.PagedResult;

import java.util.List;

public interface VideoService {


    public String saveVideo(Videos video);

    /**
     *  Update video thumb
     * */
    public void updateVideo(String videoId, String thumbPath);


    /**
     *  Paged List of Videos
     * */
    public PagedResult getAllVideos(Videos video, Integer saveRecord, Integer page, Integer pageSize);

    /**
     *  Paged List of Liked Videos
     * */
    public PagedResult getMyFavorites(String userId, Integer page, Integer pageSize);

    /**
     *  Paged List of all the Videos posted by followed users
     * */
    public PagedResult getMyFollowing(String userId, Integer page, Integer pageSize);

    /**
     *  Get hot trends
     * */
    public List<String> getTrends();

    public void userLikesVideo(String userId, String videoId, String videoCreatorId);

    public void userUnlikesVideo(String userId, String videoId, String videoCreatorId);
}
