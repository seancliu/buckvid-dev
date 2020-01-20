package com.buckvid.service.impl;

import com.buckvid.mapper.BuckvidUsersMapper;
import com.buckvid.mapper.ReportsMapper;
import com.buckvid.mapper.UsersFollowersMapper;
import com.buckvid.mapper.UsersVideosMapper;
import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.pojo.Reports;
import com.buckvid.pojo.UsersFollowers;
import com.buckvid.pojo.UsersVideos;
import com.buckvid.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BuckvidUsersMapper usersMapper;

    @Autowired
    private UsersVideosMapper usersVideosMapper;

    @Autowired
    private UsersFollowersMapper usersFollowersMapper;

    @Autowired
    private ReportsMapper reportsMapper;

    @Autowired
    private Sid sid;

    // Support a current transaction, execute non-transactionally if none exists.
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUsernameExistent(String username) {
        BuckvidUsers user = new BuckvidUsers();
        user.setUsername(username);
        BuckvidUsers result = usersMapper.selectOne(user);
        return result != null;
    }

    // Support a current transaction, create a new one if none exists.
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(BuckvidUsers user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public BuckvidUsers queryForSignIn(String username, String password) {
        Example userExameple = new Example(BuckvidUsers.class);
        Example.Criteria criteria = userExameple.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        BuckvidUsers result = usersMapper.selectOneByExample(userExameple);

        return result;
    }

    @Override
    public void updateUserInfo(BuckvidUsers user) {
        Example userExameple = new Example(BuckvidUsers.class);
        Example.Criteria criteria = userExameple.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExameple);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public BuckvidUsers queryUserInfo(String userId) {
        Example userExameple = new Example(BuckvidUsers.class);
        Example.Criteria criteria = userExameple.createCriteria();
        criteria.andEqualTo("id", userId);
        BuckvidUsers user = usersMapper.selectOneByExample(userExameple);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikedVideo(String userId, String videoId) {
        if (StringUtils.isBlank((userId)) || StringUtils.isBlank((videoId))) {
            return false;
        }
        Example example = new Example(UsersVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<UsersVideos> list = usersVideosMapper.selectByExample(example);

        if (list != null && !list.isEmpty() && list.size() > 0) {
            return true;
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFollowerRelationship(String userId, String followerId) {
        // 1. add the new relationship to users_followers db
        String relId = sid.nextShort();
        UsersFollowers uf = new UsersFollowers();
        uf.setId(relId);
        uf.setUserId(userId);
        uf.setFollowerId(followerId);
        usersFollowersMapper.insert(uf);

        // 2. update followers_count and following_count in buckvid_users db
        usersMapper.addFollowersCount(userId);
        usersMapper.addFollowingCount(followerId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFollowerRelationship(String userId, String followerId) {
        // 1. delete the relationship
        Example example = new Example(UsersFollowers.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("followerId", followerId);
        usersFollowersMapper.deleteByExample(example);

        // 2. update followers_count and following_count in buckvid_users db
        usersMapper.reduceFollowersCount(userId);
        usersMapper.reduceFollowingCount(followerId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryIfFollowed(String userId, String followerId) {
        Example example = new Example(UsersFollowers.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("followerId", followerId);
        List<UsersFollowers> list = usersFollowersMapper.selectByExample(example);

        if (list != null && !list.isEmpty() && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void report(Reports report) {
        String reportId = sid.nextShort();
        report.setId(reportId);
        report.setTimestamp(new Date());

        reportsMapper.insert(report);
    }
}
