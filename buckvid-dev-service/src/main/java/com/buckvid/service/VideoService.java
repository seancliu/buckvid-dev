package com.buckvid.service;

import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.Videos;

import java.util.List;

public interface VideoService {


    public String saveVideo(Videos video);

    /**
     *  Update video thumb
     * */
    public void updateVideo(String videoId, String thumbPath);
}
