package com.buckvid.mapper;

import com.buckvid.pojo.Videos;
import com.buckvid.pojo.vo.VideosVO;
import com.buckvid.utils.MyMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {
    public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
}