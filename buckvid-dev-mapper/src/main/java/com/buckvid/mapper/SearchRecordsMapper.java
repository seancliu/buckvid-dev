package com.buckvid.mapper;

import com.buckvid.pojo.SearchRecords;
import com.buckvid.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    List<String> getTrends();
}