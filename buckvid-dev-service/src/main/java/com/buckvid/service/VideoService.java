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

    public List<String> getTrends();
}
