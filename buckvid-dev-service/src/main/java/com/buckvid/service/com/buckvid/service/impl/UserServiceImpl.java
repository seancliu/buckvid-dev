package com.buckvid.service.com.buckvid.service.impl;

import com.buckvid.mapper.BuckvidUsersMapper;
import com.buckvid.mapper.UsersVideosMapper;
import com.buckvid.pojo.BuckvidUsers;
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

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BuckvidUsersMapper usersMapper;

    @Autowired
    private UsersVideosMapper usersVideosMapper;

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

        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }
}
