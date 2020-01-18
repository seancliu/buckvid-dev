package com.buckvid.mapper;

import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.utils.MyMapper;

public interface BuckvidUsersMapper extends MyMapper<BuckvidUsers> {

    public void addLikeReceivedCount(String userId);
    public void reduceLikeReceivedCount(String userId);

    public void addFollowersCount(String userId);
    public void reduceFollowersCount(String userId);
    public void addFollowingCount(String userId);
    public void reduceFollowingCount(String userId);
}