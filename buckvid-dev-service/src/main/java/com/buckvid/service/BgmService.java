package com.buckvid.service;

import com.buckvid.mapper.BgmMapper;
import com.buckvid.pojo.Bgm;
import com.buckvid.pojo.BuckvidUsers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface BgmService {


    /**
     * @return BGM List
     */
    public List<Bgm> queryBgmList();

    public Bgm queryBgmById(String bgmId);
}
