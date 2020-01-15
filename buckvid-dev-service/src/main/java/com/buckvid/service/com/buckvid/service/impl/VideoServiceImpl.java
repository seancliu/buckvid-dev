package com.buckvid.service.com.buckvid.service.impl;

import com.buckvid.mapper.BgmMapper;
import com.buckvid.mapper.SearchRecordsMapper;
import com.buckvid.mapper.VideosMapper;
import com.buckvid.mapper.VideosMapperCustom;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.SearchRecords;
import com.buckvid.pojo.Videos;
import com.buckvid.pojo.vo.VideosVO;
import com.buckvid.service.BgmService;
import com.buckvid.service.VideoService;
import com.buckvid.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Videos video, Integer saveRecord, Integer page, Integer pageSize) {

        // save hot trends of searching
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (saveRecord != null && saveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryAllVideos(desc);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getTrends() {
        return searchRecordsMapper.getTrends();
    }
}
