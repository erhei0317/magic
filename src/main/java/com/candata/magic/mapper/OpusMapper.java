package com.candata.magic.mapper;

import com.candata.magic.pojo.Opus;


public interface OpusMapper {
    int deleteByPrimaryKey(Integer opusid);

    int insert(Opus record);

    int insertSelective(Opus record);

    Opus selectByPrimaryKey(Integer opusid);

    int updateByPrimaryKeySelective(Opus record);

    int updateByPrimaryKey(Opus record);
}