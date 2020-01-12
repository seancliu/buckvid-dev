package com.buckvid.service.com.buckvid.service.impl;

import com.buckvid.mapper.BgmMapper;
import com.buckvid.mapper.BuckvidUsersMapper;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.BuckvidUsers;
import com.buckvid.service.BgmService;
import com.buckvid.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private Sid sid;

    // Support a current transaction, execute non-transactionally if none exists.
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }
}
