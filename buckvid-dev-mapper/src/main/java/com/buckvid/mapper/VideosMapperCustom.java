package com.buckvid.mapper;

import com.buckvid.pojo.Videos;
import com.buckvid.pojo.vo.VideosVO;
import com.buckvid.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc,
                                         @Param("userId") String userId);

    public List<VideosVO> queryMyFavorites(@Param("userId") String userId);

    public List<VideosVO> queryMyFollowing(@Param("userId") String userId);

    public void addVideoLikeCount(String videoId);

    public void reduceVideoLikeCount(String videoId);
}