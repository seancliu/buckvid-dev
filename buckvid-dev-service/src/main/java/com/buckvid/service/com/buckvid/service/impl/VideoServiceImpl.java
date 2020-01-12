package com.buckvid.service.com.buckvid.service.impl;

import com.buckvid.mapper.BgmMapper;
import com.buckvid.mapper.VideosMapper;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.Videos;
import com.buckvid.service.BgmService;
import com.buckvid.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);

        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String thumbPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setThumbnailPath(thumbPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }
}
