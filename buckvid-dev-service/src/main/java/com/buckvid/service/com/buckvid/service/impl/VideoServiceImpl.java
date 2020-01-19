package com.buckvid.service.com.buckvid.service.impl;

import com.buckvid.mapper.*;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.SearchRecords;
import com.buckvid.pojo.UsersVideos;
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
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private BuckvidUsersMapper buckvidUsersMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersVideosMapper usersVideosMapper;

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
        List<VideosVO> list = videosMapperCustom.queryAllVideos(desc, userId);
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
    public PagedResult getMyFavorites(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyFavorites(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getMyFollowing(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyFollowing(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getTrends() {
        return searchRecordsMapper.getTrends();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikesVideo(String userId, String videoId, String videoCreatorId) {
        // 1. save the relationship between a user and liked video
        String likeId = sid.nextShort();

        UsersVideos uv = new UsersVideos();
        uv.setId(likeId);
        uv.setUserId(userId);
        uv.setVideoId(videoId);
        usersVideosMapper.insert(uv);

        // 2. add like count of the video
        videosMapperCustom.addVideoLikeCount(videoId);

        // 3. add creator's received like count
        buckvidUsersMapper.addLikeReceivedCount(videoCreatorId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnlikesVideo(String userId, String videoId, String videoCreatorId) {
        // 1. delete the relationship between a user and liked video
        Example example = new Example(UsersVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);
        usersVideosMapper.deleteByExample(example);

        // 2. reduce like count of the video
        videosMapperCustom.reduceVideoLikeCount(videoId);

        // 3. reduce creator's received like count
        buckvidUsersMapper.reduceLikeReceivedCount(videoCreatorId);
    }
}
