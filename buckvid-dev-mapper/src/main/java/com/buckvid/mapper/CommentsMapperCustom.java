package com.buckvid.mapper;

import com.buckvid.pojo.Comments;
import com.buckvid.pojo.vo.CommentsVO;
import com.buckvid.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
    public List<CommentsVO> queryComments(String videoId);
}