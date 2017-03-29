package com.candata.magic.mapper;

import com.candata.magic.pojo.Portal;


public interface PortalMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Portal record);

    int insertSelective(Portal record);

    Portal selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Portal record);

    int updateByPrimaryKey(Portal record);
}